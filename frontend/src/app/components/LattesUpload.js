'use client';

import React, { useState } from 'react';

export default function LattesUpload() {
  const [file, setFile] = useState(null);
  const [status, setStatus] = useState({ message: '', type: '' });

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = async (e) => {
    e.preventDefault();
    if (!file) {
      setStatus({ message: "Por favor, selecione um arquivo primeiro!", type: "error" });
      return;
    }

    // Criando o FormData para enviar ao backend Java
    const formData = new FormData();
    formData.append('lattes', file);

    try {
      // Apontando para o seu Controller do Spring Boot
      const response = await fetch('http://localhost:8080/api/upload', {
        method: 'POST',
        body: formData,
      });

      if (response.ok) {
        setStatus({ message: "Upload realizado com sucesso no backend!", type: "success" });
      } else {
        throw new Error("Erro no servidor");
      }
    } catch (err) {
      setStatus({ message: "Erro ao enviar o arquivo. Tente novamente.", type: "error" });
    }
  };

  return (
    <div style={{ padding: '20px', border: '1px solid #ccc', borderRadius: '8px', backgroundColor: '#fff' }}>
      <h3 style={{ marginTop: 0, color: '#333' }}>Upload de Currículo Lattes (.xml)</h3>
      <form onSubmit={handleUpload} style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
        <input 
          type="file" 
          accept=".xml" 
          onChange={handleFileChange} 
          style={{ padding: '5px' }}
        />
        <button 
          type="submit" 
          style={{ 
            padding: '8px 16px', 
            backgroundColor: '#0070f3', 
            color: 'white', 
            border: 'none', 
            borderRadius: '4px', 
            cursor: 'pointer' 
          }}
        >
          Enviar
        </button>
      </form>

      {status.message && (
        <p style={{ 
          color: status.type === 'success' ? '#00701a' : '#d32f2f', 
          marginTop: '15px',
          fontWeight: '500'
        }}>
          {status.message}
        </p>
      )}
    </div>
  );
}