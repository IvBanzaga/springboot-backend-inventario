package ibg.inventarios.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
// import java.math.BigDecimal;
// import jakarta.persistence.*; // COMENTADO - USANDO REST API
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Entity // COMENTADO - USANDO REST API
// @Table(name = "producto") // COMENTADO - USANDO REST API
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignorar atributos nulos en JSON
public class Producto {

    // @Id // COMENTADO - USANDO REST API
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // COMENTADO - USANDO
    // REST API
    // @Column(name = "id_producto") // COMENTADO - USANDO REST API
    private Integer id_producto; // bigint → Long

    // @Column(name = "descripcion") // COMENTADO - USANDO REST API
    private String descripcion;

    // @Column(name = "precio") // COMENTADO - USANDO REST API
    private Double precio; // numeric → BigDecimal

    // @Column(name = "cantidad") // COMENTADO - USANDO REST API
    private Integer cantidad;

    // @Column(name = "nombre") // COMENTADO - USANDO REST API
    private String nombre;

    // @Column(name = "imagen_url") // COMENTADO - USANDO REST API
    private String imagen_url;

}
