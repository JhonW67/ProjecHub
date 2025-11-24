import React, { useState, useEffect } from 'react';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { Checkbox } from 'primereact/checkbox';
import { Password } from 'primereact/password';
import { Card } from 'primereact/card';
import { MultiSelect } from 'primereact/multiselect';
import { RadioButton } from 'primereact/radiobutton';
import { useNavigate } from 'react-router-dom';

import '../style.css/RegisterPage.css';



const Register = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [confirmError, setConfirmError] = useState('');
  const [aceito, setAceito] = useState(false);
  const [registration, setRegistration] = useState('');
  const [role, setRole] = useState('');
  const [courses, setCourses] = useState([]);
  const [semesters, setSemesters] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState('');
  const [selectedSemester, setSelectedSemester] = useState('');
  const [selectedCourses, setSelectedCourses] = useState([]);
  const [disciplinas, setDisciplinas] = useState([
  { label: 'Lógica Matemática', value: 'Lógica Matemática' },
  { label: 'Sistemas Embarcados', value: 'Sistemas Embarcados' },
  { label: 'Algoritmos e Lógica de Programação', value: 'Algoritmos e Lógica de Programação' },
  { label: 'Principios de Engenharia de Software', value: 'Principios de Engenharia de Software' },
  { label: 'Modelagem de Banco de Dados', value: 'Modelagem de Banco de Dados' },
  { label: 'Redes de Computadores', value: 'Redes de Computadores' },
  { label: 'Desenvolvimento Web', value: 'Desenvolvimento Web' },
  { label: 'Desenvolvimento Mobile', value: 'Desenvolvimento Mobile' },
  { label: 'Inteligência Artificial', value: 'Inteligência Artificial' },
  { label: 'Segurança da Informação', value: 'Segurança da Informação' },
  { label: 'Computação em Nuvem', value: 'Computação em Nuvem' },
  {label: 'Arquitetura de Software', value: 'Arquitetura de Software' },
  {label: 'Linguagem de Programação', value: 'Linguagem de Programação' },
  { label: 'Engenharia de Requisitos', value: 'Engenharia de Requisitos' },
  { label: 'Programação Orientada a Objetos', value: 'Programação Orientada a Objetos' },
  { label: 'Gerenciamento de Projetos de Software', value: 'Gerenciamento de Projetos de Software' },
  // ...adicione mais padrões
  ]);
  const [selectedSubjects, setSelectedSubjects] = useState([]); // para disciplinas MultiSelect
  const [customSubjectInput, setCustomSubjectInput] = useState('');
  const [customSubjects, setCustomSubjects] = useState([]); // para outras disciplina

  const navigate = useNavigate();

  const onSwitch = () => {
    navigate('/login');
  };
  useEffect(() => {
    fetch('http://localhost:8080/api/courses')
      .then(res => res.json())
      .then(setCourses);
    fetch('http://localhost:8080/api/semesters')
      .then(res => res.json())
      .then(setSemesters);
  }, []);

  function validatePassword(pass) {
  return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(pass);
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    setPasswordError('');
    setConfirmError('');
    
    if (!validatePassword(password)) {
      setPasswordError('A senha deve ter pelo menos uma letra, um número e 8 caracteres.');
      return;
    }
    if (password !== confirmPassword) {
      setConfirmError('As senhas precisam coincidir.');
      return;
    }
    if (!aceito) {
      alert('Aceite os termos.');
      return;
    }
    if (!role) {
      alert('Selecione o perfil.');
      return;
    }

    const data = {
        name,
        email,
        password,
        registration,
        role,
        courseId: selectedCourse,
        semesterId: selectedSemester,
        courseIds: selectedCourses, // cursos do professor
        subjects: selectedSubjects.concat(customSubjects), // todas as disciplinas
    };
    try {
      const response = await fetch('http://localhost:8080/api/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
      });
      if (response.ok) {
        alert("Cadastro realizado com sucesso!");
        // Alternar para login, limpar estado ou redirecionar se desejar
        onSwitch();
      } else {
        alert("Erro ao cadastrar usuário.");
      }
    } catch (err) {
      alert("Erro de conexão: " + err);
      }
  };


  return (
    <div className='container-registro' >
      {/* Imagem institucional à direita */}
      <div className='img-wrapper'>
        <div className='img-bg'>
          <img 
            src="../src/assets/img-register.png" 
            alt="Imagem-de-registro" 
           
          />
        </div>
      </div>

      {/* Card de registro à esquerda */}
      <div className='registro-bg'>
        <div className='card-register'>
          <Card 
            title="Registre-se" 
            subTitle="Bem-vindo ao ProjectHub"
            className="Card-register" 
            style={{ height: 'auto' }}         >
            <form onSubmit={handleSubmit} className='form-registro'>
              <InputText value={name} onChange={e => setName(e.target.value)} placeholder="Nome completo" className="mt-3" />
              <InputText value={email} 
              onChange={e => {
                setEmail(e.target.value);
                if (e.target.value && !/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(e.target.value)) {
                  setEmailError("Formato de email inválido.");
                }else {
                  setEmailError('');
                }
              }} 
              placeholder="Email" 
              className="mt-3" 
              />
              {emailError && <small style={{ color: 'red' }}>{emailError}</small>}
              <InputText value={registration} onChange={e => setRegistration(e.target.value)} placeholder="Matrícula ou ID" className="mt-3" />
              <div style={{ display: 'flex', gap: '16px', marginTop: '12px' }}>
                <RadioButton inputId="aluno" value="student" onChange={e => setRole(e.value)} checked={role === 'student'} />
                  <label htmlFor="aluno">Aluno</label>
                <RadioButton inputId="professor" value="teacher" onChange={e => setRole(e.value)} checked={role === 'teacher'} style={{marginLeft:"1rem"}}/>
                  <label htmlFor="professor">Professor</label>
              </div>
              {role === 'student' && (
                <>
                  <Dropdown
                      value={selectedCourse}
                      options={courses}
                      onChange={e => setSelectedCourse(e.value)}
                      optionLabel="name"
                      optionValue="courseId"
                      placeholder="Selecione o curso"
                      style={{ marginBottom: '1rem' }}
                      className="mt-3"
                  />
                  <Dropdown
                      value={selectedSemester}
                      options={semesters}
                      onChange={e => setSelectedSemester(e.value)}
                      optionLabel={option => `Semestre ${option.number}`}
                      optionValue="semesterId"
                      placeholder="Selecione o semestre"
                      style={{ marginBottom: '1rem' }}
                      className="mt-3"
                  />
                  <Password key='student' value={password} onChange={e => setPassword(e.target.value)} placeholder="Senha" toggleMask className="mb-3"/>
                    {passwordError && <small style={{ color: 'red', marginTop: 2 }}>{passwordError}</small>}
                  <Password key='student-confirm' value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} placeholder="Confirmar senha" toggleMask className="mb-3"/>
                    {confirmError && <small style={{ color: 'red', marginTop: 2 }}>{confirmError}</small>}
                </>
              )}  
              {role === 'teacher' && (
                <>
                  <MultiSelect
                    value={selectedCourses}
                    options={courses}
                    optionLabel="name"
                    optionValue="courseId"
                    onChange={e => setSelectedCourses(e.value)}
                    placeholder="Selecione os cursos"
                    className='mt-3'
                  />
                  <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
                    {(courses.filter(c => selectedCourses.includes(c.courseId)).map(c => (
                      <span key={c.courseId} style={{
                        background: '#b3dbfa',
                        color: '#01306e',
                        padding: '3px 12px',
                        borderRadius: 14,
                        display: 'flex',
                        alignItems: 'center'
                      }}>
                        {c.name}
                        <span
                          style={{
                            marginLeft: 8,
                            cursor: 'pointer',
                            color: '#01306e',
                            fontWeight: 'bold'
                          }}
                          onClick={() => setSelectedCourses(selectedCourses.filter(id => id !== c.courseId))}
                        >✕</span>
                      </span>
                    )))}
                  </div>
                  <MultiSelect
                    value={selectedSubjects}
                    options={disciplinas}
                    onChange={e => setSelectedSubjects(e.value)}
                    placeholder="Selecione as disciplinas"
                    className='mt-3'
                  />
                  <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 8 }}>
                    <InputText
                      value={customSubjectInput}
                      onChange={e => setCustomSubjectInput(e.target.value)}
                      placeholder="Outra disciplina"
                      style={{ flex: 1 }}
                      onKeyDown={e => {
                        if (e.key === 'Enter' && customSubjectInput.trim()) {
                          e.preventDefault();
                          if (
                            customSubjectInput.trim() &&
                            !customSubjects.includes(customSubjectInput.trim()) &&
                            !selectedSubjects.includes(customSubjectInput.trim())
                          ) {
                            setCustomSubjects([...customSubjects, customSubjectInput.trim()]);
                          }
                          setCustomSubjectInput('');
                        }
                      }}
                    />
                    <Button
                      type="button"
                      label="Adicionar"
                      onClick={() => {
                        if (
                          customSubjectInput.trim() &&
                          !customSubjects.includes(customSubjectInput.trim()) &&
                          !selectedSubjects.includes(customSubjectInput.trim())
                        ) {
                          setCustomSubjects([...customSubjects, customSubjectInput.trim()]);
                        }
                        setCustomSubjectInput('');
                      }}
                      disabled={!customSubjectInput.trim()}
                      className="p-button-sm"
                      style={{ minWidth: 90 }}
                    />
                  </div>
                  <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8, marginBottom: 10 }}>
                    {selectedSubjects.concat(customSubjects).map((subj, idx) => (
                      <span key={subj} style={{
                        background: '#2563eb', color: '#fff', padding: '3px 12px', borderRadius: 14, display: 'flex', alignItems: 'center'
                      }}>
                        {subj}
                        <span
                          style={{ marginLeft: 8, cursor: 'pointer', color: '#fff', fontWeight: 'bold' }}
                          onClick={() => {
                            if (customSubjects.includes(subj)) setCustomSubjects(customSubjects.filter(s => s !== subj));
                            if (selectedSubjects.includes(subj)) setSelectedSubjects(selectedSubjects.filter(s => s !== subj));
                          }}
                        >✕</span>
                      </span>
                    ))}
                  </div>
                  <Password key="teacher" value={password} onChange={e => setPassword(e.target.value)} placeholder="Senha" toggleMask className="mt-3"/>
                    {passwordError && <small style={{ color: 'red', marginTop: 2 }}>{passwordError}</small>}
                  <Password key="teacher-confirm" value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} placeholder="Confirmar senha" toggleMask className="mt-3"/>
                    {confirmError && <small style={{ color: 'red', marginTop: 2 }}>{confirmError}</small>}
                </>
              )}
              <Divider />
              <Checkbox inputId="aceito" checked={aceito} onChange={e => setAceito(e.checked)} className="mr-2" />
                <label htmlFor="aceito">Aceito os termos</label>
              <Button type='submit' label="Registrar" className="mt-3"/>
              <Divider />
              <Button
                type='button'
                label="Já tenho uma conta"
                onClick={onSwitch}
                className="p-button-link"
                style={{marginTop: '16px'}}
              />
            </form>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Register;
