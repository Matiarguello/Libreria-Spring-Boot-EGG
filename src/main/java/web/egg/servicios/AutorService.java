package web.egg.servicios;

import java.util.List;
import web.egg.entidades.Autor;


public interface AutorService {
    
    public List<Autor> ListarAutores();
    
    public void GuardarAutor( Autor autor );
    
    public boolean EliminarAutor( Autor autor );
    
    public Autor BuscarAutor( Autor autor );
 
    public Autor BuscarIDAutor(Long id );
    
    public List<Autor> BuscarAutorPorNombre(String id ); 
    
    public void CambiarEstadoAutor( Autor autor );
    public void VerificarAutorDuplicado(Autor autor);
}
