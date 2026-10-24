package edu.college.library.controller;

import edu.college.library.model.Book;
import edu.college.library.model.LibraryRecord;
import edu.college.library.repository.BookRepository;
import edu.college.library.repository.LibraryRecordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LibraryController {
    private final BookRepository books;
    private final LibraryRecordRepository records;
    public LibraryController(BookRepository books, LibraryRecordRepository records) {
        this.books = books; this.records = records;
    }

    @GetMapping("/books")
    public List<Book> getBooks(@RequestParam(required=false) String search) {
        if (search == null || search.isBlank()) return books.findAll();
        return books.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrDepartmentContainingIgnoreCase(search, search, search);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book title is required");
        if (book.getTotalCopies() < 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Copies must be at least 1");
        book.setAvailableCopies(book.getTotalCopies());
        return books.save(book);
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable String id, @RequestBody Book incoming) {
        Book book = books.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        book.setTitle(incoming.getTitle()); book.setAuthor(incoming.getAuthor());
        book.setIsbn(incoming.getIsbn()); book.setDepartment(incoming.getDepartment());
        int issued = book.getTotalCopies() - book.getAvailableCopies();
        book.setTotalCopies(Math.max(incoming.getTotalCopies(), issued));
        book.setAvailableCopies(book.getTotalCopies() - issued);
        return books.save(book);
    }

    @DeleteMapping("/books/{id}")
    public Map<String,String> deleteBook(@PathVariable String id) {
        Book book = books.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        if (book.getAvailableCopies() != book.getTotalCopies())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Return issued copies before deleting this book");
        books.delete(book); return Map.of("message", "Book deleted");
    }

    @GetMapping("/records")
    public List<LibraryRecord> getRecords(@RequestParam(required=false) String status) {
        return status == null || status.isBlank() ? records.findAllByOrderByIssueDateDesc() : records.findByStatusOrderByIssueDateDesc(status);
    }

    @PostMapping("/issue/{bookId}")
    public LibraryRecord issueBook(@PathVariable String bookId, @RequestBody Map<String,String> input) {
        Book book = books.findById(bookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        String studentName = input.getOrDefault("studentName", "").trim();
        String studentId = input.getOrDefault("studentId", "").trim();
        String department = input.getOrDefault("department", "").trim();
        if (studentName.isBlank() || studentId.isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student name and college ID are required");
        if (book.getAvailableCopies() < 1)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No copies available");
        book.setAvailableCopies(book.getAvailableCopies() - 1); books.save(book);
        LibraryRecord record = new LibraryRecord();
        record.setBookId(book.getId()); record.setBookTitle(book.getTitle());
        record.setStudentName(studentName); record.setStudentId(studentId); record.setDepartment(department);
        record.setStatus("ISSUED"); record.setIssueDate(LocalDate.now().toString());
        record.setDueDate(LocalDate.now().plusDays(14).toString());
        return records.save(record);
    }

    @PostMapping("/return/{recordId}")
    public LibraryRecord returnBook(@PathVariable String recordId) {
        LibraryRecord record = records.findById(recordId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
        if (!"ISSUED".equals(record.getStatus()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This book has already been returned");
        Book book = books.findById(record.getBookId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book record missing"));
        book.setAvailableCopies(Math.min(book.getTotalCopies(), book.getAvailableCopies() + 1)); books.save(book);
        record.setStatus("RETURNED"); record.setReturnDate(LocalDate.now().toString());
        return records.save(record);
    }

    @GetMapping("/dashboard")
    public Map<String,Long> dashboard() {
        List<Book> allBooks = books.findAll();
        long titles = allBooks.size();
        long copies = allBooks.stream().mapToLong(Book::getTotalCopies).sum();
        long available = allBooks.stream().mapToLong(Book::getAvailableCopies).sum();
        long issued = records.findByStatusOrderByIssueDateDesc("ISSUED").size();
        return Map.of("titles", titles, "copies", copies, "available", available, "issued", issued);
    }
}
