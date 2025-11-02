#!/bin/bash
set -e

# Script de inicialização do PostgreSQL com PostGIS
# Este script roda automaticamente quando o container é criado pela primeira vez

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- Habilitar extensão PostGIS
    CREATE EXTENSION IF NOT EXISTS postgis;
    
    -- Verificar versão instalada
    SELECT 'PostGIS ' || PostGIS_Version() || ' instalado com sucesso!' AS status;
EOSQL
