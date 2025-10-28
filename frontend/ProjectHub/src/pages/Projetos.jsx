import '../App.css'
import React, { useEffect, useState } from 'react'
import { dataTeste } from './data/dataTest';
import { IconField } from "primereact/iconfield";
import { InputIcon } from "primereact/inputicon";
import { InputText } from "primereact/inputtext";
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { ProgressSpinner } from 'primereact/progressspinner';



  const header = (
    <img alt="Card" src="https://primefaces.org/cdn/primereact/images/usercard.png" />
  );
  const footer = (
    <>
      <Button label="Visualizar" style={{ marginLeft: '110px' }}/>
      
    </>
  );


const Projetos = () => {

  const [projects, setProjects] = useState([]);
  const [busca, setBusca] = useState('');

  useEffect(() => {
    const timeout = setTimeout(() => {
      setProjects(dataTeste);
    }, 3000); //simular delay de carregamento

    return () => clearTimeout(timeout);
  }, []);

    const projetosFiltrados = projects.filter((proj) =>
    proj.title.toLowerCase().includes(busca.toLowerCase())
  );

  return (
    <div>
      <div className='search-bar'>
        <IconField iconPosition="left">
          <InputIcon className="pi pi-search"> </InputIcon>
          <InputText placeholder="Buscar Projeto" className="search-input" value={busca} onChange={(e) => setBusca(e.target.value)} aria-label="Carregando..." />
        </IconField>

        <Button label="Novo Projeto" icon="pi pi-plus" className="add-project-button"/>
      </div>
      <div className='card-container'>
      {projects.length === 0 ? (
        <div className='loading'>
          <ProgressSpinner style={{width: '50px', height: '50px'}} strokeWidth="8" animationDuration=".5s" />
        </div>
      ) : 
      (
        projetosFiltrados.map((project) => (
          <div key={project.id} className="card">
            <Card title={project.title} subTitle={`Status: ${project.status}`} footer={footer} header={header} className="md:w-25rem">
              <p className="m-0">{project.description}</p>
            </Card>
          </div>
        ))
      )}
      </div>
      
    </div>
  )
}

export default Projetos
