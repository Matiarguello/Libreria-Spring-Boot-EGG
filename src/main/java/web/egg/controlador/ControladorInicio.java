package web.egg.controlador;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.egg.entidades.*;
import web.egg.servicios.*;

@Controller
@Slf4j
public class ControladorInicio {

    @Autowired
    private LibroService libroservice;
    @Autowired
    private AutorService autorsv;
    @Autowired
    private EditorialService editorialsv;

    private List<Libro> busqueda = new ArrayList();
    private Integer searchbooks = 0;

    
    /* Para un proyecto más grande no es conveniente agregar todo en un controlador
       pero la libreria no es gran cosa, asi que venga..
    */
            
    @GetMapping("/")
    public String inicio(Model model) {

        var libros = libroservice.ListarLibros();
        var autores = autorsv.ListarAutores();
        var editoriales = editorialsv.ListaEditorial();

        model.addAttribute("libros", libros);
        model.addAttribute("searchbooks", searchbooks);
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);

        if( busqueda != null && !busqueda.isEmpty() ) {
            model.addAttribute("busqueda", busqueda);
        }

        return "index";
    }

    @GetMapping("/librosdesactivados")
    public String librosdesactivados(Model model) {

        List<Libro> new_libros = new ArrayList();

        var libros_desactivados = libroservice.ListarLibros();

        for( Libro lista : libros_desactivados ) {
            if( !lista.getAlta() ){
                new_libros.add(lista);
            }
        }
        
        for(Libro lista: new_libros){
            System.out.println(lista);
        }

         model.addAttribute("new_libros", new_libros);
   
        return "libros_debaja";
    }

    /* Con esto agregas y modificas al a vez */
    
    @GetMapping("/agregar")
    public String agregar(Libro libro) {
        return "modificar_libro";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Libro libro, Errors errores,RedirectAttributes attribute) {

        if (errores.hasErrors())
            return "modificar_libro";
        
        libroservice.GuardarLibro(libro);
        attribute.addFlashAttribute("success", "El libro fue guardado correctamente!");
        ResetSearch();
        return "redirect:/";
    }

    @GetMapping("/editar/{id_libro}")
    public String editar(Libro libro, Model model, RedirectAttributes attribute) {
        libro = libroservice.BuscarLibro(libro);
        
        if( libro.getTitulo() == null ){
            attribute.addFlashAttribute("error", "Error: Este libro no existe!");
            return "redirect:/"; 
        }

        model.addAttribute("libro", libro);      
        return "modificar_libro";
    }

    @GetMapping("/eliminar/{id_libro}")
    public String eliminar(Libro libro,RedirectAttributes attribute) {
        
        if( libroservice.EliminarLibro( libroservice.BuscarLibro( libro ) ) )
            attribute.addFlashAttribute("success", "El libro fue eliminado correctamente!");
        
        else 
            attribute.addFlashAttribute("error", "Error: Libro no existente o erróneo");
        return "redirect:/";
    }

    @GetMapping("/estadolibro/{id_libro}")
    public String estadolibro(Libro libro,RedirectAttributes attribute) {
        libro = libroservice.BuscarLibro(libro);
        
        if(libro == null){
            attribute.addFlashAttribute("error", "Error: Libro no existente o erróneo");
            return "redirect:/";
        }
        libroservice.CambiarEstadoLibro(libro);

        return "redirect:/";
    }

    @GetMapping("/estadolibro2/{id_libro}")
    public String estadolibro2(Libro libro,Model model,RedirectAttributes attribute) {
        libro = libroservice.BuscarLibro(libro);
        
        if(libro == null){
            attribute.addFlashAttribute("error", "Error: Libro no existente o erróneo");
            return "redirect:/";
        }
        attribute.addFlashAttribute("success", "El libro fue dado de alta!");
        libroservice.CambiarEstadoLibro(libro);
        ResetSearch();
        return "redirect:/";
    }

    /* =================[ SERVICIO DE BÚSQUEDA ]=================*/

    @GetMapping("/buscarporid")
    public String buscarporid(Model model, @RequestParam Long keyword,RedirectAttributes attribute) {
        busqueda = libroservice.BuscarID(keyword);
        searchbooks++;
        return "redirect:/";
    }

    @GetMapping("/buscarpornombre")
    public String buscarpornombre(Model model, @RequestParam String szname) {

        if (szname == null || szname.isEmpty()) {
            return "redirect:/";
        }

        busqueda = libroservice.BuscarPorNombre(szname);
        searchbooks++;
        return "redirect:/";
    }

    @GetMapping("/buscarporautor")
    public String buscarporautor(Model model, @RequestParam String szname,RedirectAttributes attribute) {
        if (szname == null || szname.isEmpty()) {
            return "redirect:/";
        }

        busqueda = libroservice.BuscarPorAutor(szname);
        searchbooks++;
        return "redirect:/";
    }

    
    
    /* =================[ AUTOR ]=================*/

    @GetMapping("/editarautor/{idautor}")
    public String editarautor(Autor autor, Model model, RedirectAttributes attribute) {
        autor = autorsv.BuscarAutor(autor);
        
         if(  autor == null || autor.getNombre_autor() == null){
            attribute.addFlashAttribute("error", "Error: Autor no existente o erróneo");
            return "redirect:/";
        }
                
        model.addAttribute("autor", autor);
        return "modificar_autor";
    }


    @PostMapping("/guardarautor")
    public String guardarautor(@Valid Autor autor, Errors errores, RedirectAttributes attribute) {

        if (errores.hasErrors()) {
            return "modificar_autor";
        }
        
        autorsv.VerificarAutorDuplicado(autor);
        autorsv.GuardarAutor(autor);
        attribute.addFlashAttribute("success", "El autor fue guardado correctamente!"); 
        ResetSearch();        
        return "redirect:/";
    }
    
    @GetMapping("/agregarautor")
    public String agregarautor(Autor autor) {
        return "modificar_autor";
    }  
    
    
    @GetMapping("/estadoautor/{idautor}")
    public String estadoautor(Autor autor,RedirectAttributes attribute) {
        autor = autorsv.BuscarAutor(autor);
        
         if( autor == null || autor.getNombre_autor() == null ){
            attribute.addFlashAttribute("error", "Error: Autor no existente o erróneo");
            return "redirect:/";
        }
        autorsv.CambiarEstadoAutor(autor);
        return "redirect:/";
    }
    
    @GetMapping("/eliminarautor/{idautor}")
    public String eliminarautor(Autor autor, RedirectAttributes attribute) {
        
        autor = autorsv.BuscarAutor(autor);
        
        if( autor == null || autor.getNombre_autor() == null)
            attribute.addFlashAttribute("error", "Error: Autor no existente o erróneo");
        
        else if(!autorsv.EliminarAutor(autor))
            attribute.addFlashAttribute("error", "ERROR: No se pudo eliminar el autor porque está en un libro existente");
              

        else
           attribute.addFlashAttribute("success", "El autor fue eliminado correctamente!");             

        return "redirect:/";
    }
  
    /* =================[ EDITORIAL ]=================*/
    
    
    @GetMapping("/editareditorial/{ideditorial}")
    public String editareditorial(Editorial editorial, Model model,RedirectAttributes attribute) {
        editorial = editorialsv.BuscarEditorial(editorial);
        
         if( editorial == null || editorial.getNombre_editorial() == null){
            attribute.addFlashAttribute("error", "ERROR: La editorial no existe");
            return "redirect:/";
        }  
         
        model.addAttribute("editorial", editorial);
        return "modificar_editorial";
    }

    @PostMapping("/guardareditorial")
    public String guardareditorial(@Valid Editorial editorial, Errors errores,RedirectAttributes attribute) {

        if (errores.hasErrors()) {
            return "modificar_editorial";
        }
        editorialsv.VerificarEditorialDuplicada(editorial);
        editorialsv.GuardarEditorial(editorial);
        ResetSearch();        
        return "redirect:/";
    }

    @GetMapping("/estadoeditorial/{ideditorial}")
    public String estadoeditorial(Editorial ed,RedirectAttributes attribute) {
        ed = editorialsv.BuscarEditorial(ed);
        
         if( ed == null || ed.getNombre_editorial() == null){
            attribute.addFlashAttribute("error", "ERROR: La editorial no existe");
            return "redirect:/";
        }       
        
        editorialsv.CambiarEstadoEditorial(ed);
        
        return "redirect:/";
    }
    
    @GetMapping("/agregareditorial")
    public String agregareditorial(Editorial ed,RedirectAttributes attribute) {
        return "modificar_editorial";
    }    
   
    @GetMapping("/eliminareditorial/{ideditorial}")
    public String eliminareditorial(Editorial editorial, RedirectAttributes attribute) {
        
         editorial = editorialsv.BuscarEditorial(editorial);

        if( editorial == null || editorial.getNombre_editorial() == null )
            attribute.addFlashAttribute("error", "ERROR: La editorial no existe");
        
        else if(!editorialsv.EliminarEditorial(editorial))
            attribute.addFlashAttribute("error", "ERROR: No se pudo eliminar la editorial porque está en un libro existente");  
             
        else
            attribute.addFlashAttribute("success", "La editorial fue eliminada correctamente!");           

        return "redirect:/";
    }   
    
    /* =================[ EXTRAS ]=================*/
    
    @RequestMapping("/404")
    public String ErrorPage404() {
        return "error404";
    }
    @RequestMapping("/500")
    public String ErrorPage500() {
        return "error500";
    }
    
    @GetMapping("/about")
    public String about() {
        return "layout/about";
    }        
    
    
    public void ResetSearch(){
        searchbooks = 0;
        busqueda = null;
    }
}
