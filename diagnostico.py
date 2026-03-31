import pandas as pd

try:
    # Tentando UTF-16 que é o culpado mais comum do erro NUL
    df = pd.read_csv('PAINEL_LATTES.csv', sep='\t', encoding='utf-16')
    print("Sucesso com UTF-16!")
    print(df.head())
except Exception as e:
    print(f"UTF-16 falhou: {e}")