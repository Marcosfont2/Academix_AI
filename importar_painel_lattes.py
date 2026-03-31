import pandas as pd
from sqlalchemy import create_engine
import io

# 1. Configurações
ARQUIVO = 'PAINEL_LATTES.csv'
DB_URL = 'postgresql://postgres:postgre@localhost:5432/ciencia_br'
engine = create_engine(DB_URL)

print("Limpando bytes nulos e carregando dados...")

# 2. Técnica da "Bala de Prata": Limpeza Binária
with open(ARQUIVO, 'rb') as f:
    # Remove bytes nulos (\x00) que causam o erro 'line contains NUL'
    content = f.read().replace(b'\x00', b'') 

# 3. Carregar no Pandas
# Usamos sep='\t' (Tab) e encoding 'latin1' ou 'utf-16' (teste o que der melhor head)
# Se o byte cleaning funcionou, latin1 costuma ler bem o resto
df = pd.read_csv(io.BytesIO(content), sep='\t', encoding='latin1')

# 4. Renomear as colunas para o padrão SQL (sem pontos ou espaços)
df.columns = [
    'ano', 'pais_nascimento', 'pais_formacao', 'regiao_formacao', 'uf_formacao', 
    'cidade_formacao', 'grande_area_formacao', 'area_formacao', 'instituicao_formacao', 
    'nivel_formacao', 'pais_atuacao', 'regiao_atuacao', 'uf_atuacao', 'cidade_atuacao', 
    'grande_area_atuacao', 'area_atuacao', 'instituicao_atuacao', 'setor_atividade_atuacao', 
    'enquadramento_atuacao', 'bolsa_ic', 'bolsa_grad_sanduiche', 'bolsa_mestrado', 
    'bolsa_doutorado', 'bolsa_pos_doc', 'bolsa_pq', 'bolsa_pq_1a', 'bolsa_pq_1b', 
    'bolsa_pq_1c', 'bolsa_pq_1d', 'bolsa_pq_2_1e', 'bolsa_dt', 'bolsa_dt_1a', 
    'bolsa_dt_1b', 'bolsa_dt_1c', 'bolsa_dt_1d', 'bolsa_dt_2_1e', 'sexo', 'cor_raca', 
    'data_extracao', 'contagem_registro'
]

# 5. Limpeza de espaços em branco nas células (importante para as buscas!)
df = df.apply(lambda x: x.str.strip() if x.dtype == "object" else x)

# 6. Enviar para o Postgres (Replace para resetar a tabela)
print(f"Importando {len(df)} linhas para o PostgreSQL...")
df.to_sql('lattes_painel', engine, if_exists='replace', index=False, chunksize=10000)

print("Sucesso! Tabela 'lattes_painel' pronta para uso.")