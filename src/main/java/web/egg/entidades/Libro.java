package web.egg.entidades;


import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "libro")
public class Libro implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_libro;
    
    @NotEmpty
    private String titulo;
    
    @NotNull
    private Integer year;
    
    @NotNull 
    private Integer prestados;
    
    @NotNull 
    private Integer ejemplares;
    
    @NotNull  
    private Integer restantes;
    
    @NotNull 
    private Integer isbn;
    
    private Boolean alta;

    @ManyToOne
    private Autor autor;
    
    @ManyToOne
    private Editorial editorial;
  
}
