import pandas as pd
from sqlalchemy import create_engine
import json

# 1. Configurações
ARQUIVO_OPENALEX = 'openalex_br_2024_full.csv'
DB_URL = 'postgresql://postgres:postgre@localhost:5432/ciencia_br'
engine = create_engine(DB_URL)

print("Iniciando o processamento do OpenAlex (200k registros)...")

# 2. Leitura em pedaços (Chunks) para não estourar a RAM
# O chunksize de 5000 é um bom equilíbrio entre velocidade e memória
chunks = pd.read_csv(ARQUIVO_OPENALEX, sep=',', encoding='utf-8', chunksize=5000)

primeiro_chunk = True

for i, df_chunk in enumerate(chunks):
    # Opcional: Se você quiser transformar o abstract_raw em texto legível depois, 
    # podemos fazer isso via SQL. Por enquanto, vamos subir como está.
    
    # 3. Importação progressiva
    # Na primeira vez, usamos 'replace' para criar a tabela. 
    # Nas próximas, usamos 'append' para adicionar os dados.
    modo = 'replace' if primeiro_chunk else 'append'
    
    df_chunk.to_sql('openalex_artigos', engine, if_exists=modo, index=False)
    
    primeiro_chunk = False
    if i % 5 == 0:
        print(f"Progresso: {i * 5000} linhas importadas...")

print("\nSucesso! O monstro de 200k foi domado.")