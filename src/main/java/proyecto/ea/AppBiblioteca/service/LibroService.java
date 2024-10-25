package proyecto.ea.AppBiblioteca.service;

import proyecto.ea.AppBiblioteca.entity.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {
    // CRUD para la entidad Libro

    // 1. Listar todos los libros
    List<Libro> findAllLibros();

    // 2. Buscar por el ID del libro
    Optional<Libro> findLibroById(Long idLibro);

    // 3. Guardar el libro
    Libro saveLibro(Libro libro);

    // 4. Actualizar el libro
    void updateLibro(Long idLibro, Libro libro);

    // 5. Eliminar el libro
    void deleteLibroById(Long idLibro);

    // Nuevos métodos para prestar y devolver libros

    Libro prestarLibro(Long idLibro);
    Libro devolverLibro(Long idLibro);
}