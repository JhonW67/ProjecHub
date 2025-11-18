import '../App.css'
import React, { useEffect, useRef, useState } from 'react'
import { IconField } from "primereact/iconfield";
import { InputIcon } from "primereact/inputicon";
import { InputText } from "primereact/inputtext";
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { ConfirmDialog, confirmDialog } from 'primereact/confirmdialog';
import ProjetoDialog from "./components/ProjetoDialog";
import { Toast } from 'primereact/toast';


const getHeaderImage = (eventId) => {

  if (!eventId || eventId == null) {
    return <img alt="Card" src="https://primefaces.org/cdn/primereact/images/usercard.png" />;
  }

  if (eventId.includes("7f839a36-cb5c-4dea-aee8-2024678f73bd")) {
    return <img alt="Evento" src="src/assets/PEI-20251.png" />;
  }

  if (eventId.includes("dd71b2e6-afc1-4c56-ad8c-0c4b904d1019")) {
    return <img alt="Evento" src="src/assets/PEI-20252.png" />;
  }

  return <img alt="Card" src="https://primefaces.org/cdn/primereact/images/usercard.png" />;
};


const Projetos = () => {
  const [projects, setProjects] = useState([]);
  const [busca, setBusca] = useState('');
  const [visible, setVisible] = useState(false);
  const [projetoSelecionado, setProjetoSelecionado] = useState(null);
  const toast = useRef(null);

  useEffect(() => {
    const fetchProjects = async () =>{
      try{
        const response = await fetch("http://localhost:8080/api/projects")
        if (!response.ok) throw new Error('Erro ao carregar projetos');
        const data = await response.json();
        setProjects(data)
      }catch(error){
        console.log(error)
      }
     };
    fetchProjects();
  }, []);

  const handleSave = async (projeto) => {
  try {
    let savedProject = projeto;

    if (projetoSelecionado) {
      const response = await fetch(`http://localhost:8080/api/projects/${projeto.projectId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(projeto),
      });
      savedProject = await response.json();

      setProjects((prev) =>
        prev.map((p) => (p.projectId === savedProject.projectId ? savedProject : p))
      );
    } else {
      const response = await fetch("http://localhost:8080/api/projects", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(projeto),
      });
      savedProject = await response.json();

      setProjects((prev) => [...prev, savedProject]);
    }

    toast.current?.show({
      severity: 'success',
      summary: projetoSelecionado ? 'Projeto atualizado' : 'Projeto criado',
      detail: savedProject.title,
      life: 3000
    });

    setProjetoSelecionado(null);
  } catch (error) {
    console.error(error);
    toast.current?.show({
      severity: 'error',
      summary: 'Erro',
      detail: 'Não foi possível salvar o projeto.',
      life: 3000
    });
  }
};


const projetosFiltrados = projects.filter((proj) =>
  proj.title.toLowerCase().includes(busca.toLowerCase())
  );

const confirmDelete = (project) => {
  confirmDialog({
    message: `Deseja realmente excluir o projeto "${project.title}"?`,
    header: 'Confirmação',
    icon: 'pi pi-info-circle',
    acceptLabel: 'Sim',
    rejectLabel: 'Não',
    accept: async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/projects/${project.projectId}`, {
          method: "DELETE",
        });

        if (!response.ok) throw new Error('Erro ao excluir projeto');

        setProjects((prev) => prev.filter((p) => p.projectId !== project.projectId));

        toast.current?.show({
          severity: 'success',
          summary: 'Projeto excluído',
          detail: `"${project.title}" foi removido com sucesso.`,
          life: 3000
        });
      } catch (error) {
        console.error(error);
        toast.current?.show({
          severity: 'error',
          summary: 'Erro ao excluir',
          detail: 'Não foi possível excluir o projeto.',
          life: 3000
        });
      }
    },
  });
};

  return (
    <div>
      <Toast ref={toast} />
      <div className='search-bar'>
        <IconField iconPosition="left">
          <InputIcon className="pi pi-search"> </InputIcon>
          <InputText placeholder="Buscar Projeto" className="search-input" value={busca} onChange={(e) => setBusca(e.target.value)}/>
        </IconField>

        <Button label="Novo Projeto" icon="pi pi-plus" className="add-project-button" 
        onClick={() => {setProjetoSelecionado(null); setVisible(true);}}/>
      </div>

      <ProjetoDialog visible={visible} onHide={() => setVisible(false)} projetoSelecionado={projetoSelecionado} onSave={handleSave}/>
      
      <ConfirmDialog/>
      <div className='card-container'>
        {projects.length === 0 ? (
          <div className='loading'>
            <p style={{color: "#FFF"}}>Sem projetos cadastrados no momento :(</p>
          </div>
        ) : (
          projetosFiltrados.map((project) => (
            <div key={project.projectId} className="card">
              <Card title={project.title} subTitle={`Status: ${project.status}`} footer={
                  <div style={{ display: "flex", justifyContent: "center", gap: "0.5rem" }}>
                    <Button label="Editar" icon="pi pi-pencil" severity="secondary" 
                    onClick={() => {setProjetoSelecionado(project);setVisible(true);}}/>
                    <Button onClick={() => confirmDelete(project)} icon="pi pi-times" label="Delete" className="p-button-danger"/>
                  </div>}
                header={getHeaderImage(project.event ? project.event.eventId : null)}className="md:w-25rem">
                <p className="m-0">{project.description}</p>
              </Card>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default Projetos;
