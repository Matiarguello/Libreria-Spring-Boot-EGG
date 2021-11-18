package web.egg.dao;


import web.egg.entidades.Libro;
import org.springframework.data.repository.CrudRepository;
import web.egg.entidades.Editorial;

public interface EditorialDao extends CrudRepository<Editorial,Long> {
 
}
