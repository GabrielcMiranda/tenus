
param(
    [string]$Action = "dev",  # dev, prod, stop, clean
    [switch]$SkipBuild = $false
)

# Cores para output
function Write-ColorOutput($ForegroundColor) {
    $fc = $host.UI.RawUI.ForegroundColor
    $host.UI.RawUI.ForegroundColor = $ForegroundColor
    if ($args) {
        Write-Output $args
    } else {
        $input | Write-Output
    }
    $host.UI.RawUI.ForegroundColor = $fc
}

function Write-Success { Write-ColorOutput Green $args }
function Write-Info { Write-ColorOutput Cyan $args }
function Write-Warning { Write-ColorOutput Yellow $args }
function Write-Error { Write-ColorOutput Red $args }

# Função para verificar se Docker está rodando
function Test-DockerRunning {
    try {
        docker info > $null 2>&1
        return $true
    } catch {
        return $false
    }
}

# Função para aguardar serviço estar saudável
function Wait-ForHealthyService {
    param([string]$ServiceName, [int]$TimeoutSeconds = 60)
    
    Write-Info "⏳ Aguardando $ServiceName ficar saudável..."
    $elapsed = 0
    
    while ($elapsed -lt $TimeoutSeconds) {
        $health = docker compose -f ".\tenus\docker\docker-compose.yml" --env-file ".\tenus\docker\.env" ps --format json | ConvertFrom-Json | Where-Object { $_.Service -eq $ServiceName } | Select-Object -ExpandProperty Health
        
        if ($health -eq "healthy") {
            Write-Success "✅ $ServiceName está saudável!"
            return $true
        }
        
        Start-Sleep -Seconds 5
        $elapsed += 5
        Write-Info "   Tentativa $($elapsed/5)... Status: $health"
    }
    
    Write-Error "❌ Timeout aguardando $ServiceName ficar saudável"
    return $false
}

# Função de limpeza
function Invoke-Cleanup {
    Write-Info "🧹 Limpando recursos..."
    docker compose -f ".\tenus\docker\docker-compose.yml" --env-file ".\tenus\docker\.env" down -v
    Write-Success "✅ Limpeza concluída"
}

# Handler para Ctrl+C
Register-EngineEvent PowerShell.Exiting -Action {
    Write-Warning "🛑 Interrompido pelo usuário"
    Invoke-Cleanup
}

# Função principal
function Start-Development {
    Write-Info "🚀 Iniciando Task Planner - Modo Desenvolvimento"
    
    # Verificar se Docker está rodando
    if (-not (Test-DockerRunning)) {
        Write-Error "❌ Docker não está rodando. Inicie o Docker Desktop primeiro."
        exit 1
    }
    
    # Verificar se arquivo .env existe na pasta docker
    if (-not (Test-Path ".\tenus\docker\.env")) {
        Write-Error "❌ Arquivo .env não encontrado em .\tenus\docker\.env"
        Write-Info "💡 Crie o arquivo com as seguintes variáveis:"
        Write-Info "   PG_USER=postgres"
        Write-Info "   PG_PASS=sua_senha"
        Write-Info "   PG_DATABASE=sua_base"
        Write-Info "   SPRING_PROFILES_ACTIVE=docker"
        exit 1
    } else {
        Write-Success "✅ Arquivo .env encontrado em .\tenus\docker\.env"
    }
    
    try {
        # Subir PostgreSQL e Redis
        Write-Info "🐘 Subindo PostgreSQL e Redis..."
        docker compose -f ".\tenus\docker\docker-compose.yml" --env-file ".\tenus\docker\.env" up postgres redis -d
        
        # Aguardar PostgreSQL ficar saudável
        if (-not (Wait-ForHealthyService "postgres" 60)) {
            throw "PostgreSQL não ficou saudável"
        }
        
        # Aguardar Redis ficar saudável
        if (-not (Wait-ForHealthyService "redis" 60)) {
            throw "Redis não ficou saudável"
        }
        
        # Compilar projeto (se não especificado para pular)
        if (-not $SkipBuild) {
            Write-Info "🔨 Compilando projeto..."
            Set-Location ".\tenus\backend"
            mvn clean compile
            if ($LASTEXITCODE -ne 0) {
                Set-Location ".."
                Set-Location ".."
                throw "Falha na compilação"
            }
            Set-Location ".."
            Set-Location ".."
            Write-Success "✅ Compilação concluída"
        }
        
        # Executar aplicação Spring Boot
        Write-Info "🌱 Iniciando aplicação Spring Boot..."
        Write-Info "📍 Aplicação estará disponível em: http://localhost:8080"
        Write-Info "📍 Para parar, pressione Ctrl+C"
        Write-Info ""
        
        Set-Location ".\tenus\backend"
        mvn spring-boot:run
        Set-Location ".."
        Set-Location ".."
        
    } catch {
        Write-Error "❌ Erro: $_"
        Write-Info "🧹 Executando limpeza..."
        Invoke-Cleanup
        exit 1
    } finally {
        Write-Info "🛑 Parando serviços..."
        Invoke-Cleanup
    }
}

function Start-Production {
    Write-Info "🚀 Iniciando Task Planner - Modo Produção"
    
    if (-not (Test-DockerRunning)) {
        Write-Error "❌ Docker não está rodando"
        exit 1
    }
    
    try {
        Write-Info "🔨 Construindo aplicação..."
        Set-Location ".\tenus\backend"
        mvn clean package -DskipTests
        Set-Location ".."
        Set-Location ".."
        
        Write-Info "🐳 Subindo todos os serviços..."
        docker compose -f ".\tenus\docker\docker-compose.yml" --env-file ".\tenus\docker\.env" up --build -d
        
        Write-Success "✅ Aplicação rodando em modo produção"
        Write-Info "📍 Aplicação: http://localhost:8080"
        Write-Info "📍 Para ver logs: docker compose -f .\tenus\docker\docker-compose.yml --env-file .\tenus\docker\.env logs -f"
        Write-Info "📍 Para parar: .\tenus\scripts\start_back.ps1 -Action stop"
        
    } catch {
        Write-Error "❌ Erro: $_"
        Invoke-Cleanup
        exit 1
    }
}

function Stop-Services {
    Write-Info "🛑 Parando todos os serviços..."
    docker compose -f ".\tenus\docker\docker-compose.yml" --env-file ".\tenus\docker\.env" down
    Write-Success "✅ Serviços parados"
}

function Clean-All {
    Write-Info "🧹 Limpeza completa (volumes, images, etc.)..."
    docker compose -f ".\tenus\docker\docker-compose.yml" --env-file ".\tenus\docker\.env" down -v --rmi all
    docker system prune -f
    Write-Success "✅ Limpeza completa concluída"
}

# Menu principal
switch ($Action.ToLower()) {
    "dev" { Start-Development }
    "prod" { Start-Production }
    "stop" { Stop-Services }
    "clean" { Clean-All }
    default {
        Write-Info "📋 Uso: .\start_back.ps1 -Action [dev|prod|stop|clean]"
        Write-Info ""
        Write-Info "🎯 Ações disponíveis:"
        Write-Info "   dev   - Modo desenvolvimento (PostgreSQL no Docker, app local)"
        Write-Info "   prod  - Modo produção (tudo no Docker)"
        Write-Info "   stop  - Para todos os serviços"
        Write-Info "   clean - Remove tudo (volumes, images, etc.)"
        Write-Info ""
        Write-Info "🎛️  Opções:"
        Write-Info "   -SkipBuild - Pula compilação no modo dev"
        Write-Info ""
        Write-Info "📝 Exemplos:"
        Write-Info "   .\start_back.ps1                    # Modo desenvolvimento"
        Write-Info "   .\start_back.ps1 -Action prod       # Modo produção"
        Write-Info "   .\start_back.ps1 -Action dev -SkipBuild  # Dev sem compilar"
        Write-Info "   .\start_back.ps1 -Action stop       # Para serviços"
    }
}