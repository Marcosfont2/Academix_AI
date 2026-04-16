'use client';

import { useState, useEffect } from 'react';

export default function ConselheiroIA() {
  const [usuario, setUsuario] = useState(null);
  const [conselho, setConselho] = useState('');
  const [carregando, setCarregando] = useState(false);

  useEffect(() => {
    const dadosSalvos = localStorage.getItem('usuarioLogado');
    if (dadosSalvos) {
      setUsuario(JSON.parse(dadosSalvos));
    }
  }, []);

  const solicitarConselho = async () => {
    if (!usuario) return;
    
    setCarregando(true);
    setConselho(''); // Limpa o conselho anterior

    try {
      const res = await fetch(`http://localhost:8080/api/ia/conselho/${usuario.id}`);
      const data = await res.json();
      setConselho(data.resposta);
    } catch (erro) {
      console.error("Erro ao chamar IA:", erro);
      setConselho("Erro de conexão com o servidor.");
    } finally {
      setCarregando(false);
    }
  };

  if (!usuario) {
    return <div style={{ color: '#000', padding: '20px' }}>Faça login para acessar a IA.</div>;
  }

  return (
    <div style={{ padding: '30px', backgroundColor: '#fff', borderRadius: '8px', border: '1px solid #ddd' }}>
      <h2 style={{ color: '#0070f3', marginBottom: '15px' }}>🤖 Conselheiro de Carreira (IA)</h2>
      
      <p style={{ color: '#555', fontSize: '16px', marginBottom: '20px' }}>
        A IA irá analisar o currículo que você salvou na aba "Meu Perfil" e te dar dicas personalizadas de carreira.
      </p>

      <button 
        onClick={solicitarConselho} 
        disabled={carregando}
        style={{
          padding: '12px 24px',
          backgroundColor: carregando ? '#ccc' : '#0070f3',
          color: '#fff',
          border: 'none',
          borderRadius: '5px',
          cursor: carregando ? 'not-allowed' : 'pointer',
          fontWeight: 'bold',
          fontSize: '16px',
          marginBottom: '20px'
        }}
      >
        {carregando ? '⏳ Analisando seu currículo...' : '✨ Gerar Conselho Acadêmico'}
      </button>

      {conselho && (
        <div style={{
          backgroundColor: '#f9f9f9',
          padding: '20px',
          borderRadius: '8px',
          borderLeft: '4px solid #0070f3',
          color: '#333',
          lineHeight: '1.6',
          whiteSpace: 'pre-wrap' // Isso garante que as quebras de linha da IA funcionem
        }}>
          {conselho}
        </div>
      )}
    </div>
  );
}