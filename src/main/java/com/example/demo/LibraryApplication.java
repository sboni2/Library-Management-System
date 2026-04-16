package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
@RestController
@CrossOrigin
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> data) {

        String user = data.get("username");
        String pass = data.get("password");

        System.out.println("Login attempt -> User: " + user + " Pass: " + pass);

        if ("admin".equals(user) && "1234".equals(pass)) {
            return "admin";
        }

        if ("user".equals(user) && "1234".equals(pass)) {
            return "user";
        }

        return "fail";
    }

    // ================= TEST =================
    @GetMapping("/")
    public String home() {
        return "Library System Running";
    }

    // ================= BOOK MODEL =================
    static class Book {
        public int id;
        public String title;
        public String author;
        public int quantity;
    }

    List<Book> books = new ArrayList<>();

    // ================= ADD BOOK =================
    @PostMapping("/addBook")
    public String addBook(@RequestBody Book b) {

        if (b.title == null || b.author == null || b.quantity <= 0) {
            return "Fill all fields correctly";
        }

        books.add(b);
        return "Book Added Successfully";
    }

    // ================= GET BOOKS =================
    @GetMapping("/books")
    public List<Book> getBooks() {
        return books;
    }

    // ================= ISSUE BOOK =================
    @PostMapping("/issue")
    public String issueBook(@RequestBody Map<String, String> data) {

        String bookName = data.get("bookName");

        for (Book b : books) {
            if (b.title.equalsIgnoreCase(bookName) && b.quantity > 0) {
                b.quantity--;
                return "Book Issued";
            }
        }

        return "Book not available";
    }

    // ================= RETURN BOOK =================
    @PostMapping("/return")
    public String returnBook(@RequestBody Map<String, String> data) {

        String bookName = data.get("bookName");

        for (Book b : books) {
            if (b.title.equalsIgnoreCase(bookName)) {
                b.quantity++;
                return "Book Returned";
            }
        }

        return "Book not found";
    }

    // ================= PAY FINE =================
    @PostMapping("/payFine")
    public String payFine(@RequestBody Map<String, String> data) {
        return "Fine Paid Successfully";
    }
}