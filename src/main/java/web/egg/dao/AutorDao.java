package web.egg.dao;


import web.egg.entidades.Libro;
import org.springframework.data.repository.CrudRepository;
import web.egg.entidades.Autor;

public interface AutorDao extends CrudRepository<Autor,Long> {
 
}
