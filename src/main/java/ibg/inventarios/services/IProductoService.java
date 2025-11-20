package ibg.inventarios.services;

import ibg.inventarios.entities.Producto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IProductoService {

    // Definir los métodos del servicio

    List<Producto> obtenerTodos();

    Producto buscarProductoPorId(Integer idProducto);

    Producto crearProducto(Producto producto);

    Producto actualizarProducto(Integer idProducto, Producto producto);

    void eliminarProducto(Integer idProducto);

}
