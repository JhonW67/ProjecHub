import React, { useState } from 'react';
import { Card } from 'primereact/card';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { Rating } from 'primereact/rating';


const ProjectFeedback = ({ project, setProject, user }) => {
  const [newFeedback, setNewFeedback] = useState('');
  const [saving, setSaving] = useState(false);
  const [rating, setRating] = useState(0);

  const sendFeedback = async () => {
    if (!user) {
      alert('Faça login para enviar feedback.');
      return;
    }
    if (!newFeedback.trim() && rating === 0) return;

    try {
      setSaving(true);
      const res = await fetch(`http://localhost:8080/api/projects/${project.projectId}/feedbacks`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ comment: newFeedback , rating })
      });
      if (!res.ok) throw new Error('Erro ao enviar feedback');
      const updated = await res.json(); // ProjectDetailDTO
      setProject(updated);
      setNewFeedback('');
      setRating(0);
    } catch (err) {
      console.error(err);
      alert('Não foi possível enviar o feedback.');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (feedbackId) => {
    if (!window.confirm('Remover este feedback?')) return;
    try {
      const res = await fetch(
        `http://localhost:8080/api/projects/${project.projectId}/feedbacks/${feedbackId}`,
        { method: 'DELETE' }
      );
      if (!res.ok) {
        if (res.status === 403) {
          alert('Você só pode remover seus próprios feedbacks, ou precisa ser admin.');
        } else {
          alert('Erro ao remover feedback.');
        }
        return;
      }
      const updated = await res.json();
      setProject(updated);
    } catch (err) {
      console.error(err);
      alert('Não foi possível remover o feedback.');
    }
  };

  const isAdmin = user?.role === 'admin';

  return (
    <div>
      {project.feedbacks?.length ? project.feedbacks.map(fb => {
        const isAuthor = user && fb.authorName === user.name; // se DTO tiver authorId, use id
        return (
          <Card key={fb.id} style={{ marginBottom: 10 }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <div>
                <b>{fb.authorName}:</b>
                <div style={{ margin: '4px 0' }}>
                  {typeof fb.rating === 'number' && (
                    <Rating value={fb.rating} readOnly cancel={false} />
                  )}
                </div>
                <p style={{ marginTop: 4 }}>{fb.comment}</p>
              </div>
              {(isAuthor || isAdmin) && (
                <Button
                  icon="pi pi-trash"
                  className="p-button-text p-button-danger"
                  onClick={() => handleDelete(fb.id)}
                  tooltip="Excluir feedback"
                />
              )}
            </div>
          </Card>
        );
      }) : (
        <p>Nenhum feedback ainda.</p>
      )}

      <div style={{ marginTop: 16 }}>
        <InputTextarea
          rows={3}
          value={newFeedback}
          onChange={e => setNewFeedback(e.target.value)}
          placeholder="Deixe seu feedback sobre este projeto..."
          style={{ width: '100%' }}
        />
        <div style={{ marginTop: 16 }}>
          <label>Nota (0 a 5):</label>
          <div>
            <Rating value={rating} onChange={e => setRating(e.value)} cancel={false} />
          </div>
        </div>
        <Button
          label={saving ? 'Enviando...' : 'Enviar feedback'}
          icon="pi pi-send"
          className="mt-2"
          onClick={sendFeedback}
          disabled={saving}
        />
      </div>
    </div>
  );
};

export default ProjectFeedback;
