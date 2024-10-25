package proyecto.ea.AppBiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.ea.AppBiblioteca.entity.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

}