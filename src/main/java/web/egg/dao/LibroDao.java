package web.egg.dao;

import web.egg.entidades.Libro;
import org.springframework.data.repository.CrudRepository;

public interface LibroDao extends CrudRepository<Libro,Long> {
 
}
