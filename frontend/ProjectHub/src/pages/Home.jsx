import React from 'react'
import '../style.css/HomePage.css'

const Home = () => {
  return (
    <div className='main'>
        {/* Seção de Apresentação */}
        <div className='section-apresentacao'>
          <div className="container">
            <h1>Compartilhe Seus <span className="highlight">Projetos Incríveis</span></h1>
            <h3>Plataforma universitária onde estudantes apresentam seus trabalhos, ideias e inovações para toda a comunidade acadêmica</h3>
            <div className="cta-buttons">
              <button className="btn-primary">Explorar Projetos</button>
              <button className="btn-secondary">Cadastrar Projeto</button>
            </div>
          </div>
        </div>

        {/* Seção de Estatísticas */}
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

        {/* Seção Por que usar o ProjectHub */}
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

        {/* Seção de Cards de Projetos em Destaque */}
        <div className='section cards'>
          <div className="container">
            <h2>Projetos em Destaque</h2>
            <div className="projects-grid">
              <div className="project-card">
                <div className="project-image"></div>
                <div className="project-content">
                  <h3>Sistema de Energia Solar para Comunidades Rurais</h3>
                  <p>Engenharia Elétrica - UFMG</p>
                  <div className="project-tags">
                    <span className="tag">Sustentabilidade</span>
                    <span className="tag">Energia Renovável</span>
                  </div>
                </div>
              </div>
              <div className="project-card">
                <div className="project-image"></div>
                <div className="project-content">
                  <h3>Aplicativo para Monitoramento de Saúde Mental</h3>
                  <p>Ciência da Computação - USP</p>
                  <div className="project-tags">
                    <span className="tag">Saúde</span>
                    <span className="tag">Tecnologia</span>
                  </div>
                </div>
              </div>
              <div className="project-card">
                <div className="project-image"></div>
                <div className="project-content">
                  <h3>Análise de Políticas Públicas para Educação Inclusiva</h3>
                  <p>Ciências Sociais - UnB</p>
                  <div className="project-tags">
                    <span className="tag">Educação</span>
                    <span className="tag">Políticas Públicas</span>
                  </div>
                </div>
              </div>
            </div>
            <div className="section-center">
              <button className="btn-outline">Ver Todos os Projetos</button>
            </div>
          </div>
        </div>

        {/* Seção Venha Conhecer */}
        <div className='section venha-conhecer'>
          <div className="container">
            <div className="cta-section">
              <h2>Venha fazer parte do ProjectHub</h2>
              <p>Junte-se a milhares de estudantes que já estão compartilhando suas ideias e inovações</p>
              <button className="btn-primary">Cadastre-se Agora</button>
            </div>
          </div>
        </div>
    </div>
  )
}

export default Home