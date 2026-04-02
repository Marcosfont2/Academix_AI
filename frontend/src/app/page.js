'use client';

import GraficoGenero from './components/GraficoGenero';
import LattesUpload from './components/LattesUpload'; 
import GraficoTopInstituicoes from './components/GraficoTopInstituicoes';
import GraficoTiposArtigo from './components/GraficoTiposArtigo';
import GraficoAreasConhecimento from './components/GraficoAreasConhecimento';

export default function Home() {
  return (
    <main style={{ padding: '40px', maxWidth: '900px', margin: '0 auto' }}>
      <h1 style={{ textAlign: 'center', color: '#111' }}>Dashboard Academix AI</h1>
      <p style={{ textAlign: 'center', color: '#666', marginBottom: '40px' }}>
        Análise de dados de alto nível da plataforma Lattes e OpenAlex.
      </p>

      {/* Botão de Upload no topo */}
      <div style={{ marginBottom: '40px' }}>
         <LattesUpload />
      </div>
      
      {/* Grid para os gráficos ficarem organizados */}
      <div style={{ display: 'flex', flexDirection: 'column', gap: '40px' }}>
        <GraficoGenero />
        <GraficoTopInstituicoes />
        <GraficoTiposArtigo />
        <GraficoAreasConhecimento />
      </div>
      
    </main>
  );
}