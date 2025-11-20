package ibg.inventarios.supabase;


import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ibg.inventarios.entities.Producto;

@Service
public class Client {

    private final RestTemplate restTemplate;

    @Value("${supabase.url}")
    private String BASE_URL;

    @Value("${supabase.api.key}")
    private String API_KEY;

    // ✅ Ahora usamos HttpComponentsClientHttpRequestFactory para soportar PATCH
    public Client() {
        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    // =================== GET ===================
    public List<Producto> getAll() {
        HttpHeaders headers = getHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Producto[]> response = restTemplate.exchange(
                BASE_URL + "/rest/v1/producto",
                HttpMethod.GET,
                entity,
                Producto[].class
        );

        Producto[] productos = response.getBody();
        return (productos != null) ? List.of(productos) : List.of();
    }

    public Producto getById(Integer id) {
        HttpHeaders headers = getHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Producto[]> response = restTemplate.exchange(
                BASE_URL + "/rest/v1/producto?id_producto=eq." + id,
                HttpMethod.GET,
                entity,
                Producto[].class
        );

        Producto[] productos = response.getBody();
        return (productos != null && productos.length > 0) ? productos[0] : null;
    }

    // =================== POST ===================
    public Producto create(Producto producto) {
        HttpHeaders headers = getHeaders();
        headers.set("Prefer", "return=representation");
        HttpEntity<Producto> entity = new HttpEntity<>(producto, headers);

        ResponseEntity<Producto[]> response = restTemplate.exchange(
                BASE_URL + "/rest/v1/producto",
                HttpMethod.POST,
                entity,
                Producto[].class
        );

        Producto[] productos = response.getBody();
        return (productos != null && productos.length > 0) ? productos[0] : null;
    }

    // =================== PATCH (UPDATE) ===================
    public void update(Integer id, Producto producto) {
        HttpHeaders headers = getHeaders();
        headers.set("Prefer", "return=representation"); // Para ver la respuesta

        HttpEntity<Producto> entity = new HttpEntity<>(producto, headers);

        String url = BASE_URL + "/rest/v1/producto?id_producto=eq." + id;
        System.out.println("🔄 PATCH → " + url);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                entity,
                String.class
        );

        System.out.println("📡 Respuesta PATCH: " + response.getBody());
    }



    // =================== DELETE ===================
    public void delete(Integer id) {
        HttpHeaders headers = getHeaders();
        headers.set("Prefer", "return=representation"); // Para ver la respuesta

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = BASE_URL + "/rest/v1/producto?id_producto=eq." + id;
        System.out.println("🗑️ DELETE → " + url);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        System.out.println("📡 Respuesta DELETE: " + response.getBody());
    }



    // =================== HEADERS ===================
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        // Log temporal para debugging
        System.out.println("🔑 Headers enviados: " + headers.toString());

        return headers;
    }

}
