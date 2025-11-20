# 🎬 El Almacén de Películas

Sistema de gestión de videoclub construido con arquitectura de microservicios, incluyendo autenticación OAuth2, mensajería asíncrona y gestión de ratings.

## 📋 Descripción

**El Almacén de Películas** es una aplicación distribuida que simula un sistema de videoclub moderno. El proyecto está dividido en varios microservicios que se comunican entre sí:

### Arquitectura

- **API Gateway** (Puerto 9500): Punto de entrada único que enruta las peticiones a los microservicios
- **Servicio de Catálogo** (Puerto 8081): Gestiona el catálogo de películas y categorías
- **Servicio de Rating** (Puerto 8082): Maneja las calificaciones y comentarios de películas
- **Keycloak SSO** (Puerto 9090): Servidor de autenticación OAuth2/OpenID Connect
- **RabbitMQ** (Puertos 5672/15672): Sistema de mensajería para comunicación asíncrona
- **MySQL**: Bases de datos independientes para cada servicio

### Tecnologías

- **Backend**: Spring Boot 3, Spring Cloud Gateway, Spring Security OAuth2
- **Persistencia**: JPA/Hibernate con MySQL
- **Mensajería**: RabbitMQ
- **Autenticación**: Keycloak (OAuth2 + JWT)
- **Contenedorización**: Docker & Docker Compose

## 🚀 Inicialización del Proyecto

### Prerrequisitos

- Docker Desktop instalado y en ejecución
- Docker Compose
- Puertos disponibles: 8081, 8082, 9090, 9500, 3307, 3308, 5672, 15672

### Levantar todos los servicios

```bash
# En la raíz del proyecto
docker-compose -f docker-compose-full.yml up -d
```

Este comando levantará:

- ✅ 2 bases de datos MySQL (catálogo y rating)
- ✅ RabbitMQ con interfaz de administración
- ✅ Keycloak con realm `videoclub` preconfigurado
- ✅ Servicio de Catálogo
- ✅ Servicio de Rating
- ✅ API Gateway

### Verificar el estado de los servicios

```bash
docker ps
```

### Detener los servicios

```bash
docker-compose -f docker-compose-full.yml down
```

## 🔐 Autenticación

El sistema utiliza Keycloak para autenticación OAuth2.

### Acceso a Keycloak

- URL: http://localhost:9090
- Usuario admin: `admin`
- Password: `admin`
- Realm: `videoclub`

### Obtener un Token de Acceso

```bash
curl -X POST 'http://localhost:9090/realms/videoclub/protocol/openid-connect/token' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'client_id=videoclub-app' \
  -d 'client_secret=<tu-secret>' \
  -d 'grant_type=password' \
  -d 'username=<usuario>' \
  -d 'password=<password>'
```

## 🎯 Endpoints Principales

Todos los endpoints están accesibles a través del **API Gateway** en `http://localhost:9500`

### 📽️ Catálogo de Películas

#### Listar todas las películas

```bash
GET http://localhost:9500/api/peliculas
```

#### Obtener una película específica

```bash
GET http://localhost:9500/api/peliculas/{id}
```

#### Listar categorías

```bash
GET http://localhost:9500/api/categorias
```

### 🔧 Administración de Películas (Admin)

Estos endpoints requieren permisos de administrador.

#### Crear una película

```bash
POST http://localhost:9500/api/admin/peliculas
Content-Type: application/json
Authorization: Bearer <tu-token-jwt>

{
  "titulo": "El Padrino",
  "descripcion": "La historia de la familia Corleone",
  "anio": 1972,
  "director": "Francis Ford Coppola",
  "duracion": 175,
  "categoriaId": 1
}
```

**Ejemplo con curl:**

```bash
curl -X POST 'http://localhost:9500/api/admin/peliculas' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer <tu-token-jwt>' \
  -d '{
    "titulo": "El Padrino",
    "descripcion": "La historia de la familia Corleone",
    "anio": 1972,
    "director": "Francis Ford Coppola",
    "duracion": 175,
    "categoriaId": 1
  }'
```

#### Actualizar una película

```bash
PUT http://localhost:9500/api/admin/peliculas/{id}
Content-Type: application/json
Authorization: Bearer <tu-token-jwt>

{
  "titulo": "El Padrino",
  "descripcion": "La historia de la familia Corleone (Edición actualizada)",
  "anio": 1972,
  "director": "Francis Ford Coppola",
  "duracion": 175,
  "categoriaId": 1
}
```

#### Eliminar una película

```bash
DELETE http://localhost:9500/api/admin/peliculas/{id}
Authorization: Bearer <tu-token-jwt>
```

**Ejemplo:**

```bash
curl -X DELETE 'http://localhost:9500/api/admin/peliculas/1' \
  -H 'Authorization: Bearer <tu-token-jwt>'
```

### ⭐ Servicio de Rating

#### Crear un rating (requiere autenticación)

```bash
POST http://localhost:9500/api/ratings
Content-Type: application/json
Authorization: Bearer <tu-token-jwt>

{
  "peliculaId": 1,
  "valor": 5,
  "comentario": "Excelente película!"
}
```

**Ejemplo con curl:**

```bash
curl -X POST 'http://localhost:9500/api/ratings' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer <tu-token-jwt>' \
  -d '{
    "peliculaId": 1,
    "valor": 5,
    "comentario": "Excelente película!"
  }'
```

#### Obtener ratings de una película

```bash
GET http://localhost:9500/api/ratings/pelicula/{peliculaId}
```

**Ejemplo:**

```bash
curl http://localhost:9500/api/ratings/pelicula/1
```

#### Obtener promedio de ratings de una película

```bash
GET http://localhost:9500/api/ratings/pelicula/{peliculaId}/promedio
```

**Ejemplo:**

```bash
curl http://localhost:9500/api/ratings/pelicula/1/promedio
```

#### Obtener ratings de un usuario (requiere autenticación)

```bash
GET http://localhost:9500/api/ratings/usuario/{usuarioId}
Authorization: Bearer <tu-token-jwt>
```

**Ejemplo:**

```bash
curl http://localhost:9500/api/ratings/usuario/test-user-123 \
  -H 'Authorization: Bearer <tu-token-jwt>'
```

#### Eliminar un rating

```bash
DELETE http://localhost:9500/api/ratings/{id}
Authorization: Bearer <tu-token-jwt>
```

### 🧪 Modo de Prueba (Sin Autenticación)

El servicio de rating permite crear ratings sin autenticación para testing. En este caso, se usará un `usuarioId` por defecto: `test-user-123`.

```bash
# Crear rating sin autenticación (modo testing)
curl -X POST 'http://localhost:8082/api/ratings' \
  -H 'Content-Type: application/json' \
  -d '{
    "peliculaId": 1,
    "valor": 4,
    "comentario": "Muy buena"
  }'
```

## 📊 Interfaces de Administración

### RabbitMQ Management

- URL: http://localhost:15672
- Usuario: `guest`
- Password: `guest`

### Keycloak Admin Console

- URL: http://localhost:9090/admin
- Usuario: `admin`
- Password: `admin`

## 🔧 Acceso Directo a los Servicios (Sin Gateway)

Si necesitas acceder directamente a los servicios:

- **Servicio de Catálogo**: http://localhost:8081
- **Servicio de Rating**: http://localhost:8082

## 🔄 Comunicación Asíncrona (RabbitMQ)

Los microservicios se comunican mediante eventos a través de RabbitMQ usando el patrón **Event-Driven Architecture**.

### Flujo de Eventos de Rating

Cuando se crea un nuevo rating:

1. **Rating Service** crea el rating en su base de datos
2. **Rating Service** publica un evento `RatingActualizadoEvent` en RabbitMQ
3. El evento contiene: `peliculaId`, `ratingPromedio`, `totalRatings`
4. **Catálogo Service** escucha el evento
5. **Catálogo Service** actualiza automáticamente el rating promedio de la película

**Exchange**: `exchange_videocloud00` (tipo: `topic`)  
**Routing Key**: `RatingActualizadoEvent.CREATE`  
**Queue**: `rating.catalogo.queue`

### Verificar Eventos en RabbitMQ

Accede a http://localhost:15672 para ver:

- **Exchanges**: Confirma que `exchange_videocloud00` existe
- **Queues**: Verifica `rating.catalogo.queue`
- **Messages**: Inspecciona los eventos publicados

## 📊 Ejemplos de Respuestas

### GET /api/peliculas/1

```json
{
  "id": 1,
  "titulo": "El Padrino",
  "descripcion": "La historia de la familia Corleone",
  "anio": 1972,
  "director": "Francis Ford Coppola",
  "duracion": 175,
  "categoriaId": 1,
  "ratingPromedio": 4.5,
  "totalRatings": 120
}
```

### GET /api/ratings/pelicula/1

```json
[
  {
    "id": 1,
    "peliculaId": 1,
    "usuarioId": "user-123",
    "valor": 5,
    "comentario": "Obra maestra del cine",
    "fechaCreacion": "2025-11-19T10:30:00"
  },
  {
    "id": 2,
    "peliculaId": 1,
    "usuarioId": "user-456",
    "valor": 4,
    "comentario": "Muy buena película",
    "fechaCreacion": "2025-11-19T11:15:00"
  }
]
```

### GET /api/ratings/pelicula/1/promedio

```json
4.5
```

## 🛠️ Troubleshooting

### Los servicios no inician correctamente

```bash
# Ver logs de un servicio específico
docker logs catalogo-backend
docker logs rating-service
docker logs keycloak-sso

# Verificar healthchecks
docker ps
```

### Error de conexión a la base de datos

Espera a que los healthchecks estén en estado `healthy`:

```bash
docker ps --filter "name=mysql"
```

### Keycloak marca "unhealthy" pero funciona

Es un problema conocido del healthcheck. Verifica que puedas acceder a http://localhost:9090. Si responde, está funcionando correctamente.

### Los eventos no se procesan

1. Verifica que RabbitMQ esté corriendo: http://localhost:15672
2. Confirma que el exchange `exchange_videocloud00` existe
3. Revisa los logs de ambos servicios:
   ```bash
   docker logs catalogo-backend | grep -i rating
   docker logs rating-service | grep -i event
   ```

### Puertos ya en uso

Si algún puerto está ocupado, modifica el `docker-compose-full.yml`:

```yaml
ports:
  - "NUEVO_PUERTO:PUERTO_INTERNO"
```

## 📝 Validaciones y Reglas de Negocio

### Ratings

- **valor**: Debe estar entre 1 y 5 (estrellas)
- **peliculaId**: Debe existir en el catálogo
- **usuarioId**: Se extrae del JWT (automático)
- **comentario**: Opcional

### Películas (Admin)

- **titulo**: Requerido, no vacío
- **anio**: Año válido
- **duracion**: En minutos, mayor a 0
- **categoriaId**: Debe existir

## 📝 Notas Técnicas

- El usuario para los ratings se extrae automáticamente del JWT (claim `sub`)
- Los servicios se comunican mediante eventos RabbitMQ en el exchange `exchange_videocloud00`
- Cada servicio tiene su propia base de datos MySQL (Database per Service pattern)
- El API Gateway maneja CORS para permitir peticiones desde `http://localhost:5173` (frontend)
- Los eventos RabbitMQ tienen reintentos automáticos (3 intentos con 5 segundos de espera)
- El rating promedio se actualiza en tiempo real mediante eventos asíncronos

## 🏗️ Estructura del Proyecto

```
demo-el-almacen-de-peliculas/
├── apigateway-main/              # API Gateway (Spring Cloud Gateway)
├── el-almacen-de-peliculas-online/ # Servicio de Catálogo
├── el-almacen-de-peliculas-online-rating/ # Servicio de Rating
├── springboot-sso/                # Configuración de Keycloak
└── docker-compose-full.yml        # Orquestación completa
```

## 👥 Proyecto Académico

Proyecto desarrollado por Rodrigo Damian Battillier y Agustín Fernández Gómez para el **Taller de Tecnologías y Producción de Software** - UNRN

##
