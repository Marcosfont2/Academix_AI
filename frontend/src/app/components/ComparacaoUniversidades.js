"use client";

import React, { useState, useEffect } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { BookOpen, Users, GraduationCap, Award, Search } from 'lucide-react';

// =========================================================================
// 1. COMPONENTE DE BUSCA DINÂMICA
// =========================================================================
const BuscadorUniversidade = ({ label, corHex, valor, setValor }) => {
  const [termo, setTermo] = useState(valor);
  const [sugestoes, setSugestoes] = useState([]);
  const [mostrarLista, setMostrarLista] = useState(false);

  useEffect(() => {
    const delayDebounce = setTimeout(async () => {
      // Só busca se tiver 3 ou mais letras e se o termo for diferente do que já está selecionado
      if (termo.length >= 3 && termo !== valor) {
        try {
          const res = await fetch(`http://localhost:8080/api/comparacao/sugestoes?termo=${encodeURIComponent(termo)}`);
          if (res.ok) {
            const data = await res.json();
            setSugestoes(data);
            setMostrarLista(true);
          }
        } catch (err) {
          console.error("Erro ao buscar sugestões", err);
        }
      } else {
        setSugestoes([]);
        setMostrarLista(false);
      }
    }, 300);

    return () => clearTimeout(delayDebounce);
  }, [termo, valor]);

  const selecionarSugestao = (nome) => {
    setTermo(nome);
    setValor(nome); // Atualiza o estado da tela principal
    setMostrarLista(false);
  };

  return (
    <div className="relative w-full">
      <label className="block text-sm font-semibold mb-2" style={{ color: corHex }}>{label}</label>
      <div className="relative">
        <input
          type="text"
          value={termo}
          onChange={(e) => setTermo(e.target.value)}
          onFocus={() => termo.length >= 3 && setMostrarLista(true)}
          placeholder="Digite o nome da instituição..."
          className="w-full p-3 pl-10 rounded-lg border border-slate-200 focus:ring-2 outline-none transition-all"
          style={{ focusRing: corHex }}
        />
        <Search className="absolute left-3 top-3 text-slate-400" size={20} />
      </div>

      {mostrarLista && sugestoes.length > 0 && (
        <ul className="absolute z-20 w-full mt-1 bg-white border border-slate-200 rounded-lg shadow-xl max-h-60 overflow-y-auto">
          {sugestoes.map((sugestao, idx) => (
            <li 
              key={idx} 
              onClick={() => selecionarSugestao(sugestao)}
              className="p-3 hover:bg-slate-50 cursor-pointer border-b border-slate-100 last:border-0 text-sm text-slate-700"
            >
              {sugestao}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

// =========================================================================
// 2. TELA PRINCIPAL DE COMPARAÇÃO
// =========================================================================
export default function ComparacaoUniversidades() {
  const [univA, setUnivA] = useState('Universidade Federal do Rio de Janeiro');
  const [univB, setUnivB] = useState('Universidade Federal de São Paulo');
  const [dadosA, setDadosA] = useState(null);
  const [dadosB, setDadosB] = useState(null);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState(null);

  const buscarDados = async () => {
    setLoading(true);
    setErro(null);
    try {
      const [resA, resB] = await Promise.all([
        fetch(`http://localhost:8080/api/comparacao?universidade=${encodeURIComponent(univA)}`),
        fetch(`http://localhost:8080/api/comparacao?universidade=${encodeURIComponent(univB)}`)
      ]);

      if (!resA.ok || !resB.ok) throw new Error("Erro ao buscar dados na API.");

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

  // Função já corrigida para suportar nomes de universidades idênticos
  const mesclarDadosDemografia = (listaA, listaB) => {
    const mapa = new Map();
    
    listaA?.forEach(item => {
      mapa.set(item.categoria, { nome: item.categoria, valorA: item.porcentagem });
    });
    
    listaB?.forEach(item => {
      const existente = mapa.get(item.categoria) || { nome: item.categoria };
      existente.valorB = item.porcentagem;
      mapa.set(item.categoria, existente);
    });

    return Array.from(mapa.values());
  };

  return (
    <div className="p-8 min-h-screen bg-slate-50 text-slate-800">
      <div className="max-w-7xl mx-auto">
        
        {/* CABEÇALHO E CONTROLES DE BUSCA */}
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-100 mb-8">
          <h1 className="text-3xl font-bold text-slate-900 mb-6 text-center">Painel Comparativo de Instituições</h1>
          
          <div className="flex flex-col md:flex-row gap-6 items-end justify-center">
            <div className="w-full md:w-2/5">
              <BuscadorUniversidade 
                label="Instituição A (Azul)" 
                corHex="#6366f1" 
                valor={univA} 
                setValor={setUnivA} 
              />
            </div>

            <div className="w-full md:w-2/5">
              <BuscadorUniversidade 
                label="Instituição B (Verde)" 
                corHex="#10b981" 
                valor={univB} 
                setValor={setUnivB} 
              />
            </div>

            <button 
              onClick={buscarDados}
              disabled={loading}
              className="w-full md:w-1/5 px-8 py-3 bg-slate-900 text-white font-bold rounded-lg hover:bg-slate-800 transition-all disabled:opacity-50 h-[48px]"
            >
              {loading ? "Processando..." : "Comparar"}
            </button>
          </div>
          {erro && <p className="text-red-500 text-center mt-4 font-medium">{erro}</p>}
        </div>

        {/* ÁREA DE RESULTADOS */}
        {dadosA && dadosB && (
          <div className="space-y-8 animate-in fade-in duration-500">
            
            {/* CARDS DE IMPACTO GERAL */}
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
                      <Bar dataKey="valorA" name={univA} fill="#6366f1" radius={[4, 4, 0, 0]} />
                      <Bar dataKey="valorB" name={univB} fill="#10b981" radius={[4, 4, 0, 0]} />
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
                      <Bar dataKey="valorA" name={univA} fill="#6366f1" radius={[4, 4, 0, 0]} />
                      <Bar dataKey="valorB" name={univB} fill="#10b981" radius={[4, 4, 0, 0]} />
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