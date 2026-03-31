'use client';

import GraficoGenero from './components/GraficoGenero';
import LattesUpload from './components/LattesUpload'; 

export default function Home() {
  return (
    <main style={{ padding: '40px', maxWidth: '800px', margin: '0 auto' }}>
      <h1>Dashboard Academix AI</h1>
      <p>Análise de dados de alto nível da plataforma Lattes.</p>
      
      {/* Botão de Upload no topo */}
      <div style={{ marginBottom: '40px' }}>
         <LattesUpload />
      </div>
      
      {/* Gráfico logo abaixo */}
      <GraficoGenero />
      
    </main>
  );
}