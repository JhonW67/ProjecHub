import React from 'react'
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { Checkbox } from 'primereact/checkbox'
import { Password } from "primereact/password";
import { Card } from "primereact/card";
import '../style.css/LoginPage.css';

const Login = () => {
  // const [email, setEmail] = useState("")
  // const [senha, setSenha] = useState("")
  // const [lembrar, setLembrar] = useState(false)
  
  return (
    <div className='content'> {/*div content */}

      <div>  {/*div imagem página de login */}
        <div>
          <img src="../src/assets/imagem-login.png" alt="Imagem-de-login" className='h-30rem'/>
        </div>
      </div>


      <div>  {/*div formulário login */}
        <div>
          <Card title="Login" subTitle="Card subtitle" footer={""} header={""} className="md:w-30rem h-auto">
          </Card>
        </div>
      </div>

    </div>
  )
}

export default Login
