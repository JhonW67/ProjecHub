import React, { useState, useEffect } from "react";
import { Dialog } from "primereact/dialog";
import { InputText } from "primereact/inputtext";
import { InputTextarea } from "primereact/inputtextarea";
import { Dropdown } from "primereact/dropdown";
import { Button } from "primereact/button";

const STATUS_OPTIONS = [
  { id: 1, status: "Em Andamento" },
  { id: 2, status: "Finalizado" },
  { id: 3, status: "Cancelado" },
];

export default function ProjetoDialog({ visible, onHide, projetoSelecionado, onSave }) {
  const [titulo, setTitulo] = useState("");
  const [descricao, setDescricao] = useState("");
  const [selectedStatus, setSelectedStatus] = useState(null);
  const [erro, setErro] = useState(null);
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);

useEffect(() => {
    if (projetoSelecionado) {
      setTitulo(projetoSelecionado.title || "");
      setDescricao(projetoSelecionado.description || "");
      setSelectedStatus(
        STATUS_OPTIONS.find((s) => s.status === projetoSelecionado.status) || null
      );
      if (projetoSelecionado.event && events.length > 0) {
        const eventoEncontrado = events.find(e => e.id === projetoSelecionado.event.eventId);
        setSelectedEvent(eventoEncontrado || null);
      } else if (!projetoSelecionado.event) {
         setSelectedEvent(null);
      }
      
    } else {
      cleanForm();
    }
  }, [projetoSelecionado, visible, events]);

  useEffect(() => {
  const fetchEvents = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/events");
      if (!response.ok) throw new Error("Erro ao carregar eventos");
      const data = await response.json();

      const formatted = data.map((event) => ({
        name: event.title,
        id: event.eventId
      }));

      setEvents(formatted);
    } catch (error) {
      console.error(error);
    }
  };

  fetchEvents();
}, []);


  const cleanForm = () => {
    setTitulo("");
    setDescricao("");
    setSelectedStatus(null);
    setSelectedEvent(null)
  };

  const handleSubmit = () => {
    if (!titulo.trim() || !selectedStatus) {
      setErro("Por favor, preencha o título e selecione um status.");
      return;
    }

    const dadosProjeto = {
      projectId: projetoSelecionado ? projetoSelecionado.projectId : null,
      title: titulo,
      description: descricao,
      status: selectedStatus.status,
      event: selectedEvent ? { eventId: selectedEvent.id } : null,
      createdBy: null,
      createdAt: projetoSelecionado?.created_at || new Date()
    };

    onSave(dadosProjeto);
    setErro(null);
    onHide();
    cleanForm();

  };

  return (
    <Dialog
      header={projetoSelecionado ? "Editar Projeto" : "Cadastrar novo Projeto"}
      visible={visible}
      style={{ width: "600px" }}
      onHide={() => {
        setErro(null);
        onHide();
        cleanForm();
      }}
    >
      <div className="card flex flex-column gap-3">
        <InputText
          placeholder="Título"
          className={`w-full ${!titulo && erro ? "p-invalid" : ""}`}
          value={titulo}
          onChange={(e) => setTitulo(e.target.value)}
        />

        <InputTextarea
          placeholder="Descrição"
          className="w-full"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          rows={5}
          cols={30}
        />

        <Dropdown
          value={selectedStatus}
          onChange={(e) => setSelectedStatus(e.value)}
          options={STATUS_OPTIONS}
          optionLabel="status"
          placeholder="Status"
          className={`w-full ${!selectedStatus && erro ? "p-invalid" : ""}`}
        />

        <Dropdown
          value={selectedEvent}
          onChange={(e) => setSelectedEvent(e.value)}
          options={events}
          optionLabel="name"
          placeholder="Selecione um evento"
          className="w-full"
        />


        <Button
          label={projetoSelecionado ? "Salvar Alterações" : "Cadastrar"}
          onClick={handleSubmit}
        />
      </div>
    </Dialog>
  );
}
