import React, { useEffect, useState } from 'react';
import { TabView, TabPanel } from 'primereact/tabview';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { useParams } from 'react-router-dom';

//components
import ProjectHeader from './components/ProjectHeader';
import ProjectDocuments from './components/ProjectDocument';
import ProjectFeedback from './components/ProjectFeedback';

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
      <ProjectHeader project={project} setProject={setProject} user={user} canEdit={canEdit} />
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
          <ProjectDocuments project={project} setProject={setProject} canEdit={canEdit} />
        </TabPanel>
        <TabPanel header="Feedback">
          <ProjectFeedback project={project} setProject={setProject} user={user} />
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
