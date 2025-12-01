import React, { useState } from 'react';
import { Card } from 'primereact/card';
import { InputTextarea } from 'primereact/inputtextarea';
import { InputNumber } from 'primereact/inputnumber';
import { Button } from 'primereact/button';

const ProjectEvaluation = ({ project, setProject, user }) => {
  const [grade, setGrade] = useState(project.evaluation?.grade ?? (0));
  const [comment, setComment] = useState(project.evaluation?.comment || '');
  const [saving, setSaving] = useState(false);
  const isTeacherOrAdmin = user && (user.role === 'teacher' || user.role === 'admin');

  const handleSave = async () => {
  if (!user) {
    alert('Faça login como professor para avaliar.');
    return;
  }

  try {
    setSaving(true);
    const res = await fetch(
      `http://localhost:8080/api/projects/${project.projectId}/evaluation`,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          grade,
          comment,
          //visibleToStudents,
          userId: user.userId || user.id // garante que manda o id do professor
        })
      }
    );
    if (!res.ok) throw new Error('Erro ao salvar avaliação');
    const updated = await res.json();
    setProject(updated);
  } catch (err) {
    console.error(err);
    alert('Erro ao salvar avaliação');
  } finally {
    setSaving(false);
  }
  };

  const canEdit = isTeacherOrAdmin
// só professor/admin altera
  
  return (
    <div>
      {project.evaluation ? (
        <Card style={{ marginBottom: 16 }}>
          <p><b>Nota final:</b> {project.evaluation.grade}</p>
          <p><b>Comentário:</b> {project.evaluation.comment}</p>
          <p><b>Avaliador:</b> {project.evaluation.professorName}</p>
          {project.evaluation.professorName && (
            <p style={{ color: '#64748b' }}>
              Avaliado por: {project.evaluation.professorName}
            </p>
          )}
        </Card>
      ) : (
        <p>Aguardando avaliação do professor.</p>
      )}

      {isTeacherOrAdmin && (
        <Card title={project.evaluation ? 'Atualizar avaliação' : 'Registrar avaliação'}>
          <div className="p-fluid">
            <div className="field">
              <label>Nota (0 a 10)</label>
              <InputNumber
                value={grade}
                onValueChange={e => setGrade(e.value)}
                min={0}
                max={10}
              />
            </div>
            <div className="field" style={{ marginTop: 12 }}>
              <label>Comentário</label>
              <InputTextarea
                rows={4}
                value={comment}
                onChange={e => setComment(e.target.value)}
                style={{ width: '100%' }}
              />
            </div>
            <div style={{ marginTop: 16, textAlign: 'right' }}>
              <Button
                label={saving ? 'Salvando...' : 'Salvar avaliação'}
                icon="pi pi-check"
                onClick={handleSave}
                disabled={saving}
              />
            </div>
          </div>
        </Card>
      )}
    </div>
  );
};

export default ProjectEvaluation;
