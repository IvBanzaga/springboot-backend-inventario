# Proyecto Inventario - SpringBoot / Angular

## 📋 Información General

**Nombre**: Sistema de Inventarios  
**Tecnologías**: Spring Boot 3.5.5 + Angular + Supabase  
**Java Version**: 21  
**Puerto**: 8080  
**Base de Datos**: Supabase (PostgreSQL + REST API)  

---

## 🗂️ Estructura del Proyecto

### Paquetes Principales
```
ibg.inventarios/
├── SpringBootAngularInventarioApplication.java
├── controllers/
│   └── ProductoController.java
├── entities/
│   └── Producto.java
├── services/
│   ├── IProductoService.java
│   └── ProductoService.java
├── supabase/
│   └── Client.java
└── repository/ (comentado)
    └── ProductoRepository.java
```

### Archivos de Configuración
- `application.properties` - Configuración principal
- `application-dev.properties` - Configuración desarrollo
- `pom.xml` - Dependencias Maven

---

## 🔄 Historia de Migración

### Estado Inicial
- **Problema**: Proyecto con conflictos entre JPA y REST API de Supabase
- **Arquitectura mixta**: Intentaba usar JPA + Supabase REST API simultáneamente
- **Errores**: Timeouts de conexión a PostgreSQL, dependencias duplicadas

### Cambios Realizados

#### 1. **Restructuración de Paquetes** ✅
- Movido `models/` → `entities/`
- Actualizado todos los imports correspondientes

#### 2. **Migración a REST API Puro** ✅
- **Eliminado**: Configuración JPA/Hibernate
- **Comentado**: Dependencias `spring-boot-starter-data-jpa` y `postgresql`
- **Comentado**: Anotaciones JPA (`@Entity`, `@Table`, `@Id`, etc.)
- **Comentado**: `ProductoRepository` (guardado para referencia futura)

#### 3. **Servicios Actualizados** ✅
- `ProductoService` ahora usa `Client` de Supabase
- Eliminado `@Transactional` (no necesario con REST API)
- Implementa todos los métodos CRUD via REST calls

#### 4. **Configuración Limpia** ✅
- `application.properties`: Solo configuración REST API
- Credenciales externalizadas con `@Value`
- Puerto corregido y liberado

---

## ⚙️ Configuración Actual

### application.properties
```properties
# Configuración REST API Supabase
supabase.url=${SUPABASE_URL:https://lqrckzoesshhzybollws.supabase.co}
supabase.api.key=${SUPABASE_API_KEY:...}

# Configuración Servidor
server.port=${PORT:8080}

# Logging
logging.level.root=WARN
logging.level.ibg.inventarios=INFO
```

### Dependencias Activas (pom.xml)
```xml
<!-- Dependencias Activas -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>

<!-- Comentadas (JPA) -->
<!-- 
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
-->
```

---

## 📊 Modelo de Datos

### Entidad Producto
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Producto {
    private Integer id_producto;
    private String descripcion;
    private Double precio;
    private Integer cantidad;
}
```

### Productos en Base de Datos (25 total)
1. **Productos Originales**: Laptop, Mouse, Teclado
2. **Productos Agregados**:
   - Smartwatch Apple Watch Series 9 - $399.99
   - Proyector Epson Home Cinema - $699.99
   - Tarjeta Gráfica NVIDIA RTX 4070 - $599.99
   - Memoria RAM 16GB DDR4 - $79.99
   - Placa Base ASUS ROG Strix - $249.99
   - Disco Duro Interno 1TB - $59.99
   - Cargador USB-C 65W - $29.99

---

## 🌐 API REST Endpoints

### Base URL: `http://localhost:8080/api/productos`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/` | Listar todos los productos |
| GET | `/{id}` | Obtener producto por ID |
| POST | `/` | Crear nuevo producto |
| PUT | `/{id}` | Actualizar producto existente |
| DELETE | `/{id}` | Eliminar producto |

### Ejemplo de Uso
```bash
# Listar productos
GET http://localhost:8080/api/productos

# Crear producto
POST http://localhost:8080/api/productos
Content-Type: application/json
{
    "descripcion": "Nuevo Producto",
    "precio": 99.99,
    "cantidad": 10
}
```

---

## 🔧 Arquitectura Técnica

### Patrón de Capas
```
Controller → Service → Client → Supabase API
```

### Flujo de Datos
1. **Controller** recibe petición HTTP
2. **Service** procesa lógica de negocio
3. **Client** hace llamada REST a Supabase
4. **Supabase** retorna datos JSON
5. Respuesta se propaga de vuelta al cliente

### Configuración de CORS
```java
@CrossOrigin(origins = "http://localhost:4200") // Angular
```

---

## 🚀 Comandos de Ejecución

### Iniciar Aplicación
```bash
# Con Maven Wrapper (recomendado)
.\mvnw.cmd spring-boot:run

# Con perfil desarrollo
$env:SPRING_PROFILES_ACTIVE="dev"
.\mvnw.cmd spring-boot:run

# Con puerto específico
.\mvnw.cmd spring-boot:run -Dserver.port=8081
```

### Verificar Estado
```bash
# Verificar puerto libre
netstat -ano | findstr :8080

# Probar API
Invoke-RestMethod -Uri "http://localhost:8080/api/productos" -Method GET
```

---

## 📝 Logging y Debugging

### Logger Configurado
```java
private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

// Ejemplos de uso
logger.info("Productos obtenidos: {}", productos.toString());
productos.forEach(producto -> logger.info(producto.toString()));
```

### Logs de Supabase Client
```java
System.out.println("🔑 Headers enviados: " + headers.toString());
```

### Configuración de Logging
```properties
logging.level.ibg.inventarios=INFO  # Para logs de aplicación
logging.level.ibg.inventarios=DEBUG # Para más detalle
```

---

## ⚠️ Problemas Resueltos

### 1. **Error de Puerto Ocupado**
```
Web server failed to start. Port 8080 was already in use.
```
**Solución**: 
```bash
# Identificar proceso
netstat -ano | findstr :8080
# Terminar proceso
taskkill /PID [PID] /F
```

### 2. **Conflicto JPA/REST API**
```
El intento de conexión falló. SocketTimeoutException: Connect timed out
```
**Solución**: Migrar completamente a REST API, comentar configuración JPA

### 3. **Dependencias Duplicadas**
```
jakarta.persistence-api conflictos de versión
```
**Solución**: Eliminar dependencias redundantes, usar solo las incluidas en starters

### 4. **Encoding de Caracteres**
```
Error de compilación por caracteres especiales en comments
```
**Solución**: Limpiar archivos de configuración, usar solo ASCII en comments

---

## 🔮 Próximos Pasos

### Desarrollo Futuro
1. **Frontend Angular**: Conectar en puerto 4200
2. **Validaciones**: Agregar validaciones en entidades
3. **Manejo de Errores**: Mejorar exception handling
4. **Tests**: Implementar tests unitarios e integración
5. **Seguridad**: Implementar autenticación/autorización

### Posibles Mejoras
- Cambiar `Double` por `BigDecimal` para precios
- Implementar paginación en listados
- Agregar filtros y búsqueda
- Cache de datos frecuentes
- Documentación con Swagger

---

## 📚 Referencias Técnicas

### Tecnologías Utilizadas
- **Spring Boot 3.5.5**: Framework principal
- **Lombok**: Reducción de boilerplate code  
- **Jackson**: Serialización JSON
- **SLF4J + Logback**: Sistema de logging
- **Supabase**: Backend as a Service (PostgreSQL + REST API)
- **Maven**: Gestión de dependencias

### Patrones Implementados
- **Repository Pattern** (comentado, para futura referencia)
- **Service Layer Pattern**
- **REST API Pattern**
- **Dependency Injection**
- **Configuration externalization**

---

## ✅ Estado Final del Proyecto

**✅ COMPLETAMENTE FUNCIONAL**

- ✅ Aplicación Spring Boot iniciada
- ✅ REST API funcionando en puerto 8080  
- ✅ Conexión a Supabase operativa
- ✅ 25 productos en base de datos
- ✅ CRUD completo implementado
- ✅ Logging configurado y funcionando
- ✅ Sin errores de compilación o ejecución
- ✅ Preparado para frontend Angular

**Última actualización**: 9 septiembre 2025
**Desarrollado por**: Iván Bazaga
**Estado**: Proyecto funcional - Listo para desarrollo frontend
