import '../App.css'
import React from 'react'
import { IconField } from "primereact/iconfield";
import { InputIcon } from "primereact/inputicon";
import { InputText } from "primereact/inputtext";
import { Button } from 'primereact/button';


const Projetos = () => {
  return (
    <div>
      <div className='search-bar'>
        <IconField iconPosition="left">
          <InputIcon className="pi pi-search"> </InputIcon>
          <InputText placeholder="Buscar Projeto" className="search-input" />
        </IconField>

        <Button label="Novo Projeto" icon="pi pi-plus" className="add-project-button"/>
      </div>
    </div>
  )
}

export default Projetos
