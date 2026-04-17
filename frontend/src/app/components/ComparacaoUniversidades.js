"use client";

import React, { useState } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, RadarChart, PolarGrid, PolarAngleAxis, PolarRadiusAxis, Radar } from 'recharts';
import { BookOpen, Users, GraduationCap, Award } from 'lucide-react';

export default function ComparacaoUniversidades() {
  const [univA, setUnivA] = useState('Universidade Federal do Rio de Janeiro');
  const [univB, setUnivB] = useState('Universidade Federal de São Paulo');
  const [dadosA, setDadosA] = useState(null);
  const [dadosB, setDadosB] = useState(null);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState(null);

  // Lista pré-definida para facilitar o teste
  const listaUniversidades = [
    "Universidade Federal do Rio de Janeiro",
    "Universidade Federal de São Paulo",
    "Universidade de São Paulo",
    "Universidade Federal do Rio Grande do Norte"
  ];

  const buscarDados = async () => {
    setLoading(true);
    setErro(null);
    try {
      // Faz as duas requisições ao mesmo tempo (Promise.all é mais rápido!)
      const [resA, resB] = await Promise.all([
        fetch(`http://localhost:8080/api/comparacao?universidade=${encodeURIComponent(univA)}`),
        fetch(`http://localhost:8080/api/comparacao?universidade=${encodeURIComponent(univB)}`)
      ]);

      if (!resA.ok || !resB.ok) throw new Error("Erro ao buscar dados na API.");

      // Trata o nosso famoso 204 No Content
      const dataA = resA.status === 204 ? null : await resA.json();
      const dataB = resB.status === 204 ? null : await resB.json();

      setDadosA(dataA);
      setDadosB(dataB);
    } catch (err) {
      setErro("Falha de comunicação com o servidor. Verifique se o backend está rodando.");
    } finally {
      setLoading(false);
    }
  };

  // Função auxiliar para juntar dados das duas faculdades no mesmo gráfico
  const mesclarDadosDemografia = (listaA, listaB) => {
    const mapa = new Map();
    
    listaA?.forEach(item => {
      mapa.set(item.categoria, { nome: item.categoria, [univA]: item.porcentagem });
    });
    
    listaB?.forEach(item => {
      const existente = mapa.get(item.categoria) || { nome: item.categoria };
      existente[univB] = item.porcentagem;
      mapa.set(item.categoria, existente);
    });

    return Array.from(mapa.values());
  };

  return (
    <div className="p-8 min-h-screen bg-slate-50 text-slate-800">
      <div className="max-w-7xl mx-auto">
        
        {/* CABEÇALHO E CONTROLES */}
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-100 mb-8">
          <h1 className="text-3xl font-bold text-slate-900 mb-6 text-center">Painel Comparativo de Instituições</h1>
          
          <div className="flex flex-col md:flex-row gap-4 items-end justify-center">
            <div className="w-full md:w-1/3">
              <label className="block text-sm font-semibold text-indigo-600 mb-2">Instituição A (Azul)</label>
              <select 
                value={univA} 
                onChange={(e) => setUnivA(e.target.value)}
                className="w-full p-3 rounded-lg border border-slate-200 focus:ring-2 focus:ring-indigo-500 outline-none transition-all"
              >
                {listaUniversidades.map(u => <option key={u} value={u}>{u}</option>)}
              </select>
            </div>

            <div className="w-full md:w-1/3">
              <label className="block text-sm font-semibold text-emerald-600 mb-2">Instituição B (Verde)</label>
              <select 
                value={univB} 
                onChange={(e) => setUnivB(e.target.value)}
                className="w-full p-3 rounded-lg border border-slate-200 focus:ring-2 focus:ring-emerald-500 outline-none transition-all"
              >
                {listaUniversidades.map(u => <option key={u} value={u}>{u}</option>)}
              </select>
            </div>

            <button 
              onClick={buscarDados}
              disabled={loading}
              className="w-full md:w-auto px-8 py-3 bg-slate-900 text-white font-bold rounded-lg hover:bg-slate-800 transition-all disabled:opacity-50"
            >
              {loading ? "Processando..." : "Comparar Dados"}
            </button>
          </div>
          {erro && <p className="text-red-500 text-center mt-4 font-medium">{erro}</p>}
        </div>

        {/* ÁREA DE RESULTADOS */}
        {dadosA && dadosB && (
          <div className="space-y-8 animate-in fade-in duration-500">
            
            {/* CARDS DE IMPACTO GERAL (KPIs) */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              {/* Card Univ A */}
              <div className="bg-white p-6 rounded-2xl shadow-sm border-t-4 border-t-indigo-500">
                <h2 className="text-xl font-bold text-slate-800 mb-4">{dadosA.nomeUniversidade}</h2>
                <div className="grid grid-cols-2 gap-4">
                  <div className="p-4 bg-indigo-50 rounded-xl">
                    <div className="flex items-center gap-2 text-indigo-600 mb-1"><BookOpen size={18}/> Artigos</div>
                    <p className="text-2xl font-black">{dadosA.totalArtigos?.toLocaleString('pt-BR') || 0}</p>
                  </div>
                  <div className="p-4 bg-indigo-50 rounded-xl">
                    <div className="flex items-center gap-2 text-indigo-600 mb-1"><Award size={18}/> Citações</div>
                    <p className="text-2xl font-black">{dadosA.totalCitacoes?.toLocaleString('pt-BR') || 0}</p>
                  </div>
                  <div className="p-4 bg-indigo-50 rounded-xl">
                    <div className="flex items-center gap-2 text-indigo-600 mb-1"><Users size={18}/> Docentes</div>
                    <p className="text-2xl font-black">{dadosA.totalDocentes?.toLocaleString('pt-BR') || 0}</p>
                  </div>
                  <div className="p-4 bg-indigo-50 rounded-xl">
                    <div className="flex items-center gap-2 text-indigo-600 mb-1"><GraduationCap size={18}/> Egressos</div>
                    <p className="text-2xl font-black">{dadosA.totalFormados?.toLocaleString('pt-BR') || 0}</p>
                  </div>
                </div>
              </div>

              {/* Card Univ B */}
              <div className="bg-white p-6 rounded-2xl shadow-sm border-t-4 border-t-emerald-500">
                <h2 className="text-xl font-bold text-slate-800 mb-4">{dadosB.nomeUniversidade}</h2>
                <div className="grid grid-cols-2 gap-4">
                  <div className="p-4 bg-emerald-50 rounded-xl">
                    <div className="flex items-center gap-2 text-emerald-600 mb-1"><BookOpen size={18}/> Artigos</div>
                    <p className="text-2xl font-black">{dadosB.totalArtigos?.toLocaleString('pt-BR') || 0}</p>
                  </div>
                  <div className="p-4 bg-emerald-50 rounded-xl">
                    <div className="flex items-center gap-2 text-emerald-600 mb-1"><Award size={18}/> Citações</div>
                    <p className="text-2xl font-black">{dadosB.totalCitacoes?.toLocaleString('pt-BR') || 0}</p>
                  </div>
                  <div className="p-4 bg-emerald-50 rounded-xl">
                    <div className="flex items-center gap-2 text-emerald-600 mb-1"><Users size={18}/> Docentes</div>
                    <p className="text-2xl font-black">{dadosB.totalDocentes?.toLocaleString('pt-BR') || 0}</p>
                  </div>
                  <div className="p-4 bg-emerald-50 rounded-xl">
                    <div className="flex items-center gap-2 text-emerald-600 mb-1"><GraduationCap size={18}/> Egressos</div>
                    <p className="text-2xl font-black">{dadosB.totalFormados?.toLocaleString('pt-BR') || 0}</p>
                  </div>
                </div>
              </div>
            </div>

            {/* SESSÃO DE GRÁFICOS */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
              
              {/* Gráfico 1: Comparação de Sexo */}
              <div className="bg-white p-6 rounded-2xl shadow-sm">
                <h3 className="text-lg font-bold text-slate-800 mb-6 text-center">Distribuição por Sexo (%)</h3>
                <div className="h-80 w-full">
                  <ResponsiveContainer width="100%" height="100%">
                    <BarChart data={mesclarDadosDemografia(dadosA.distribuicaoSexo, dadosB.distribuicaoSexo)} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
                      <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#e2e8f0"/>
                      <XAxis dataKey="nome" axisLine={false} tickLine={false} />
                      <YAxis axisLine={false} tickLine={false} tickFormatter={(value) => `${value}%`} />
                      <Tooltip cursor={{fill: '#f1f5f9'}} formatter={(value) => `${value}%`} />
                      <Legend iconType="circle" />
                      <Bar dataKey={univA} name="Inst. A" fill="#6366f1" radius={[4, 4, 0, 0]} />
                      <Bar dataKey={univB} name="Inst. B" fill="#10b981" radius={[4, 4, 0, 0]} />
                    </BarChart>
                  </ResponsiveContainer>
                </div>
              </div>

              {/* Gráfico 2: Comparação de Raça/Cor */}
              <div className="bg-white p-6 rounded-2xl shadow-sm">
                <h3 className="text-lg font-bold text-slate-800 mb-6 text-center">Distribuição por Raça/Cor (%)</h3>
                <div className="h-80 w-full">
                  <ResponsiveContainer width="100%" height="100%">
                    <BarChart data={mesclarDadosDemografia(dadosA.distribuicaoRaca, dadosB.distribuicaoRaca)} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
                      <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#e2e8f0"/>
                      <XAxis dataKey="nome" axisLine={false} tickLine={false} />
                      <YAxis axisLine={false} tickLine={false} tickFormatter={(value) => `${value}%`} />
                      <Tooltip cursor={{fill: '#f1f5f9'}} formatter={(value) => `${value}%`} />
                      <Legend iconType="circle" />
                      <Bar dataKey={univA} name="Inst. A" fill="#6366f1" radius={[4, 4, 0, 0]} />
                      <Bar dataKey={univB} name="Inst. B" fill="#10b981" radius={[4, 4, 0, 0]} />
                    </BarChart>
                  </ResponsiveContainer>
                </div>
              </div>

            </div>
          </div>
        )}
      </div>
    </div>
  );
}