package proyecto.ea.AppBiblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.ea.AppBiblioteca.entity.Libro;
import proyecto.ea.AppBiblioteca.service.LibroService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    // 1. Obtener todos los libros
    @GetMapping
    public ResponseEntity<List<Libro>> listarTodosLosLibros() {
        return new ResponseEntity<>(libroService.findAllLibros(), HttpStatus.OK);
    }

    // 2. Buscar libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarLibroPorId(@PathVariable Long id) {
        Optional<Libro> libro = libroService.findLibroById(id);
        if (libro.isPresent()) {
            return new ResponseEntity<>(libro.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el libro
        }
    }

    // 3. Agregar un nuevo libro
    @PostMapping
    public ResponseEntity<Libro> agregarLibro(@RequestBody Libro libro) {
        Libro nuevoLibro = libroService.saveLibro(libro);
        return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
    }

    // 4. Actualizar un libro existente
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        Optional<Libro> libroExistente = libroService.findLibroById(id);

        if (libroExistente.isPresent()) {
            Libro libroActualizado = libroExistente.get();
            libroActualizado.setTitulo(libroDetalles.getTitulo());
            libroActualizado.setAutor(libroDetalles.getAutor());
            libroActualizado.setIsbn(libroDetalles.getIsbn());
            libroService.saveLibro(libroActualizado);  // Guardar los cambios
            return new ResponseEntity<>(libroActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el libro
        }
    }

    // 5. Eliminar un libro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        Optional<Libro> libro = libroService.findLibroById(id);
        if (libro.isPresent()) {
            libroService.deleteLibroById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Eliminado exitosamente
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el libro
        }
    }

    // 6. Prestar un libro
    @PostMapping("/{id}/prestar")
    public ResponseEntity<Libro> prestarLibro(@PathVariable Long id) {
        Libro libroPrestado = libroService.prestarLibro(id);
        return new ResponseEntity<>(libroPrestado, HttpStatus.OK);
    }

    // 7. Devolver un libro
    @PostMapping("/{id}/devolver")
    public ResponseEntity<Libro> devolverLibro(@PathVariable Long id) {
        Libro libroDevuelto = libroService.devolverLibro(id);
        return new ResponseEntity<>(libroDevuelto, HttpStatus.OK);
    }
}