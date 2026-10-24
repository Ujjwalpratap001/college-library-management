package edu.college.library.repository;
import edu.college.library.model.LibraryRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface LibraryRecordRepository extends MongoRepository<LibraryRecord, String> {
    List<LibraryRecord> findAllByOrderByIssueDateDesc();
    List<LibraryRecord> findByStatusOrderByIssueDateDesc(String status);
}
