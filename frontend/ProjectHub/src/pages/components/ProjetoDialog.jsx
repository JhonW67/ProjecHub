import React, { useState } from "react";
import { Dialog } from "primereact/dialog";
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from "primereact/inputtextarea";
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';

export default function ProjetoDialog({ visible, onHide }) {
    const [titulo, setTitulo] = useState("");
    const [descricao, setDescricao] = useState("");
    const [selectedStatus, setSelectedStatus] = useState(null);
    const [erro, setErro] = useState(null);


    const status = [
        {id: 1, status:"Em Andamento"},
        {id: 2, status:"Finalizado"},
        {id: 3, status:"Cancelado"},
    ]

    const cleanForm = () => {
        setTitulo("")
        setDescricao("")
        setSelectedStatus(null)
    }

  const handleSubmit = () => {
    if (!titulo.trim() || !selectedStatus) {
      setErro("Por favor, preencha o título e selecione um status.");
      return;
    }

    const dadosProjeto = {
      titulo,
      descricao,
      status: selectedStatus
    };

    console.log("Dados capturados:", dadosProjeto);
    onHide();
    setErro(null)
    cleanForm();
  };
  return (
    <Dialog
      header="Cadastrar novo Projeto"
      visible={visible}
      style={{ width: "600px" }}
      onHide={onHide}>

        <div className="card flex flex-column gap-3">
            <InputText placeholder="Título" className={`w-full ${!titulo && erro ? "p-invalid" : ""}`} value={titulo} onChange={(e) => setTitulo(e.target.value)} />

            <InputTextarea  placeholder="Descrição" className="w-full" value={descricao} onChange={(e) => setDescricao(e.target.value)} rows={5} cols={30}/>

            <Dropdown value={selectedStatus} onChange={(e) => setSelectedStatus(e.value)} options={status} optionLabel="status" placeholder="Status" className={`w-full ${!selectedStatus && erro ? "p-invalid" : ""}`} />

            <Button label="Cadastrar" onClick={handleSubmit}/>
        </div>

    </Dialog>
  );
}
