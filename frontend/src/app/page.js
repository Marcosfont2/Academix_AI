'use client';

import { useState } from 'react';
import Navbar from './components/Navbar';
import CurriculoManager from './components/CurriculoManager';
import DashboardGraficos from './components/DashboardGraficos';
import ConselheiroIA from './components/ConselheiroIA';

export default function Home() {
  // O Estado que controla qual aba está ativa (começa no 'perfil')
  const [abaAtiva, setAbaAtiva] = useState('perfil');

  // Função que decide o que renderizar no meio da tela
  const renderizarConteudo = () => {
    switch (abaAtiva) {
      case 'perfil':
        return <CurriculoManager />;
      case 'graficos':
        return <DashboardGraficos />;
      case 'ia':
        return <ConselheiroIA />;
      default:
        return <CurriculoManager />;
    }
  };

  return (
    <div style={{ display: 'flex', minHeight: '100vh', backgroundColor: '#f4f7f6' }}>
      
      {/* ================= BARRA LATERAL (SIDEBAR) ================= */}
      <aside style={{ 
        width: '250px', 
        backgroundColor: '#1a1c23', 
        color: '#fff', 
        display: 'flex', 
        flexDirection: 'column' 
      }}>
        <div style={{ padding: '20px', fontSize: '22px', fontWeight: 'bold', borderBottom: '1px solid #2d313c', textAlign: 'center' }}>
          🎓 Academix AI
        </div>
        
        <nav style={{ display: 'flex', flexDirection: 'column', padding: '20px 0' }}>
          
          <button 
            onClick={() => setAbaAtiva('perfil')}
            style={estiloBotaoSidebar(abaAtiva === 'perfil')}
          >
            👤 Meu Perfil
          </button>
          
          <button 
            onClick={() => setAbaAtiva('graficos')}
            style={estiloBotaoSidebar(abaAtiva === 'graficos')}
          >
            📊 Dados Básicos
          </button>
          
          <button 
            onClick={() => setAbaAtiva('ia')}
            style={estiloBotaoSidebar(abaAtiva === 'ia')}
          >
            🤖 Conselheiro IA
          </button>

        </nav>
      </aside>

      {/* ================= ÁREA DE CONTEÚDO PRINCIPAL ================= */}
      <main style={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
        
        {/* Mantemos a sua Navbar no topo para mostrar o nome do usuário e o botão Sair */}
        <Navbar /> 
        
        <div style={{ padding: '30px', overflowY: 'auto' }}>
          {/* Aqui é onde a mágica acontece! O componente muda dinamicamente */}
          {renderizarConteudo()}
        </div>
        
      </main>
    </div>
  );
}

// Função auxiliar para pintar o botão da barra lateral dependendo se ele está ativo ou não
const estiloBotaoSidebar = (ativo) => ({
  padding: '15px 25px',
  backgroundColor: ativo ? '#0070f3' : 'transparent',
  color: ativo ? '#fff' : '#a0a5b1',
  border: 'none',
  textAlign: 'left',
  fontSize: '16px',
  cursor: 'pointer',
  transition: 'background 0.2s',
  borderLeft: ativo ? '4px solid #fff' : '4px solid transparent',
  marginBottom: '5px'
});