import { useState } from 'react';
import { CSSTransition, SwitchTransition } from 'react-transition-group';
import Register from '../Register.jsx';
import Login from '../Login.jsx';

const AuthPageSwitcher = () => {
  const [page, setPage] = useState('register'); // 'login' ou 'register'

  return (
    <SwitchTransition>
      <CSSTransition
        key={page}
        timeout={500}
        classNames="slide"
      >
        {page === 'register' ? (
          <Register onSwitch={() => setPage('login')} />
        ) : (
          <Login onSwitch={() => setPage('register')} />
        )}
      </CSSTransition>
    </SwitchTransition>
  );
};

export default AuthPageSwitcher;
