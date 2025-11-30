import React, { useState } from 'react';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';

const FRONT_BASE_URL = 'http://localhost:5173'; // ajuste se mudar

const ProjectQrCode = ({ project, setProject, canEdit }) => {
  const [loading, setLoading] = useState(false);
  const projectUrl = `${FRONT_BASE_URL}/Projetos/${project.projectId}`;

  const handleGenerate = async () => {
    try {
      setLoading(true);
      const res = await fetch(`http://localhost:8080/api/projects/${project.projectId}/qrcode`, {
        method: 'POST'
      });
      if (!res.ok) throw new Error('Erro ao gerar QR Code');

      const updated = await res.json(); // ProjectDetailDTO atualizado
      setProject(updated);
    } catch (err) {
      console.error(err);
      alert('Não foi possível gerar o QR Code.');
    } finally {
      setLoading(false);
    }
  };

  const copyToClipboard = async () => {
    try {
      await navigator.clipboard.writeText(projectUrl);
      alert('Link copiado para a área de transferência.');
    } catch (err) {
      console.error(err);
      alert('Não foi possível copiar o link.');
    }
  };

  return (
    <div style={{ textAlign: 'center', marginTop: 24 }}>
      {project.qrCodeUrl ? (
        <>
          <img
            src={project.qrCodeUrl}
            alt="QR Code"
            style={{ width: 220, height: 220, marginBottom: 12 }}
          />
          <p>Escaneie para acessar esta página direto.</p>
          {canEdit && (
            <Button
              label={loading ? 'Atualizando...' : 'Regenerar QR Code'}
              icon="pi pi-refresh"
              className="p-button-text"
              onClick={handleGenerate}
              disabled={loading}
            />
          )}
        </>
      ) : (
        <>
          <p>QR Code ainda não foi gerado para este projeto.</p>
          {canEdit && (
            <Button
              label={loading ? 'Gerando...' : 'Gerar QR Code'}
              icon="pi pi-qrcode"
              onClick={handleGenerate}
              disabled={loading}
            />
          )}
        </>
      )}

      <div style={{ marginTop: 24, maxWidth: 520, marginLeft: 'auto', marginRight: 'auto' }}>
        <p style={{ marginBottom: 8 }}>Link direto para este projeto:</p>
        <div style={{ display: 'flex', gap: 8 }}>
          <InputText
            value={projectUrl}
            readOnly
            style={{ flex: 1 }}
          />
          <Button
            label="Copiar"
            icon="pi pi-copy"
            onClick={copyToClipboard}
          />
        </div>
      </div>
    </div>
  );
};

export default ProjectQrCode;
