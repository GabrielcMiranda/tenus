# Tenus - Sistema de Controle de Atividades

Aplicativo de controle de atividades que gerencia e metrifica o comportamento do usuário no que diz respeito a elas.

## 📋 Estrutura do Projeto

O projeto foi reorganizado seguindo as melhores práticas de separação de responsabilidades:

```
tenus/
├── backend/                # Código fonte da aplicação Spring Boot
│   ├── src/
│   │   ├── main/java/miranda/gabriel/tenus/
│   │   │   ├── adapters/   # Camada de adaptadores (inbound/outbound)
│   │   │   ├── application/ # Casos de uso e serviços
│   │   │   ├── core/       # Domínio e regras de negócio
│   │   │   └── infrastructure/ # Configurações
│   │   └── resources/      # Arquivos de propriedades
│   ├── pom.xml            # Dependências Maven
│   └── Dockerfile         # Container do backend
├── docker/                # Configurações Docker
│   ├── docker-compose.yml # Orquestração de containers
│   └── .env              # Variáveis de ambiente
└── scripts/              # Scripts de automação
    └── start_back.ps1    # Script para iniciar a aplicação
```

## 🏗️ Arquitetura

O projeto implementa **Arquitetura Hexagonal (Ports and Adapters)** com:

- **Core/Domain**: Lógica de negócio pura, sem dependências externas
- **Application**: Casos de uso e interfaces de serviços
- **Adapters**: Implementações dos adaptadores para entrada (controllers) e saída (repositories)
- **Infrastructure**: Configurações do Spring e outras tecnologias

## 🚀 Como Executar

### Pré-requisitos
- Java 21
- Maven 3.8+
- Docker e Docker Compose
- PowerShell (Windows)

### Modo de Desenvolvimento (com recarga automática)
```powershell
.\scripts\start_back.ps1 -Action dev
```

### Modo de Produção (com Docker)
```powershell
.\scripts\start_back.ps1 -Action prod
```

### Parar Serviços
```powershell
.\scripts\start_back.ps1 -Action stop
```

### Limpeza Completa
```powershell
.\scripts\start_back.ps1 -Action clean
```

## 🔧 Tecnologias

- **Spring Boot 3.5.5** - Framework principal
- **Java 21** - Linguagem de programação
- **PostgreSQL 15** - Banco de dados
- **MapStruct 1.5.5** - Mapeamento de objetos
- **JWT** - Autenticação e autorização
- **Docker** - Containerização
- **Maven** - Gerenciamento de dependências

## 🔐 Configuração

Configure as variáveis de ambiente no arquivo `docker/.env`:

```env
PG_DATABASE=tenus_db
PG_USER=tenus_user
PG_PASS=tenus_password
```



## 🧪 Testes

```bash
cd backend
mvn test
```

## 📦 Build

```bash
cd backend
mvn clean package
```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.