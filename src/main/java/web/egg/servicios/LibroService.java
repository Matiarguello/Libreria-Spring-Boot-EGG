package web.egg.servicios;

import java.util.List;
import web.egg.entidades.Libro;


public interface LibroService {
    
    public List<Libro> ListarLibros();
    
    public void GuardarLibro( Libro libro );
    
    public boolean EliminarLibro( Libro libro );
    
    public Libro BuscarLibro( Libro libro );
 
    public List<Libro> BuscarID(Long id );
    
    public List<Libro> BuscarPorNombre(String id );  

    public List<Libro> BuscarPorAutor(String szname);
    
    public void CambiarEstadoLibro( Libro libro );

}
