
package web.egg.entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "editorial")
public class Editorial implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long ideditorial;     
    
    @NotEmpty
    private String nombre_editorial;
    
    private Boolean alta_editorial;
}
