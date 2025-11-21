import React, { useState }from 'react'
import { InputText } from 'primereact/inputtext';
import ButtonNoStyle  from '../components/ButtonNoStyle';
import { Divider } from 'primereact/divider';
import { Checkbox } from 'primereact/checkbox'
import { Password } from "primereact/password";
import { Card } from "primereact/card";
import { FloatLabel } from 'primereact/floatlabel';
import { useNavigate } from 'react-router-dom';

import '../style.css/LoginPage.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [erro, setErro] = useState('');
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleLogin = async () => {
      setErro('');
      setLoading(true);

      try {
        const response = await fetch('http://localhost:8080/api/auth/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email, password: senha })
        });
        if (response.ok) {
          const data = await response.json();
          localStorage.setItem('token', data.token); // salva o token
          window.location.href = '/'; // redirecione como desejar
        } else {
          const error = await response.json();
          setErro(error.error || 'Email ou senha inválidos.');
        }
      } catch (err) {
        console.error(err);
        setErro('Erro na conexão com o servidor.');
      } finally {
        setLoading(false);
      }
    };
  const subTitle = (
    <>
      <ButtonNoStyle
        className="btn-criar-conta"
        label={"Não tem uma conta?"}
        onClick={() => navigate('/register')}
      />
    </>
  );

  const footer = (
      <>
        <ButtonNoStyle 
        label={loading ? "Entrando..." : "Entrar"} 
        className='btn-entrar' 
        onClick={handleLogin} 
        disabled={loading} 
        />
      </>
    );
  
  return (
    <div className='content'> {/*div content */}

      <div className='div-wrapper-img'>  {/*div imagem página de login */}
        <div className='div-img-login'>
          <img src="../src/assets/imagem-login.png" alt="Imagem-de-login" className='img-login'/>
        </div>
      </div>


      <div>  {/*div formulário login */}
        <div className='div-card-login'>
          <Card title="Login" subTitle={subTitle} footer={footer} header={""} className="card-login">
            <form onSubmit={e => { e.preventDefault(); handleLogin(); }}>
            <FloatLabel className='floatLabel-submit-email'>
              <InputText
                id="email"
                keyfilter="email"
                className='text-submit-email'
                value={email}
                onChange={e => setEmail(e.target.value)}
              />
              <label htmlFor="email">Email</label>
            </FloatLabel>
            <FloatLabel>
              <Password
                toggleMask
                feedback={false}
                className='text-submit-senha'
                value={senha}
                onChange={e => setSenha(e.target.value)}
              />
              <label htmlFor="senha">Senha</label>
            </FloatLabel>
            {erro && <div style={{ color: 'red', margin: '10px 0' }}>{erro}</div>}
            <ButtonNoStyle label="Esqueceu a senha?" className="btn-esqueceu-senha" onClick={() => navigate('/register')}/>
            </form>
          </Card>
        </div>
      </div>

    </div>
  )
}

export default Login