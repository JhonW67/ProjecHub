import React, { useState , useRef } from 'react';
import { Card } from 'primereact/card';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { FileUpload } from 'primereact/fileupload';

const typeOptions = [
  { label: 'Vídeo', value: 'video' },
  { label: 'Link', value: 'link' }
];

const ProjectDocuments = ({ project, setProject, canEdit }) => {
  const [name, setName] = useState('');
  const [type, setType] = useState('pdf');
  const [url, setUrl] = useState('');
  const [saving, setSaving] = useState(false);
  const fileUploadRef = useRef(null);

  // ✅ adiciona documento por LINK (fluxo atual)
  const handleAdd = async () => {
    if (!name.trim() || !url.trim()) return;
    setSaving(true);
    try {
      const res = await fetch(
        `http://localhost:8080/api/projects/${project.projectId}/documents`,
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ name, type, url })
        }
      );
      if (!res.ok) throw new Error('Erro ao adicionar documento');
      const updated = await res.json(); // ProjectDetailDTO
      setProject(updated);
      setName('');
      setUrl('');
      setType('pdf');
    } catch (err) {
      console.error(err);
      alert('Não foi possível adicionar o documento.');
    } finally {
      setSaving(false);
    }
  };

  // ✅ upload de ARQUIVO (png, jpeg, pdf, doc, docx, mp4, zip)
  const handleFileUpload = async (e) => {
    const [file] = e.files;
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);
    if (name.trim()) {
      formData.append('name', name.trim());
    }

    try {
      const res = await fetch(
        `http://localhost:8080/api/projects/${project.projectId}/documents/upload`,
        {
          method: 'POST',
          body: formData
        }
      );
      if (!res.ok) throw new Error('Erro ao enviar arquivo');
      const updated = await res.json();
      setProject(updated);
      setName('');
      // ✅ limpa o componente após sucesso
      if (fileUploadRef.current) {
        fileUploadRef.current.clear();
      }
    } catch (err) {
      console.error(err);
      alert('Não foi possível enviar o arquivo.');
    }
  };

  const handleDelete = async (documentId) => {
    if (!window.confirm('Deseja realmente remover este documento?')) return;

    try {
      const res = await fetch(
        `http://localhost:8080/api/projects/${project.projectId}/documents/${documentId}`,
        { method: 'DELETE' }
      );
      if (!res.ok) {
        if (res.status === 403) {
          alert('Você não tem permissão para remover documentos deste projeto.');
        } else {
          alert('Erro ao remover documento.');
        }
        return;
      }
      const updated = await res.json(); // ProjectDetailDTO
      setProject(updated);
    } catch (err) {
      console.error(err);
      alert('Não foi possível remover o documento.');
    }
  };

  console.log('DEBUG docs:', project.documents);


  return (
    <div>
      {project.documents && project.documents.length ? (
        project.documents.map((doc) => (
          <Card key={doc.id} style={{ marginBottom: 10 }}>
            <div
              style={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center'
              }}
            >
              <div>
                <strong>{doc.name}</strong>{' '}
                <span style={{ color: '#64748b' }}>({doc.type})</span>
              </div>
              <div style={{ display: 'flex', gap: 8 }}>
                {/* Se tiver url externa, abre normal. Se for arquivo, você vai mandar um downloadUrl no DTO */}
                <Button
                  label={doc.type === 'video' ? 'Assistir' : 'Abrir'}
                  icon={doc.type === 'video' ? 'pi pi-video' : 'pi pi-external-link'}
                  onClick={() => window.open(doc.url, '_blank')}
                />
                {canEdit && (
                  <Button
                    icon="pi pi-trash"
                    className="p-button-danger p-button-outlined"
                    onClick={() => handleDelete(doc.id)}
                    tooltip="Remover documento"
                  />
                )}
              </div>
            </div>
          </Card>
        ))
      ) : (
        <p>Nenhum documento enviado ainda.</p>
      )}

      {canEdit && (
        <div style={{ marginTop: 20 }}>
          <h4>Novo documento</h4>

          {/* Upload de arquivo */}
          <div style={{ marginBottom: 16 }}>
            <label style={{ display: 'block', marginBottom: 4 }}>
              Upload de arquivo (png, jpeg, pdf, doc, docx, mp4, zip)
            </label>
            <FileUpload
              ref={fileUploadRef}
              mode="basic"
              name="file"
              accept=".png,.jpg,.jpeg,.pdf,.doc,.docx,.mp4,.zip"
              maxFileSize={50_000_000}
              customUpload
              uploadHandler={handleFileUpload}
              chooseLabel="Selecionar arquivo"
            />
          </div>

          {/* Cadastro por link (como já fazia) */}
          <div className="p-fluid p-formgrid p-grid">
            <div className="p-field p-col-12 p-md-5">
              <label>Nome</label>
              <InputText value={name} onChange={(e) => setName(e.target.value)} />
            </div>
            <div className="p-field p-col-12 p-md-3">
              <label>Tipo</label>
              <Dropdown
                value={type}
                options={typeOptions}
                onChange={(e) => setType(e.value)}
              />
            </div>
            <div className="p-field p-col-12 p-md-4">
              <label>URL (Drive, GitHub, vídeo...)</label>
              <InputText value={url} onChange={(e) => setUrl(e.target.value)} />
            </div>
          </div>
          <Button
            label={saving ? 'Salvando...' : 'Adicionar link'}
            icon="pi pi-plus"
            onClick={handleAdd}
            disabled={saving}
            className="mt-2"
          />
        </div>
      )}
    </div>
  );
};

export default ProjectDocuments;
