import React, { useState, useRef } from 'react';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { FileUpload } from 'primereact/fileupload';

const ProjectHeader = ({ project, setProject, user, canEdit }) => {
  const [editVisible, setEditVisible] = useState(false);
  const [imageUrl, setImageUrl] = useState(project.imageUrl || '');
  const [groupName, setGroupName] = useState(project.groupName || '');
  const [bannerFile, setBannerFile] = useState(null);
  const fileUploadRef = useRef(null);

  const saveBasic = async () => {
    try {
      let updatedProject = project;

      // 1) Se tiver arquivo selecionado, faz upload do banner
      if (bannerFile) {
        const formData = new FormData();
        formData.append('file', bannerFile);

        const resBanner = await fetch(
          `http://localhost:8080/api/projects/${project.projectId}/banner`,
          { method: 'POST', body: formData }
        );
        if (!resBanner.ok) throw new Error('Erro ao atualizar banner');
        updatedProject = await resBanner.json();
        setProject(updatedProject);
        setImageUrl(updatedProject.imageUrl || '');
      }

      // 2) Atualiza infos básicas (groupName, etc.)
      const res = await fetch(
        `http://localhost:8080/api/projects/${project.projectId}/basic`,
        {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            imageUrl: updatedProject.imageUrl, // mantém a url que veio do backend
            groupName,
            memberIds: null
          })
        }
      );
      if (!res.ok) throw new Error('Erro ao atualizar projeto');
      const finalProject = await res.json();
      setProject(finalProject);
      setImageUrl(finalProject.imageUrl || '');
      setGroupName(finalProject.groupName || '');
      setBannerFile(null);
      if (fileUploadRef.current) fileUploadRef.current.clear();
      setEditVisible(false);
    } catch (err) {
      console.error(err);
      alert('Não foi possível atualizar os dados do projeto.');
    }
  };

  // Agora o uploadHandler só guarda o arquivo em memória
  const onBannerSelect = (e) => {
    const [file] = e.files;
    if (file) {
      setBannerFile(file);
    }
  };

  return (
    <>
      <div style={{ display: 'flex', alignItems: 'center', gap: 30, marginBottom: 28 }}>
        <img
          src={project.imageUrl || 'fallback-url.png'}
          alt="Banner"
          style={{
            width: 210,
            height: 210,
            borderRadius: 16,
            background: '#f3f6fa',
            objectFit: 'cover'
          }}
        />
        <div>
          <h1 style={{ margin: 0 }}>{project.title}</h1>
          <div style={{ margin: '12px 0' }}>
            <b>Grupo:</b> {project.groupName || 'Não definido'} <br />
            <b>Criado em:</b> {project.createdAt}
          </div>
          {canEdit && (
            <Button
              label="Editar cabeçalho"
              icon="pi pi-pencil"
              onClick={() => {
                setImageUrl(project.imageUrl || '');
                setGroupName(project.groupName || '');
                setBannerFile(null);
                setEditVisible(true);
              }}
            />
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
            <label>Arquivo do banner</label>
            <FileUpload
              ref={fileUploadRef}
              mode="basic"
              name="file"
              accept="image/png,image/jpeg"
              maxFileSize={5_000_000}
              customUpload
              uploadHandler={onBannerSelect} // agora só guarda o arquivo
              chooseLabel={bannerFile ? bannerFile.name : 'Selecionar banner'}
            />
          </div>
          <div className="field" style={{ marginTop: 12 }}>
            <label>Nome do grupo</label>
            <InputText
              value={groupName}
              onChange={(e) => setGroupName(e.target.value)}
            />
          </div>
          <div style={{ marginTop: 20, textAlign: 'right' }}>
            <Button
              label="Cancelar"
              className="p-button-text"
              onClick={() => setEditVisible(false)}
            />
            <Button label="Salvar" icon="pi pi-check" className="ml-2" onClick={saveBasic} />
          </div>
        </div>
      </Dialog>
    </>
  );
};

export default ProjectHeader;
