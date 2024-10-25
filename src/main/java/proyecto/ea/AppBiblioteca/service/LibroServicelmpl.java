package proyecto.ea.AppBiblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.ea.AppBiblioteca.entity.Libro;
import proyecto.ea.AppBiblioteca.repository.LibroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServicelmpl implements LibroService{

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> findAllLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Optional<Libro> findLibroById(Long idLibro) {
        return libroRepository.findById(idLibro);
    }

    @Override
    public Libro saveLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public void updateLibro(Long idLibro, Libro libro) {
        Libro libroBD = findLibroById(idLibro).get();
        libroBD.setTitulo(libro.getTitulo());
        libroBD.setAutor(libro.getAutor());
        libroBD.setIsbn(libro.getIsbn());
        libroRepository.save(libroBD); // Guardar los cambios en libroBD
    }

    @Override
    public void deleteLibroById(Long idLibro) {
        libroRepository.deleteById(idLibro);
    }

    @Override
    public Libro prestarLibro(Long idLibro) {
        Libro libro = findLibroById(idLibro).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        if (libro.isPrestado()) {
            throw new RuntimeException("El libro ya está prestado");
        }
        libro.setPrestado(true);
        return libroRepository.save(libro);
    }

    @Override
    public Libro devolverLibro(Long idLibro) {
        Libro libro = findLibroById(idLibro).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        if (!libro.isPrestado()) {
            throw new RuntimeException("El libro no está prestado");
        }
        libro.setPrestado(false);
        return libroRepository.save(libro);
    }
}