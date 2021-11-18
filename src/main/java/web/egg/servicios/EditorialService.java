package web.egg.servicios;

import java.util.List;
import web.egg.entidades.Editorial;


public interface EditorialService {

    
    public List<Editorial> ListaEditorial();
    
    public void GuardarEditorial( Editorial editorial );
    
    public boolean EliminarEditorial( Editorial editorial );
   
    public Editorial BuscarEditorial( Editorial editorial );
 
    public Editorial BuscarIDEditorial(Long id );
    
    public List<Editorial> BuscarEditorialPorNombre(String szName ); 
    public void CambiarEstadoEditorial(Editorial ed);
    public void VerificarEditorialDuplicada(Editorial editorial);
    
    
}
