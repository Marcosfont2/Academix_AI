import pandas as pd
from sqlalchemy import create_engine
import io

# 1. Configurações
ARQUIVO_CAPES = 'CAPES.csv' # Ajuste o nome do arquivo
DB_URL = 'postgresql://postgres:postgre@localhost:5432/ciencia_br'
engine = create_engine(DB_URL)

print("Processando arquivo CAPES...")

# 2. Limpeza Binária (Removendo bytes nulos que travam o Python)
with open(ARQUIVO_CAPES, 'rb') as f:
    content = f.read().replace(b'\x00', b'')

# 3. Carregar no Pandas
# Note: sep=';' e encoding='latin1' para tratar os acentos do seu sample
df = pd.read_csv(io.BytesIO(content), sep=';', encoding='latin1')

# 4. Normalizar nomes das colunas (Minúsculas e sem caracteres especiais)
# Isso facilita muito as queries SQL depois
df.columns = [c.lower().replace(' ', '_').replace('-', '_') for c in df.columns]

# 5. Limpeza de espaços em branco (Strip)
df = df.apply(lambda x: x.str.strip() if x.dtype == "object" else x)

# 6. Criar Tabela e Importar
print(f"Importando {len(df)} registros para a tabela 'capes_docentes'...")
df.to_sql('capes_docentes', engine, if_exists='replace', index=False, chunksize=10000)

print("Sucesso! A tabela 'capes_docentes' foi criada e populada.")