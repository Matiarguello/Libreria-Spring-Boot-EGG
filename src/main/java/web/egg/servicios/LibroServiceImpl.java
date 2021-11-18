package web.egg.servicios;



import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.egg.dao.LibroDao;
import web.egg.entidades.*;


@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroDao librodao;
    
    
    @Autowired  
    private AutorService autorsv;
    
    @Autowired
    private EditorialService editorialsv;

    @Override
    @Transactional(readOnly = true)
    public List<Libro> ListarLibros() {
        return (List<Libro>) librodao.findAll();
    }
    
    @Override
    @Transactional
    public void GuardarLibro(Libro libro) {
        
        Autor new_autor = libro.getAutor();
        Editorial new_editorial = libro.getEditorial();

        /* ===========[ ES UNA ENTIDAD NULA ? ] =========== */
        
        if(new_autor == null){
            new_autor = new Autor();
            libro.setAutor(new_autor);
        }
        if(new_editorial == null){
            new_editorial = new Editorial();
            libro.setEditorial(new_editorial);
        }  
        
        if( new_autor.getNombre_autor() == null ) 
            new_autor.setNombre_autor("(Desconocido)");
        
        if( new_editorial.getNombre_editorial()== null ) 
            new_editorial.setNombre_editorial("(Desconocida");
        
        /* Este fix lo habia hecho para testear una cosa, pero igual lo dejo por las dudas, quien sabe (? */
        //==============
        
        
        new_autor.setAlta_autor(true);
        new_editorial.setAlta_editorial(true);  
        
        /*============ [ VERIFICAMOS QUE NO ESTEN REPETIDOS ] =========*/
        
        editorialsv.VerificarEditorialDuplicada(new_editorial);
        autorsv.VerificarAutorDuplicado(new_autor);
        
        
        /* =========== [ GUARDAMOS LOS DATOS ] =========== */ 

        librodao.save(libro);
        autorsv.GuardarAutor(new_autor);
        editorialsv.GuardarEditorial(new_editorial);
    }


    @Override
    @Transactional    
    public boolean EliminarLibro(Libro libro) {
        
        if( libro == null || libro.getTitulo()== null )
            return false;
            
        librodao.delete(libro);
        return true;
    }
    

    @Override
    @Transactional(readOnly = true)   
    public Libro BuscarLibro(Libro libro) {
        return librodao.findById( libro.getId_libro() ).orElse(null); 
    }

    
    @Override
    @Transactional(readOnly = true) 
    public List<Libro> BuscarID(Long id) {
       
        List< Libro > libros = new ArrayList();
        List< Libro > newlist = new ArrayList();
        
        libros = ListarLibros();
        
        for( Libro listadelibros : libros ){
           
           if(listadelibros.getId_libro()== id){
               newlist.add(listadelibros);
           } 
        }
        
        if(newlist.isEmpty() || newlist == null)
            return null;

        return newlist;
    }

    @Override
    @Transactional(readOnly = true)    
    public List<Libro> BuscarPorNombre(String szname) {
  
        var libros = ListarLibros();
        List< Libro > newlist = new ArrayList();
        
        for( Libro listadelibros : libros ){
            
            String temp = listadelibros.getTitulo();
            
            if( temp.contains(szname) || temp.equalsIgnoreCase(szname) ){
               newlist.add(listadelibros);
            }
        }
        
        if(newlist.isEmpty() || newlist == null){
            return null;
        }
        
        return newlist;
    }

    @Override
    @Transactional(readOnly = true)    
    public List<Libro> BuscarPorAutor(String szname) {
        
        List< Libro > libros = new ArrayList();
        List< Libro > newlist = new ArrayList();
        
        libros = ListarLibros();
        
        for( Libro listadelibros : libros ){
            
            Autor autor_temp = listadelibros.getAutor();
            String temp = autor_temp.getNombre_autor();
            
            if( temp.contains(szname) || temp.equalsIgnoreCase(szname) ){
               newlist.add(listadelibros);
            }
            
        }
        
        if( newlist.isEmpty() || newlist == null )
            return null;

        return newlist;      
    }

    @Override
    @Transactional
    public void CambiarEstadoLibro(Libro libro) {
        libro.setAlta(!libro.getAlta());
        librodao.save(libro);
    }
    
}
