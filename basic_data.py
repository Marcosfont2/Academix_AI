import requests
import pandas as pd
import plotly.express as px

def carregar_amostra_openalex(qtd_paginas=10):
    all_works = []
    url = "https://api.openalex.org/works"
    
    # Filtros que você aplicou
    params = {
        "filter": "from_publication_date:2025-01-01,institutions.country_code:br,concepts.id:C41008148",
        "per_page": 100,
        "mailto": "seu-email@ufrn.edu.br" # Coloque seu e-mail aqui
    }

    print(f"Buscando {qtd_paginas * 100} registros...")
    
    for page in range(1, qtd_paginas + 1):
        params['page'] = page
        response = requests.get(url, params=params)
        if response.status_code == 200:
            data = response.json()
            all_works.extend(data['results'])
        else:
            print(f"Erro na página {page}")
            break

    # Tratamento básico para visualização
    rows = []
    for w in all_works:
        rows.append({
            'titulo': w.get('display_name'),
            'ano': w.get('publication_year'),
            'data': w.get('publication_date'),
            'instituicao_principal': w['authorships'][0]['institutions'][0]['display_name'] if w['authorships'] and w['authorships'][0]['institutions'] else "N/A",
            'tipo': w.get('type'),
            'citacoes': w.get('cited_by_count', 0),
            'is_oa': w.get('open_access', {}).get('is_oa', False)
        })
    
    return pd.DataFrame(rows)

# Executar a busca
df = carregar_amostra_openalex(10) # Baixa 1000 registros para o teste rápido