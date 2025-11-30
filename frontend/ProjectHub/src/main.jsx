import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'primereact/resources/themes/lara-light-blue/theme.css';  // tema
import 'primereact/resources/primereact.min.css';                // css base
import 'primeicons/primeicons.css';       
import 'primeflex/primeflex.css'; //  classes utilitárias (flex, gap, etc)
import App from './App.jsx';




createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)

// Registro do service worker 
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('/sw.js')
      .then(() => console.log('✅ Conexão com o serviceWorker estabelecida ✅'))
      .catch(error => console.log('❌ Conexão com o serviceWorker falha ❌', error));
  });
}



