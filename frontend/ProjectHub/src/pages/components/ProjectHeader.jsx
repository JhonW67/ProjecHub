import React, { useState } from 'react';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';

const ProjectHeader = ({ project, setProject, user, canEdit }) => {
  const [editVisible, setEditVisible] = useState(false);
  const [imageUrl, setImageUrl] = useState(project.imageUrl || '');
  const [groupName, setGroupName] = useState(project.groupName || '');

  const saveBasic = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/projects/${project.projectId}/basic`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          imageUrl,
          groupName,
          // por enquanto sem memberIds – adicionamos depois
          memberIds: null
        })
      });
      if (!res.ok) throw new Error('Erro ao atualizar projeto');
      const updated = await res.json();     // ProjectDetailDTO
      setProject(updated);
      setEditVisible(false);
    } catch (err) {
      console.error(err);
      alert('Não foi possível atualizar os dados do projeto.');
    }
  };

  return (
    <>
      <div style={{ display: 'flex', alignItems: 'center', gap: 30, marginBottom: 28 }}>
        <img
          src={project.imageUrl || '/default_project_img.svg'}
          alt="Banner"
          style={{ width: 210, height: 210, borderRadius: 16, background: '#f3f6fa', objectFit: 'cover' }}
        />
        <div>
          <h1 style={{ margin: 0 }}>{project.title}</h1>
          <div style={{ margin: '12px 0' }}>
            <b>Grupo:</b> {project.groupName || 'Não definido'} <br />
            <b>Criado em:</b> {project.createdAt}
          </div>
          {canEdit && (
            <Button label="Editar cabeçalho" icon="pi pi-pencil" onClick={() => setEditVisible(true)} />
          )}
        </div>
      </div>

      <Dialog
        header="Editar informações do projeto"
        visible={editVisible}
        style={{ width: '480px' }}
        onHide={() => setEditVisible(false)}
      >
        <div className="p-fluid">
          <div className="field">
            <label>URL do banner</label>
            <InputText value={imageUrl} onChange={e => setImageUrl(e.target.value)} />
          </div>
          <div className="field" style={{ marginTop: 12 }}>
            <label>Nome do grupo</label>
            <InputText value={groupName} onChange={e => setGroupName(e.target.value)} />
          </div>
          <div style={{ marginTop: 20, textAlign: 'right' }}>
            <Button label="Cancelar" className="p-button-text" onClick={() => setEditVisible(false)} />
            <Button label="Salvar" icon="pi pi-check" className="ml-2" onClick={saveBasic} />
          </div>
        </div>
      </Dialog>
    </>
  );
};

export default ProjectHeader;
