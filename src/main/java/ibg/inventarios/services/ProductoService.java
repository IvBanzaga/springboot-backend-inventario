package ibg.inventarios.services;

import java.util.List;

import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional; // COMENTADO - NO NECESARIO CON REST API

import ibg.inventarios.entities.Producto;
import ibg.inventarios.supabase.Client;

@Service
// @Transactional // COMENTADO - NO NECESARIO CON REST API

public class ProductoService implements IProductoService {

    private final Client supabaseClient;

    public ProductoService(Client supabaseClient) {
        this.supabaseClient = supabaseClient;
    }

    @Override
    // @Transactional(readOnly = true) // COMENTADO - NO NECESARIO CON REST API
    public List<Producto> obtenerTodos() {
        return supabaseClient.getAll();
    }

    @Override
    // @Transactional(readOnly = true) // COMENTADO - NO NECESARIO CON REST API
    public Producto buscarProductoPorId(Integer idProducto) {
        return supabaseClient.getById(idProducto);
    }

    @Override
    public Producto crearProducto(Producto producto) {
        return supabaseClient.create(producto);
    }

    @Override
    public Producto actualizarProducto(Integer idProducto, Producto producto) {
        // Verificar que el producto existe
        Producto productoExistente = supabaseClient.getById(idProducto);
        if (productoExistente == null) {
            throw new IllegalArgumentException("Producto con ID " + idProducto + " no existe.");
        }

        // Actualizar usando Supabase Client
        supabaseClient.update(idProducto, producto);

        // Retornar el producto actualizado
        return supabaseClient.getById(idProducto);
    }

    @Override
    public void eliminarProducto(Integer idProducto) {
        // Verificar que el producto existe
        Producto productoExistente = supabaseClient.getById(idProducto);
        if (productoExistente == null) {
            throw new IllegalArgumentException("Producto con ID " + idProducto + " no existe.");
        }

        supabaseClient.delete(idProducto);
    }
}
