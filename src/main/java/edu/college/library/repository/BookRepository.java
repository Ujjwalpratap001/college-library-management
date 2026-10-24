package edu.college.library.repository;
import edu.college.library.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String title, String author, String department);
}
