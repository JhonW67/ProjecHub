import React from 'react'
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
  // const [email, setEmail] = useState("")
  // const [senha, setSenha] = useState("")
  // const [lembrar, setLembrar] = useState(false)
  const navigate = useNavigate()

  const subTitle = (
    
    <>
    <ButtonNoStyle  label={"Não tem uma conta?" } className="btn-criar-conta" onClick={() => navigate('/register')}/>
    </>
  );

  const footer = (
      <>
        <ButtonNoStyle label="Entrar" className='btn-entrar'/>
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

            <FloatLabel className='floatLabel-submit-email'>
              <InputText id="email"  keyfilter="email" className='text-submit-email' />
              <label htmlFor="email">Email</label>
            </FloatLabel>

            <FloatLabel>
              <Password toggleMask feedback={false} className='text-submit-senha'/>
              <label htmlFor="senha">Senha</label>
            </FloatLabel>
            <ButtonNoStyle label="Esqueceu a senha?" className="btn-esqueceu-senha" onClick={() => navigate('/register')}/>
          
          </Card>
        </div>
      </div>

    </div>
  )
}

export default Login
