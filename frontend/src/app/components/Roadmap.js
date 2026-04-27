import React, { useState, useEffect } from 'react';

export default function Roadmap({ userId }) {
    const [eventos, setEventos] = useState([]);
    const [loading, setLoading] = useState(true);

    const [novaAtividade, setNovaAtividade] = useState({
        ano: new Date().getFullYear(),
        tipo: 'Curso',
        titulo: '',
        descricao: ''
    });

    const carregarRoadmap = async () => {
        setLoading(true);
        try {
            const response = await fetch(`http://localhost:8080/api/auth/${userId}/roadmap`);
            if (response.ok) {
                const data = await response.json();
                setEventos(data);
            }
        } catch (error) {
            console.error("Erro ao buscar roadmap:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (userId) carregarRoadmap();
    }, [userId]);

    const handleSalvarAtividade = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/auth/${userId}/atividades`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(novaAtividade)
            });

            if (response.ok) {
                setNovaAtividade({ ...novaAtividade, titulo: '', descricao: '' });
                carregarRoadmap();
            } else {
                alert("Erro ao salvar atividade.");
            }
        } catch (error) {
            console.error("Erro na requisição:", error);
        }
    };

    return (
        <div className="max-w-4xl mx-auto p-6 space-y-10">
            {/* --- SEÇÃO 1: FORMULÁRIO DE ADIÇÃO --- */}
            <div className="bg-white p-6 rounded-lg shadow-md border border-gray-300">
                <h2 className="text-xl font-extrabold text-black mb-4">Adicionar Nova Conquista</h2>
                <form onSubmit={handleSalvarAtividade} className="grid grid-cols-1 md:grid-cols-4 gap-4">
                    
                    <div className="col-span-1">
                        <label className="block text-sm font-bold text-black">Ano</label>
                        <input type="number" required
                            className="mt-1 w-full p-2 border border-gray-400 rounded-md text-black bg-white font-medium"
                            value={novaAtividade.ano}
                            onChange={(e) => setNovaAtividade({ ...novaAtividade, ano: Number(e.target.value) })}
                        />
                    </div>

                    <div className="col-span-1">
                        <label className="block text-sm font-bold text-black">Tipo</label>
                        <select 
                            className="mt-1 w-full p-2 border border-gray-400 rounded-md text-black bg-white font-medium"
                            value={novaAtividade.tipo}
                            onChange={(e) => setNovaAtividade({ ...novaAtividade, tipo: e.target.value })}
                        >
                            <option value="Curso">Curso</option>
                            <option value="Projeto de Pesquisa">Projeto de Pesquisa</option>
                            <option value="Congresso">Congresso</option>
                            <option value="Premiação">Premiação</option>
                            <option value="Projeto Acadêmico">Projeto Acadêmico</option>
                        </select>
                    </div>

                    <div className="col-span-2">
                        <label className="block text-sm font-bold text-black">Título</label>
                        <input type="text" required placeholder="Ex: Especialização em IA"
                            className="mt-1 w-full p-2 border border-gray-400 rounded-md text-black bg-white font-medium placeholder-gray-500"
                            value={novaAtividade.titulo}
                            onChange={(e) => setNovaAtividade({ ...novaAtividade, titulo: e.target.value })}
                        />
                    </div>

                    <div className="col-span-1 md:col-span-4">
                        <label className="block text-sm font-bold text-black">Descrição (Opcional)</label>
                        <textarea rows="2" placeholder="Detalhes sobre a conquista..."
                            className="mt-1 w-full p-2 border border-gray-400 rounded-md text-black bg-white font-medium placeholder-gray-500"
                            value={novaAtividade.descricao}
                            onChange={(e) => setNovaAtividade({ ...novaAtividade, descricao: e.target.value })}
                        />
                    </div>

                    <div className="col-span-1 md:col-span-4 flex justify-end mt-2">
                        <button type="submit" className="bg-blue-600 text-white font-bold px-6 py-2 rounded shadow hover:bg-blue-700 transition">
                            Adicionar ao Roadmap
                        </button>
                    </div>
                </form>
            </div>

            {/* --- SEÇÃO 2: A LINHA DO TEMPO VISUAL --- */}
            <div>
                <h2 className="text-2xl font-extrabold text-black mb-6">Meu Roadmap Acadêmico</h2>
                
                {loading ? (
                    <p className="text-black font-semibold text-lg">Carregando sua trajetória...</p>
                ) : eventos.length === 0 ? (
                    <p className="text-black font-semibold text-lg">Nenhum evento encontrado. Importe seu Lattes ou adicione manualmente.</p>
                ) : (
                    <div className="relative border-l-4 border-blue-500 ml-3 md:ml-6 pl-6 space-y-8">
                        {eventos.map((evento, index) => (
                            <div key={index} className="relative">
                                {/* O "pontinho" da linha do tempo */}
                                <span className="absolute -left-[37px] bg-blue-600 text-white w-6 h-6 rounded-full flex items-center justify-center border-4 border-white shadow-md">
                                </span>
                                
                                {/* O Cartão de Conteúdo */}
                                <div className="bg-white p-5 rounded-lg shadow-md border border-gray-300">
                                    <div className="flex items-center justify-between mb-2">
                                        <span className="text-sm font-extrabold text-white bg-blue-600 px-3 py-1 rounded">
                                            {evento.ano}
                                        </span>
                                        <span className="text-xs font-bold text-gray-800 uppercase tracking-wider bg-gray-100 px-2 py-1 rounded">
                                            {evento.tipo}
                                        </span>
                                    </div>
                                    <h3 className="text-xl font-extrabold text-black">{evento.titulo}</h3>
                                    {evento.descricao && (
                                        <p className="text-black mt-3 text-base leading-relaxed">{evento.descricao}</p>
                                    )}
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}