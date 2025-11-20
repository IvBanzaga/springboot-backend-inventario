package ibg.inventarios.controllers;

import ibg.inventarios.entities.Producto;
import ibg.inventarios.services.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "https://angular-spring-boot-1l9fuzgts-tenerifedev.vercel.app/") // Angular corre en 4200
public class ProductoController {

    // Servicio para manejar la lógica de negocio
    private final IProductoService productoService;

    // Logger para registrar información
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    // Inyección de dependencia a través del constructor
    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    // ✔ Listar todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        List<Producto> productos = productoService.obtenerTodos();
        logger.info("Productos obtenidos: {}", productos.toString());
        productos.forEach(producto -> logger.info(producto.toString()));
        return productos; // ← Retornar la lista 
    }

    // ✔ Obtener un producto por ID , la url sería: http://localhost:8080/api/productos/1
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
        Producto producto = productoService.buscarProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✔ Crear un nuevo producto la url sería: http://localhost:8080/api/productos
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    // ✔ Actualizar un producto existente la url sería: http://localhost:8080/api/productos/1
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody(required = false) Producto producto) {
        // Si no se envía cuerpo, crear un objeto vacío o buscar el existente
        if (producto == null) {
            // Obtener el producto existente para mantener sus datos actuales
            producto = productoService.buscarProductoPorId(id);
            if (producto == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        if (productoActualizado != null) {
            return ResponseEntity.ok(productoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✔ Eliminar un producto la url sería: http://localhost:8080/api/productos/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

}
