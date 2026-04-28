'use client';
import { useState, useEffect } from 'react';
import Roadmap from './Roadmap';

export default function CurriculoManager() {
  const [usuario, setUsuario] = useState(null);
  const [curriculoTexto, setCurriculoTexto] = useState('');
  const [carregando, setCarregando] = useState(false);

  useEffect(() => {
    const dados = localStorage.getItem('usuarioLogado');
    if (dados) {
      const user = JSON.parse(dados);
      setUsuario(user);
      fetchCurriculo(user.id);
    }
  }, []);

  const fetchCurriculo = async (id) => {
    try {
      const res = await fetch(`http://localhost:8080/api/auth/${id}/curriculo`);
      const texto = await res.text();
      setCurriculoTexto(texto);
    } catch (err) {
      console.error("Erro ao buscar currículo:", err);
    }
  };

  const handleFileUpload = async (e) => {
    const file = e.target.files[0];
    if (!file || !usuario) return;

    setCarregando(true);
    const formData = new FormData();
    formData.append('file', file);

    try {
      const res = await fetch(`http://localhost:8080/api/auth/${usuario.id}/upload-curriculo`, {
        method: 'POST',
        body: formData,
      });

      if (res.ok) {
        alert("Upload concluído!");
        fetchCurriculo(usuario.id);
        window.location.reload(); 
      }
    } catch (err) {
      console.error(err);
    } finally {
      setCarregando(false);
    }
  };

  if (!usuario) return <p style={{ color: '#000' }}>Faça login para gerenciar seu currículo.</p>;

  return (
    <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap', alignItems: 'flex-start' }}>
      
      {/* --- COLUNA ESQUERDA: Agora com 50% do espaço --- */}
      <div style={{ 
        flex: '1', // <-- Mantido como 1
        minWidth: '300px', 
        padding: '20px', 
        border: '1px solid #ddd', 
        borderRadius: '8px', 
        backgroundColor: '#fff',
        color: '#000'
      }}>
        <h3 style={{ color: '#000', marginBottom: '15px' }}>Meu Currículo Acadêmico</h3>
        
        <div style={{ marginBottom: '20px' }}>
          <label style={{ 
            display: 'block', 
            marginBottom: '10px', 
            fontWeight: 'bold',
            color: '#000' 
          }}>
            Atualizar Currículo (.xml simplificado):
          </label>
          <input 
            type="file" 
            accept=".xml" 
            onChange={handleFileUpload} 
            disabled={carregando}
            style={{ color: '#000', width: '100%' }} 
          />
          {carregando && <span style={{ color: '#000', display: 'block', marginTop: '10px' }}> Processando...</span>}
        </div>

        {/* Aumentei um pouco o maxHeight aqui para aproveitar o novo espaço vertical se necessário */}
        <div style={{ 
          backgroundColor: '#f9f9f9', 
          padding: '15px', 
          borderRadius: '5px', 
          whiteSpace: 'pre-wrap', 
          border: '1px solid #eee',
          minHeight: '100px',
          maxHeight: '500px', // <-- Aumentado de 300px para 500px
          overflowY: 'auto'  
        }}>
          <strong style={{ color: '#000' }}>Conteúdo Salvo no Banco:</strong>
          <p style={{ 
            fontFamily: 'monospace', 
            fontSize: '12px', 
            marginTop: '10px',
            color: '#000',
            lineHeight: '1.5'
          }}>
            {curriculoTexto || "Nenhum currículo enviado ainda."}
          </p>
        </div>
      </div>

      {/* --- COLUNA DIREITA: Agora com 50% do espaço --- */}
      <div style={{ 
        flex: '1', // <-- MUDOU DE '2' PARA '1'
        minWidth: '300px' // <-- MUDOU DE '400px' PARA '300px' para garantir simetria
      }}>
        <Roadmap userId={usuario.id} />
      </div>

    </div>
  );
}