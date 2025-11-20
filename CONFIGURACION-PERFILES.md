# 🚀 Configuración de Perfiles - Sistema de Inventarios

Este proyecto utiliza diferentes perfiles de configuración para manejar distintos entornos de ejecución.

## 📁 Archivos de Configuración

### `application.properties` (Producción)
- Configuración principal y de producción
- Usa variables de entorno para mayor seguridad
- Logging mínimo para mejor rendimiento
- `ddl-auto=validate` para proteger la base de datos

### `application-dev.properties` (Desarrollo)
- Configuración específica para desarrollo
- Logging detallado para debugging
- `ddl-auto=update` para desarrollo ágil
- Configuraciones más permisivas

## 🔧 Cómo usar los perfiles

### Desarrollo (Recomendado)
```bash
# Opción 1: Variable de entorno
set SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run

# Opción 2: Parámetro JVM
mvn spring-boot:run -Dspring.profiles.active=dev

# Opción 3: En IDE (IntelliJ/Eclipse)
# Agregar en VM options: -Dspring.profiles.active=dev
```

### Producción
```bash
# Sin perfil específico (usa application.properties)
mvn spring-boot:run

# O explícitamente
mvn spring-boot:run -Dspring.profiles.active=prod
```

## 🔐 Variables de Entorno (Producción)

Para mayor seguridad en producción, configura estas variables de entorno:

```bash
# Configuración de Supabase
SUPABASE_URL=https://tu-proyecto.supabase.co
SUPABASE_API_KEY=tu_api_key_aqui

# Configuración de Base de Datos
DATABASE_URL=jdbc:postgresql://host:puerto/database
DATABASE_USERNAME=usuario
DATABASE_PASSWORD=contraseña

# Puerto del servidor
PORT=8080
```

## 🎯 Características por Perfil

| Característica | Desarrollo | Producción |
|---------------|------------|------------|
| Puerto | 8081 | 8081 (o PORT env) |
| SQL Logs | ✅ Habilitados | ❌ Deshabilitados |
| DDL Auto | `update` | `validate` |
| Log Level | `DEBUG` | `WARN` |
| Format SQL | ✅ Sí | ❌ No |
| DevTools | ✅ Habilitado | ❌ Deshabilitado |

## 📱 Endpoints Disponibles

### Desarrollo
- **API Base**: `http://localhost:8081/api/productos`
- **Health Check**: `http://localhost:8081/actuator/health`
- **Metrics**: `http://localhost:8081/actuator/metrics`

### Principales endpoints:
```http
GET    /api/productos              # Listar todos los productos
POST   /api/productos              # Crear nuevo producto
POST   /api/productos/datos-ejemplo # Insertar datos de prueba
```

## 🚨 Seguridad

### ⚠️ NUNCA hagas esto en producción:
- Hardcodear credenciales en el código
- Subir archivos con credenciales al repositorio
- Usar el mismo API key para desarrollo y producción

### ✅ Buenas prácticas:
- Usar variables de entorno en producción
- Tener credenciales diferentes por entorno
- Revisar el `.gitignore` regularmente

## 🔄 Ejemplos de Uso

### Insertar datos de ejemplo (solo desarrollo)
```bash
curl -X POST http://localhost:8081/api/productos/datos-ejemplo
```

### Crear un producto
```bash
curl -X POST http://localhost:8081/api/productos \
  -H "Content-Type: application/json" \
  -d '{"descripcion": "Nuevo Producto", "precio": 99.99, "cantidad": 10}'
```

### Listar productos
```bash
curl http://localhost:8081/api/productos
```

---

## 🛠️ Troubleshooting

### Error: "Unknown property 'supabase.url'"
- **Solución**: Es normal, son propiedades personalizadas. La aplicación funcionará correctamente.

### Error: "Failed to configure a DataSource"
- **Verificar**: Las credenciales de base de datos en el archivo de configuración activo
- **Solución**: Asegúrate de que las credenciales sean correctas

### La aplicación no encuentra el perfil
- **Verificar**: Que el archivo `application-dev.properties` esté en `src/main/resources/`
- **Solución**: Usar la sintaxis correcta: `-Dspring.profiles.active=dev`

---
*Creado para el proyecto Spring Boot + Angular - Sistema de Inventarios*
