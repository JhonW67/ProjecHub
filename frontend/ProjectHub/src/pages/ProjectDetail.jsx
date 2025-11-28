import React, { useEffect, useState } from 'react';
import { TabView, TabPanel } from 'primereact/tabview';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { useParams } from 'react-router-dom';

// Recebe o id do projeto via props ou via rota
const ProjectDetails = () => {
  const { projectId } = useParams();
  const [project, setProject] = useState(null);
  const [user, setUser] = useState(null); // Pega do contexto ou do localStorage/session

  useEffect(() => {
    if (!projectId) return;
    fetch(`http://localhost:8080/api/projects/${projectId}`)
      .then(res => {
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        return res.json();
      })
      .then(setProject)
      .catch(err => {
        console.error(err);
        setProject(null);
      });

    setUser(JSON.parse(localStorage.getItem('user')));
  }, [projectId]);

  if (!project) return <div>Carregando... ou Projeto não encontrado.</div>;

  const isAdmin = user?.role === 'admin';
  const isMember = project.members?.some(m => m.userId === user?.userId);
  const canEdit = isAdmin || isMember;

  return (
    <div className="project-details-main" style={{ maxWidth: '1050px', margin: '0 auto', padding: '32px 0' }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: 30, marginBottom: 28 }}>
        <img src={project.imageUrl || '/default_project_img.svg'} alt="Banner" style={{ width: 210, height: 210, borderRadius: 16, background: '#f3f6fa', objectFit: 'cover' }}/>
        <div>
          <h1 style={{ margin: 0 }}>{project.title}</h1>
          <div style={{ margin: '12px 0' }}>
            <b>Grupo:</b> {project.groupName} <br />
            <b>Criado em:</b> {project.createdAt}
          </div>
          {canEdit && (
            <Button label="Editar Projeto" icon="pi pi-pencil" />
          )}
        </div>
      </div>
      <TabView>
        <TabPanel header="Sobre o Projeto">
          <Card>
            <h3>Descrição</h3>
            <p>{project.description}</p>
            <h4>Data de Criação</h4>
            <p>{project.createdAt}</p>
          </Card>
        </TabPanel>
        <TabPanel header="Documentação">
          {project.documents && project.documents.length ?
            project.documents.map(doc => (
              <Card key={doc.id} style={{ marginBottom: 10 }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span>{doc.name}</span>
                  <Button
                    label={doc.type === 'video' ? "Assistir" : "Baixar"}
                    icon={doc.type === 'video' ? "pi pi-video" : "pi pi-download"}
                    onClick={() => window.open(doc.url, "_blank")}
                  />
                </div>
              </Card>
            )) : <p>Nenhum documento enviado ainda.</p>}
        </TabPanel>
        <TabPanel header="Feedback">
          {project.feedbacks?.length ? project.feedbacks.map(fb => (
            <Card key={fb.id} style={{ marginBottom: 10 }}>
              <b>{fb.authorName}:</b>
              <p>{fb.comment}</p>
            </Card>
          )) : <p>Nenhum feedback ainda.</p>}
        </TabPanel>
        <TabPanel header="QR Code">
          <img src={project.qrCodeUrl} alt="QR Code" style={{ width: 200, display: 'block', margin: '20px auto' }}/>
          <p style={{ textAlign: 'center' }}>Escaneie para acessar esta página direto.</p>
        </TabPanel>
        <TabPanel header="Avaliação">
          {project.evaluation ?
            <Card>
              <b>Nota:</b> {project.evaluation.grade}<br/>
              <b>Comentário:</b> {project.evaluation.comment}
            </Card>
            : <p>Aguardando avaliação do professor.</p>
          }
        </TabPanel>
      </TabView>
    </div>
  );
};

export default ProjectDetails;
