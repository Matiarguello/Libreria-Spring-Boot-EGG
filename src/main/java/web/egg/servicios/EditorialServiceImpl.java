package web.egg.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.egg.dao.EditorialDao;
import web.egg.entidades.Editorial;
import web.egg.entidades.Libro;

@Service
public class EditorialServiceImpl implements EditorialService {

    @Autowired
    private EditorialDao editorialdao;
    
    @Autowired
    private LibroService librosv;
    
    @Override
    @Transactional(readOnly = true)
    public List<Editorial> ListaEditorial() {
        return (List<Editorial>) editorialdao.findAll();
    }

    @Override
    public void GuardarEditorial(Editorial editorial) {
        editorialdao.save(editorial);
    }

    @Override
    public boolean EliminarEditorial(Editorial editorial) {
        
        boolean eliminar = true;
        
        var Libros = librosv.ListarLibros();
        
        for(Libro l : Libros){
            if(l.getEditorial().getIdeditorial() == editorial.getIdeditorial()){
                eliminar = false;
                break;
            }
        }
        
        if( eliminar )
            editorialdao.delete(editorial);
        
        return eliminar;
    }

    @Override
    @Transactional(readOnly = true)
    public Editorial BuscarEditorial(Editorial editorial) {
        return editorialdao.findById(editorial.getIdeditorial()).orElse(null);
    }

    @Override
    public Editorial BuscarIDEditorial(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<Editorial> BuscarEditorialPorNombre(String szName) {

        List< Editorial> editoriales = new ArrayList();
        List< Editorial> newlist = new ArrayList();

        editoriales = ListaEditorial();

        for (Editorial listadeeditoriales : editoriales) {
            String temp = listadeeditoriales.getNombre_editorial();

            if (temp.contains(szName)) {
                newlist.add(listadeeditoriales);
            }
        }

        if (newlist.isEmpty() || newlist == null) {
            return null;
        }

        return newlist;
    }

    @Override
    public void CambiarEstadoEditorial(Editorial ed) {
        ed.setAlta_editorial(!ed.getAlta_editorial());
        editorialdao.save(ed);
    }
    
    @Override
    @Transactional(readOnly = true) 
    public void VerificarEditorialDuplicada(Editorial editorial) {
        String szEditorial = editorial.getNombre_editorial();

        var Editoriales = ListaEditorial();

        for (Editorial aux : Editoriales) {
            String temp = aux.getNombre_editorial();

            if (szEditorial.equalsIgnoreCase(temp)) {
                editorial.setIdeditorial(aux.getIdeditorial());
                editorial.setAlta_editorial(aux.getAlta_editorial());
                break;
            }
        }

    }

}
