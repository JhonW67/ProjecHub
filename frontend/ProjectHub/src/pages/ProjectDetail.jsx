import React, { useEffect, useState } from 'react';
import { TabView, TabPanel } from 'primereact/tabview';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { useParams } from 'react-router-dom';

//components
import ProjectHeader from './components/ProjectHeader';
import ProjectDocuments from './components/ProjectDocument';
import ProjectFeedback from './components/ProjectFeedback';
import ProjectQrCode from './components/ProjectQrCode';
import ProjectEvaluation from './components/ProjectEvaluation';

const ProjectDetails = () => {
  const { projectId } = useParams();
  const [project, setProject] = useState(null);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // ✅ SEMPRE executa - NUNCA condicional
  useEffect(() => {
    if (!projectId) {
      setLoading(false);
      return;
    }

    const fetchProject = async () => {
      try {
        console.log('🔍 DEBUG - Buscando projeto:', projectId);
        const res = await fetch(`http://localhost:8080/api/projects/${projectId}`);
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const projectData = await res.json();
        console.log('🔍 DEBUG - Projeto recebido:', projectData);
        setProject(projectData);
      } catch (err) {
        console.error('🔍 DEBUG - Erro projeto:', err);
        setProject(null);
      }
    };

    const fetchUser = () => {
      try {
        const userData = localStorage.getItem('user');
        console.log('🔍 DEBUG - User raw:', userData);
        if (userData) {
          const parsedUser = JSON.parse(userData);
          console.log('🔍 DEBUG - User parsed:', parsedUser);
          setUser(parsedUser);
        }
      } catch (err) {
        console.error('🔍 DEBUG - Erro user:', err);
      }
    };

    fetchProject();
    fetchUser();
    setLoading(false);
  }, [projectId]); // ✅ Dependency array correto

  // ✅ SEGUNDO useEffect - SEMPRE após o primeiro
  useEffect(() => {
    if (project && user) {
      console.log('🔍 DEBUG - Verificando permissões:');
      console.log('🔍 DEBUG - User:', user);
      console.log('🔍 DEBUG - Project createdBy:', project.createdBy);
      console.log('🔍 DEBUG - Project members:', project.members);

      const isAdmin = user?.role === 'admin';
      const isCreator = project.createdBy?.userId === user.userId || 
                       project.createdBy?.id === user.userId ||
                       project.createdBy?.userId === user.id;
      const isMember = project.members?.some(m => 
        m.user?.userId === user.userId || 
        m.userId === user.userId ||
        m.user?.id === user.userId ||
        m.id === user.userId
      );
      
      const canEdit = isAdmin || isCreator || isMember;
      console.log('🔍 DEBUG - isAdmin:', isAdmin);
      console.log('🔍 DEBUG - isCreator:', isCreator);
      console.log('🔍 DEBUG - isMember:', isMember);
      console.log('🔍 DEBUG - canEdit FINAL:', canEdit);
    }
  }, [project, user]); // ✅ Sempre executa após project/user mudarem

  if (loading) return <div>Carregando projeto...</div>;
  if (!project) return <div>Projeto não encontrado.</div>;

  const isAdmin = user?.role === 'admin';
  const isCreator = project.createdBy?.userId === user?.userId || 
                   project.createdBy?.id === user?.userId ||
                   project.createdBy?.userId === user?.id;
  const isMember = project.members?.some(m => 
    m.user?.userId === user?.userId || 
    m.userId === user?.userId ||
    m.user?.id === user?.userId ||
    m.id === user?.userId
  );
  const canEdit = isAdmin || isCreator || isMember;

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
          <ProjectQrCode project={project} setProject={setProject} canEdit={canEdit} />
        </TabPanel>
        <TabPanel header="Avaliação">
          <ProjectEvaluation project={project} setProject={setProject} user={user} />
        </TabPanel>
      </TabView>
    </div>
  );
};

export default ProjectDetails;
