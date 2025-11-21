import React, { useState } from 'react'; 
//importa menubar
import { Menubar } from 'primereact/menubar';
import { Badge } from 'primereact/badge';
import { Avatar } from 'primereact/avatar';  
//importa a logo do project hub
import logo from '../assets/projecthub-logo-icon.png'
//importa o userNavigate para troca de páginas
import { useNavigate } from 'react-router-dom';

import { Link } from 'react-router-dom';


export default function TemplateDemo() {

    const navigate = useNavigate(); // exporta os router para os links funcionarem
    const [menu, setMenu] = useState(null);

    const user = JSON.parse(localStorage.getItem('user'));
    const isLoggedIn = !!user;

    const items = [
        {
            label: 'Home',
            icon: 'pi pi-home',
            command: () => {
                navigate('/');
            }
        },
        {
            label: 'Projetos',
            icon: 'pi pi-search',
            command: () => {
                navigate('/projetos');
            }
        },
        ...(!isLoggedIn ? [   
         {
            label: 'Login',
            icon: 'pi pi-user',
            command: () => {
                navigate('/login');
            }
        },
        {
        label: 'Register',
        icon: 'pi pi-user-plus', 
        command: () => {
            navigate('/register');
        }
        }
        ] : [])
    ];

    const menuItems = [
       /* {
            label: 'Dashboard',
            icon: 'pi pi-th-large',
            command: () => navigate('/dashboard')
        },
        {
            label: 'Meus Dados',
            icon: 'pi pi-cog',
            command: () => navigate('/opcoes')
        },
        { separator: true },*/
        {
            label: 'Logout',
            icon: 'pi pi-sign-out',
            command: () => {
                // Limpa sessão/login (exemplo)
                localStorage.removeItem('user');
                localStorage.removeItem('token');
                window.location.reload();
            }
        }
    ];
    const end = isLoggedIn ? (
            <div className="flex align-items-center ml-4">
                <Menu model={menuItems} popup ref={el => setMenu(el)} id="avatar_menu" />
                <Avatar image="https://primefaces.org/cdn/primereact/images/avatar/amyelsner.png" shape="square" />
            </div>
    ): null;

    const start = (
        <div className="flex align-items-center">
            <div className='inline-flex align-items-center flex-none'>
                <Link to="/" className="link-nav" >
                    <img alt="logo" src={logo} height="50" className="logo-navbar" />
                </Link>
            </div>
        </div>
    );
     

    return (
        <div className="card">
            <Menubar model={items} start={start} end={end} />
        </div>
    )
}
        