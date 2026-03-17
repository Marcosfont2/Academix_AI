# Academix AI
O Academix AI é uma plataforma de analytics de alto nível voltada para o ecossistema acadêmico. Utilizando Inteligência Artificial Generativa e Preditiva, o sistema processa currículos e dados institucionais para oferecer insights estratégicos. Ele permite que nichos específicos apresentem suas trajetórias com base em dados reais de currículos acadêmicos e trajetórias acadêmicas.

Ele analisa padrões em milhares de trajetórias acadêmicas para responder perguntas complexas:

"Qual universidade possui o maior índice de publicação em Visão Computacional nos últimos 2 anos?"

"Com base no meu perfil atual, qual a probabilidade de aprovação em um mestrado na instituição X?" (To conjecturando...)

## Funcionalidades (Casos de Uso)

UC01 – *Dashboard de Métricas Visuais*: Conversão de currículos textuais em gráficos de radar de competências e mapas de calor de produtividade.

UC02 – *Motor de Busca por "Polos de Força"*: Busca inteligente que identifica quais faculdades ou linhas de pesquisa são líderes reais em temas específicos (ex: Visão Computacional, Segurança da Informação).

UC03 – *Preditor de Trajetória (IA)*: A IA analisa o currículo do aluno e sugere o "próximo melhor passo" com base em perfis de sucesso similares. (Aqui achei uma boa ideia, mas tem que ver...)

UC04 – *Comparador de Ecossistemas*: Funcionalidade que coloca duas instituições lado a lado, comparando a produção científica real (por exemplo).

UC05 – *Análise de Gap de Competência*: Identificação automática do que falta no currículo do aluno para atingir um objetivo específico (ex: uma vaga de intercâmbio). (Aqui também só coloquei o que pensei, temos que discutir...)

UC06 - *Roadmap Dinâmico*: Uma linha do tempo gerada automaticamente que se ajusta conforme adicionada a conclusão de novas etapas acadêmicas.


## Tecnologias Utilizadas

*Front-end*:

O projeto utiliza React.js e Tailwind CSS para uma interface moderna. A visualização de dados complexos, como os mapas de calor, é feita através da biblioteca D3.js.

*Back-end*:

A estrutura de serviços pode ser montada em Python (FastAPI) (Aqui preferencialmente, melhor para lidar com dados), ou C/C++ (que a gente domina mais). O processamento de inteligência artificial é realizado via LangChain com modelos da OpenAI (Aqui acho que ficaria muito trabalhoso treinar um modelo do zero, mas é possível).

*Dados*:

O armazenamento utiliza PostgreSQL (recomendo) com a extensão pgvector, permitindo que a IA realize buscas semânticas rápidas para encontrar perfis e instituições similares.

## Informações adicionais 

Bancos de Dados a serem utilizados:

<http://bi.cnpq.br/painel/formacao-atuacao-lattes/>

## Autores/Disciplina

- Marcos Fontes
- Thiago Raquel 
- Yuri Maximiliano

*Disciplina*: Engenharia de Software
