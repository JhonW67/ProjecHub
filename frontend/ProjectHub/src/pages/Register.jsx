import React from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { Checkbox } from 'primereact/checkbox';
import { Password } from 'primereact/password';
import { Card } from 'primereact/card';
import '../style.css/LoginPage.css';

const Register = () => {
  return (
    <div className='content' style={{ 
      display: 'flex', 
    flexDirection: 'row-reverse', 
    minHeight: '100vh', 
    background: 'rgba(1, 48, 110)', 
    paddingTop: '70px' 
    }}>
      {/* Imagem institucional à direita */}
      <div>
        <div style={{ flex: 1, height: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <img 
            src="../src/assets/imagem-login.png" 
            alt="Imagem-de-registro" 
            style={{
              width: '100%',
              height: '100%',
              objectFit: 'cover' 
            }}
          />
        </div>
      </div>

      {/* Card de registro à esquerda */}
      <div>
        <div style={{ flex: 1, display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
          <Card 
            title="Registro" 
            subTitle="Bem-vindo ao ProjectHub"
            style={{
              width: '450px', // aumenta largura
              height: '80vh', // aumenta altura
              boxShadow: '0 4px 24px rgba(0,0,0,0.08)'
            }} 
            className="md:w-30rem h-auto"
          >
            <div className="p-fluid">
              <InputText placeholder="Nome completo" className="mb-3"/>
              <InputText placeholder="Email" className="mb-3"/>
              <InputText placeholder="CPF" className="mb-3"/>
              <InputText placeholder="Matrícula/ID" className="mb-3"/>
              <InputText placeholder="Curso" className="mb-3"/>
              <InputText placeholder="Semestre" className="mb-3"/>
              <Password placeholder="Senha" toggleMask className="mb-3"/>
              <Password placeholder="Confirmar senha" toggleMask className="mb-3"/>
              <Divider />
              <Checkbox inputId="aceito" className="mr-2" />
              <label htmlFor="aceito">Aceito os termos</label>
              <Button label="Registrar" className="mt-3"/>
              <Divider />
              <a href="/login">Já tenho uma conta</a>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Register;
