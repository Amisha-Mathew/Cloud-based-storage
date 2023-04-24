package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

@RestController
@RequestMapping("/users")
public class Controller {
    private Connection connection;

    public Controller() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "ala");

    }

    private List<Model> userFilesList = new ArrayList<>();

    @PostMapping("/files")

    public ResponseEntity<Model> addUserFile(@RequestParam Integer userId,
            @RequestParam("file") MultipartFile file) throws SQLException, IOException {
        byte[] fileContent = file.getBytes();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO user_files (user_id, filename, file_content) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, userId);
        statement.setString(2, file.getOriginalFilename());
        statement.setBytes(3, fileContent);
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        // int id = rs.getInt(1);
        statement.close();
        Model model = new Model(userId, file.getOriginalFilename(), fileContent);
        // Return a html response saying the file was uploaded
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/files")
    // Get all files
    // curl -X GET http://localhost:8080/users/files?userId=12345678
    public ResponseEntity<List<Model>> getAllUserFiles(@RequestParam Integer userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_files WHERE user_id = ?");
        ResultSet rs = statement.executeQuery();
        List<Model> userFilesList = new ArrayList<>();
        while (rs.next()) {
            // int id = rs.getInt("id");
            int userId2 = rs.getInt("user_id");
            String filename = rs.getString("filename");
            byte[] fileContent = rs.getBytes("file_content");
            Model model = new Model(userId2, filename, fileContent);
            userFilesList.add(model);
        }
        statement.close();
        return new ResponseEntity<>(userFilesList, HttpStatus.OK);
    }

    @PutMapping("/files")
    public ResponseEntity<String> updateUserFile( @RequestParam Integer userId, @RequestParam MultipartFile file)
            throws SQLException, IOException {
        PreparedStatement statement;
        byte[] fileContent = file.getBytes();

        statement = connection.prepareStatement("UPDATE user_files SET file_content = ? WHERE user_id = ?");
        statement.setBytes(1, fileContent);
        statement.setInt(2, userId);
        int result = statement.executeUpdate();
        if (result == 0) {
            return new ResponseEntity<>("File not Updated",HttpStatus.NOT_FOUND);
        }
        statement.close();
        return new ResponseEntity<>("File Updated",HttpStatus.OK);
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<String> deleteUserFile(@PathVariable Integer id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user_files WHERE id = ?");
        statement.setInt(1, id);
        int result = statement.executeUpdate();
        if (result == 0) {
            return new ResponseEntity<>("File not Deleted",HttpStatus.NOT_FOUND);
        }
        statement.close();
        return new ResponseEntity<>("File Deleted",HttpStatus.OK);
    }
}
