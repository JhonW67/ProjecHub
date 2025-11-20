import './App.css'

//importa rotas
import { BrowserRouter, Routes, Route } from "react-router-dom";

//importa componentes
import Navbar from './components/Navbar'

//importa páginas
import Home from './pages/Home'
import Login from './pages/Login'
import Projetos from './pages/Projetos'
import Register from './pages/Register';
import AuthPageSwitcher from './pages/components/AuthPageSwitcher';
function App() {

  return (
    <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route path="/" element={<Home />} />        {/* Página inicial */}
        <Route path="/Login" element={<Login />} />  {/* Página de login */}
        <Route path="/Projetos" element={<Projetos />} />  {/* Página de Projetos */}
        <Route path="/Register" element={<Register />} />  {/* Página de Registro */}
      </Routes>
    </BrowserRouter>
  );
}

export default App
