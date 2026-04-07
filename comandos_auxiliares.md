-- 1. Mostra o nome de todas as colunas e o tipo de dado de cada uma
SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name = 'tabela1';

-- 2. Traz 10 exemplos de entradas com todas as colunas
SELECT * FROM tabela1 
LIMIT 10;

-- 3. Código para pegar as colunas
SELECT '- **' || column_name || '**: `' || data_type || '`' AS documentacao_markdown
FROM information_schema.columns 
WHERE table_name = 'tabela1';


capes_docentes

"- **an_base**: `bigint`" 2024
"- **cd_area_avaliacao**: `bigint`" 19
"- **nm_area_avaliacao**: `text`" "FARMÁCIA"
"- **nm_grande_area_conhecimento**: `text`" "CIÊNCIAS DA SAÚDE"
"- **nm_area_conhecimento**: `character varying`" "FARMÁCIA"
"- **cd_programa_ies**: `text`" "12001015033P0"
"- **nm_programa_ies**: `text`" "CIÊNCIAS FARMACÊUTICAS"
"- **nm_grau_programa**: `text`" "MESTRADO"
"- **nm_modalidade_programa**: `text`" "ACADÊMICO"
"- **cd_conceito_programa**: `text`" "3"
"- **cd_entidade_capes**: `bigint`" 12001015
"- **cd_entidade_emec**: `text`" "4"
"- **sg_entidade_ensino**: `text`" "UFAM"
"- **nm_entidade_ensino**: `text`" "UNIVERSIDADE FEDERAL DO AMAZONAS"
"- **ds_dependencia_administrativa**: `text`" "PÚBLICA"
"- **cs_status_juridico**: `text`" "FEDERAL"
"- **nm_municipio_programa_ies**: `text`" "MANAUS"
"- **sg_uf_programa**: `text`" "AM"
"- **nm_regiao**: `text`" "NORTE"
"- **id_pessoa**: `bigint`" 327009
"- **tp_documento_docente**: `text`" "CPF"
"- **nr_documento_docente**: `text`" "***.747.968-**"
"- **nm_docente**: `text`" "ANA LIGIA LEANDRINI DE OLIVEIRA"
"- **an_nascimento_docente**: `bigint`" 1984
"- **ds_faixa_etaria**: `text`" "40 A 44 ANOS"
"- **ds_tipo_nacionalidade_docente**: `text`" "BRASILEIRO"
"- **nm_pais_nacionalidade_docente**: `text`" "BRASIL"
"- **ds_categoria_docente**: `text`" "PERMANENTE"
"- **ds_tipo_vinculo_docente_ies**: `text`" "SERVIDOR PÚBLICO"
"- **ds_regime_trabalho**: `text`" "DEDICAÇÃO EXCLUSIVA"
"- **cd_cat_bolsa_produtividade**: `text`" 2 ou [null]
"- **in_doutor**: `text`" "S"
"- **an_titulacao**: `bigint`" 2013
"- **nm_grau_titulacao**: `text`" "DOUTORADO"
"- **cd_area_basica_titulacao**: `bigint`" 40300005
"- **nm_area_basica_titulacao**: `text`" "FARMÁCIA"
"- **sg_ies_titulacao**: `text`" "USP-RIBEIRÃO PRETO"
"- **nm_ies_titulacao**: `text`" "UNIVERSIDADE DE SÃO PAULO - CAMPUS RIBEIRÃO PRETO"
"- **nm_pais_ies_titulacao**: `text`" "BRASIL"
"- **id_add_foto_programa**: `bigint`" 198816
"- **id_add_foto_programa_ies**: `bigint`" 265196


instituicoes_openalex

"- **id**: `character varying`" "https://openalex.org/I17974374"
"- **cited_by_count**: `integer`" 21790173
"- **country_code**: `character varying`" "BR"
"- **display_name**: `character varying`" "Universidade de São Paulo"
"- **geo_city**: `text`" "São Paulo"
"- **geo_region**: `text`" "São Paulo"
"- **homepage_url**: `text`" "https://www5.usp.br"
"- **ror**: `text`" "https://ror.org/036rp1748"
"- **h_index**: `integer`" 844
"- **tipo**: `character varying`" "education"
"- **works_count**: `integer`" 456831


lattes_painel

"- **ano**: `bigint`" 2024
"- **pais_nascimento**: `text`" "África do Sul"
"- **pais_formacao**: `text`" "Brasil"
"- **regiao_formacao**: `text`" "Sudeste"
"- **uf_formacao**: `text`" "Rio de Janeiro"
"- **cidade_formacao**: `text`" "Rio de Janeiro"
"- **grande_area_formacao**: `text`" "Ciências Biológicas"
"- **area_formacao**: `text`" "Imunologia"
"- **instituicao_formacao**: `text`" "Universidade Federal do Rio de Janeiro UFRJ"
"- **nivel_formacao**: `text`" "Doutorado"
"- **pais_atuacao**: `text`" "Brasil"
"- **regiao_atuacao**: `text`" "Sudeste"
"- **uf_atuacao**: `text`" "Rio de Janeiro"
"- **cidade_atuacao**: `text`" "Duque de Caxias"
"- **grande_area_atuacao**: `text`" "Ciências Biológicas"
"- **area_atuacao**: `text`" "Biotecnologia"
"- **instituicao_atuacao**: `text`" "Universidade Federal do Rio de Janeiro UFRJ"
"- **setor_atividade_atuacao**: `text`" "Setor Exterior"
"- **enquadramento_atuacao**: `text`" "Pós-doutorando"
"- **bolsa_ic**: `text`" "Não"
"- **bolsa_grad_sanduiche**: `text`" "Não"
"- **bolsa_mestrado**: `text`" "Sim"
"- **bolsa_doutorado**: `text`" "Não"
"- **bolsa_pos_doc**: `text`" "Não"
"- **bolsa_pq**: `text`" "Não"
"- **bolsa_pq_1a**: `text`" "Não"
"- **bolsa_pq_1b**: `text`" "Não"
"- **bolsa_pq_1c**: `text`" "Não"
"- **bolsa_pq_1d**: `text`" "Não"
"- **bolsa_pq_2_1e**: `text`" "Não"
"- **bolsa_dt**: `text`" "Não"
"- **bolsa_dt_1a**: `text`" "Não"
"- **bolsa_dt_1b**: `text`" "Não"
"- **bolsa_dt_1c**: `text`" "Não"
"- **bolsa_dt_1d**: `text`" "Não"
"- **bolsa_dt_2_1e**: `text`" "Não"
"- **sexo**: `character varying`" "Feminino"
"- **cor_raca**: `text`" "Branca"
"- **data_extracao**: `text`" "04/03/2026"
"- **contagem_registro**: `bigint`" 1
"- **id**: `bigint`" 5


openalex_artigos

"- **id_openalex**: `character varying`" "https://openalex.org/W4395447529"
"- **doi**: `text`" "https://doi.org/10.1126/science.adj4503"
"- **titulo**: `text`" "Genomic factors shape carbon and nitrogen metabolic niche breadth across Saccharomycotina yeasts"
"- **ano**: `bigint`" 2024
"- **data_pub**: `text`" "2024-04-25"
"- **tipo**: `character varying`" "article"
"- **citacoes**: `bigint`" 115
"- **is_oa**: `boolean`" true
"- **revista**: `text`" "Science"
"- **topico**: `text`" "Biology"
"- **instituicoes**: `text`" "Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; Villanova University; University of North Carolina at Charlotte; Vanderbilt University; Vanderbilt University; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; Zhejiang University; South China Agricultural University; Vanderbilt University; Qingdao National Laboratory for Marine Science and Technology; Shandong University of Science and Technology; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; Howard Hughes Medical Institute; Vanderbilt University; University of California, Berkeley; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; University of Colorado Anschutz Medical Campus; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; Energy Center of Wisconsin; Universidade Nova de Lisboa; Vanderbilt University; Energy Center of Wisconsin; Universidade Nova de Lisboa; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; South China Agricultural University; Vanderbilt University; Qingdao National Laboratory for Marine Science and Technology; Shandong University of Science and Technology; Energy Center of Wisconsin; Washington University in St. Louis; Energy Center of Wisconsin; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; Max Planck Institute of Molecular Physiology; Universidade Federal de Minas Gerais; University of Ljubljana; Consejo Nacional de Investigaciones Científicas y Técnicas; National University of Comahue; Agricultural Research Service; National Center for Agricultural Utilization Research; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin; Agricultural Research Service; National Center for Agricultural Utilization Research; Universidade Nova de Lisboa; Universidade Nova de Lisboa; South China Agricultural University; Vanderbilt University; Vanderbilt University; Zhejiang University; Westerdijk Fungal Biodiversity Institute; Vanderbilt University; Great Lakes Bioenergy Research Center; Energy Center of Wisconsin"
"- **abstract_raw**: `text`" "{'Organisms': [0], 'exhibit': [1], 'extensive': [2], 'variation': [3, 121], 'in': [4, 70, 85, 98, 122], 'ecological': [5, 50], 'niche': [6, 77, 119], 'breadth,': [7], 'from': [8, 52, 66, 95], 'very': [9, 13], 'narrow': [10], '(specialists)': [11], 'to': [12, 22, 75], 'broad': [14], '(generalists).': [15], 'Two': [16], 'general': [17], 'paradigms': [18], 'have': [19], 'been': [20], 'proposed': [21], 'explain': [23], 'this': [24], 'variation:': [25], '(i)': [26], 'trade-offs': [27], 'between': [28, 92], 'performance': [29], 'efficiency': [30], 'and': [31, 33, 41, 49], 'breadth': [32, 78, 87, 120], '(ii)': [34], 'the': [35, 58, 86], 'joint': [36], 'influence': [37], 'of': [38, 57, 88], 'extrinsic': [39], '(environmental)': [40], 'intrinsic': [42, 96, 116], '(genomic)': [43], 'factors.': [44], 'We': [45, 80], 'assembled': [46], 'genomic,': [47], 'metabolic,': [48], 'data': [51, 113], 'nearly': [53], 'all': [54], 'known': [55], 'species': [56], 'ancient': [59], 'fungal': [60], 'subphylum': [61], 'Saccharomycotina': [62], '(1154': [63], 'yeast': [64], 'strains': [65], '1051': [67], 'species),': [68], 'grown': [69], '24': [71], 'different': [72], 'environmental': [73], 'conditions,': [74], 'examine': [76], 'evolution.': [79], 'found': [81, 106], 'that': [82, 115], 'large': [83], 'differences': [84, 97], 'carbon': [89], 'utilization': [90], 'traits': [91], 'yeasts': [93], 'stem': [94], 'genes': [99], 'encoding': [100], 'specific': [101], 'metabolic': [102], 'pathways,': [103], 'but': [104], 'we': [105], 'limited': [107], 'evidence': [108], 'for': [109], 'trade-offs.': [110], 'These': [111], 'comprehensive': [112], 'argue': [114], 'factors': [117], 'shape': [118], 'microbes.': [123]}"

		