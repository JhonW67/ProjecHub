import React, { useRef, useEffect, useState } from 'react';
import '../style.css/HomePage.css';
import { Button } from 'primereact/button';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const navigate = useNavigate();
  const targetRef = useRef(null);

  const [highlightProjects, setHighlightProjects] = useState([]);

  const scrollToElement = () => {
    targetRef.current?.scrollIntoView({
      behavior: 'smooth'
    });
  };

  useEffect(() => {
    // pega últimos 3 projetos (ou os 3 primeiros)
    fetch('http://localhost:8080/api/projects')
      .then(res => res.json())
      .then(data => {
        const sorted = [...data].sort(
          (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
        );
        setHighlightProjects(sorted.slice(0, 3));
      })
      .catch(err => {
        console.error('Erro ao carregar projetos em destaque', err);
      });
  }, []);

  return (
    <div className='main'>
      {/* Seção de Apresentação */}
      <div className='section-apresentacao'>
        <div className="container">
          <h1>Compartilhe Seus <span className="highlight">Projetos Incríveis</span></h1>
          <h3>Plataforma universitária onde estudantes apresentam seus trabalhos, ideias e inovações para toda a comunidade acadêmica</h3>
          <div className="cta-buttons">
            <Button className='btn-primary' label="Explorar Projetos" onClick={scrollToElement} />
            <Button className='btn-secondary' label="Cadastrar Projeto" onClick={() => navigate('/projetos')} />
          </div>
        </div>
      </div>

      {/* Seção de Estatísticas (mantém igual) */}
      <div className='section grid-estatisticas'>
        <div className="container">
          <h2>ProjectHub em Números</h2>
          <div className="stats-grid">
            <div className="stat-item">
              <div className="stat-number">1.200+</div>
              <div className="stat-label">Projetos Publicados</div>
            </div>
            <div className="stat-item">
              <div className="stat-number">850+</div>
              <div className="stat-label">Estudantes Ativos</div>
            </div>
            <div className="stat-item">
              <div className="stat-number">45+</div>
              <div className="stat-label">Universidades Parceiras</div>
            </div>
            <div className="stat-item">
              <div className="stat-number">32</div>
              <div className="stat-label">Áreas de Conhecimento</div>
            </div>
          </div>
        </div>
      </div>

      {/* Seção Por que usar o ProjectHub (mantém igual) */}
      <div className='section porque'>
        <div className="container">
          <h2>Por que usar o ProjectHub?</h2>
          <div className="benefits-grid">
            <div className="benefit-item">
              <div className="benefit-icon">👥</div>
              <h3>Visibilidade</h3>
              <p>Apresente seu trabalho para toda a comunidade acadêmica e aumente seu networking profissional.</p>
            </div>
            <div className="benefit-item">
              <div className="benefit-icon">💡</div>
              <h3>Inspiração</h3>
              <p>Encontre ideias inovadoras e conheça projetos de diferentes áreas do conhecimento.</p>
            </div>
            <div className="benefit-item">
              <div className="benefit-icon">🤝</div>
              <h3>Colaboração</h3>
              <p>Conecte-se com outros estudantes para desenvolver projetos multidisciplinares.</p>
            </div>
            <div className="benefit-item">
              <div className="benefit-icon">🏆</div>
              <h3>Reconhecimento</h3>
              <p>Destaque-se academicamente e ganhe visibilidade para oportunidades futuras.</p>
            </div>
          </div>
        </div>
      </div>

      {/* Seção de Cards de Projetos em Destaque (DINÂMICA) */}
      <div ref={targetRef} className='section cards'>
        <div className="container">
          <h2>Projetos em Destaque</h2>

          <div className="projects-grid">
            {highlightProjects.length === 0 && (
              <p style={{ color: '#64748b' }}>Nenhum projeto cadastrado ainda. Que tal ser o primeiro?</p>
            )}

            {highlightProjects.map(project => (
              <div
                key={project.projectId}
                className="project-card"
                onClick={() => navigate(`/projetos/${project.projectId}`)}
                style={{ cursor: 'pointer' }}
              >
                <div
                  className="project-image"
                  style={{
                    backgroundImage: project.imageUrl ? `url(${project.imageUrl})` : 'none',
                    backgroundSize: 'cover',
                    backgroundPosition: 'center'
                  }}
                />
                <div className="project-content">
                  <h3>{project.title}</h3>
                  <p>{project.groupName || 'Grupo não informado'}</p>
                  <div className="project-tags">
                    <span className="tag">{project.status || 'Em Andamento'}</span>
                    {project.event && <span className="tag">{project.event.name}</span>}
                  </div>
                </div>
              </div>
            ))}
          </div>

          <div className="section-center">
            <Button
              className='btn-outline'
              label="Ver Todos os Projetos"
              onClick={() => navigate('/projetos')}
            />
          </div>
        </div>
      </div>

      {/* Seção Venha Conhecer (mantém igual) */}
      <div className='section venha-conhecer'>
        <div className="container">
          <div className="cta-section">
            <h2>Venha fazer parte do ProjectHub</h2>
            <p>Junte-se a milhares de estudantes que já estão compartilhando suas ideias e inovações</p>
            <Button className='btn-primary' label="Cadastre-se Agora" onClick={() => navigate('/register')} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
