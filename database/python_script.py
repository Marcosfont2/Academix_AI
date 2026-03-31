import requests
import pandas as pd
import time

# Configurações
email = "thiaguinho2005@yahoo.com.br" # Use seu e-mail real para a Polite Pool
url_base = "https://api.openalex.org/works"
params = {
    'filter': 'institutions.country_code:br,publication_year:2024',
    'per_page': 200,
    'cursor': '*', 
    'mailto': email
}

todos_trabalhos = []
contagem = 0

print("Iniciando download otimizado para 2024...")

while params['cursor'] is not None:
    try:
        response = requests.get(url_base, params=params).json()
        results = response.get('results', [])
        
        if not results:
            break
            
        for work in results:
            # Extração segura de dados aninhados
            primary_loc = work.get('primary_location') or {}
            source = primary_loc.get('source') or {}
            
            todos_trabalhos.append({
                'id_openalex': work.get('id'),
                'doi': work.get('doi'),
                'titulo': work.get('title'),
                'ano': work.get('publication_year'),
                'data_pub': work.get('publication_date'),
                'tipo': work.get('type'),
                'citacoes': work.get('cited_by_count'),
                'is_oa': work.get('open_access', {}).get('is_oa'),
                'revista': source.get('display_name'),
                # Pega o primeiro conceito (tópico principal)
                'topico': work.get('concepts', [{}])[0].get('display_name') if work.get('concepts') else None,
                # Transforma a lista de instituições em uma string separada por ';' para o CSV
                'instituicoes': "; ".join([inst.get('display_name') for auth in work.get('authorships', []) 
                                           for inst in auth.get('institutions', []) if inst.get('display_name')]),
                # Se quiser o abstract (inverted index), descomente a linha abaixo:
                'abstract_raw': work.get('abstract_inverted_index') 
            })
        
        params['cursor'] = response.get('meta', {}).get('next_cursor')
        contagem += len(results)
        print(f"Registros processados: {contagem}...", end='\r')

    except Exception as e:
        print(f"\nErro: {e}. Retentando...")
        time.sleep(5)

# Salva o dataset completo
df = pd.DataFrame(todos_trabalhos)
df.to_csv("openalex_br_2024_full.csv", index=False, encoding='utf-8')
print(f"\nConcluído! {contagem} linhas salvas em 'openalex_br_2024_full.csv'")