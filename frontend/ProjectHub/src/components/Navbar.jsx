import React from 'react';
//importa menubar
import { Menubar } from 'primereact/menubar';
import { Badge } from 'primereact/badge';
import { Avatar } from 'primereact/avatar';  
//importa a logo do project hub
import logo from '../assets/projecthub-logo-icon.jpg'
//importa o userNavigate para troca de páginas
import { useNavigate } from 'react-router-dom';

import { Link } from 'react-router-dom';


export default function TemplateDemo() {

    const navigate = useNavigate(); // exporta os router para os links funcionarem

    const items = [
        {
            label: 'Home',
            icon: 'pi pi-home',
            command: () => {
                navigate('/home');
            }
        },
        {
            label: 'Projetos',
            icon: 'pi pi-search',
            command: () => {
                navigate('/projetos');
            }
        },
        {
            label: 'Login',
            icon: 'pi pi-user',
            command: () => {
                navigate('/login');
            }
        }
    ]

    const start = (
        <div className="flex align-items-center"> 
            <Link to="/home" className="flex align-items-center">
            <img alt="logo" src={logo} height="50"  className="mr-5 ml-6" ></img>
            </Link>
        </div>
    );
    const end = (
        <div className="flex align-items-center ml-4" >
            <Avatar image="https://primefaces.org/cdn/primereact/images/avatar/amyelsner.png" shape="square" />
        </div>
    );

    return (
        <div className="card">
            <Menubar model={items} start={start} end={end} />
        </div>
    )
}
        