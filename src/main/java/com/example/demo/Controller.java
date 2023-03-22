package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import java.io.File;
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

    @PostMapping("/{userId}/files")
    public ResponseEntity<Model> addUserFile(@PathVariable Integer userId, @RequestParam("numFiles") int numFiles,
            @RequestParam("file") MultipartFile file) throws SQLException, IOException {
        byte[] fileContent = file.getBytes();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO user_files (user_id, num_files, filename, file_content) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, userId);
        statement.setInt(2, numFiles);
        statement.setString(3, file.getOriginalFilename());
        statement.setBytes(4, fileContent);
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        statement.close();
        Model model = new Model(id, userId, numFiles, file.getOriginalFilename(), fileContent);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @GetMapping("/files")
    // Get all files
    // curl -X GET http://localhost:8080/users/files
    public ResponseEntity<List<Model>> getAllUserFiles() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_files");
        ResultSet rs = statement.executeQuery();
        List<Model> userFilesList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            int numFiles = rs.getInt("num_files");
            String filename = rs.getString("filename");
            byte[] fileContent = rs.getBytes("file_content");
            System.out.println("id: " + id + " userId: " + userId + " numFiles: " + numFiles + " filename: " + filename);
            // Add it to userFilesList 
            userFilesList.addAll(id,userId,numFiles,filename,String(fileContent));
        }
        statement.close();
        return new ResponseEntity<>(userFilesList, HttpStatus.OK);
    }
   

    @PutMapping("/{userId}/files/{fileId}")
    // Generate put request
    public ResponseEntity<Model> updateUserFileById(@PathVariable Integer userId, @PathVariable Integer fileId,
            @RequestParam("numFiles") int numFiles, @RequestParam("file") MultipartFile file) throws SQLException, IOException {
        byte[] fileContent = file.getBytes();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE user_files SET num_files = ?, filename = ?, file_content = ? WHERE user_id = ? AND num_files = ?");
        statement.setInt(1, numFiles);
        statement.setString(2, file.getOriginalFilename());
        statement.setBytes(3, fileContent);
        statement.setInt(4, userId);
        statement.setInt(5, fileId);
        int rowsUpdated = statement.executeUpdate();
        statement.close();
        if (rowsUpdated > 0) {
            Model model = new Model(userId, numFiles, file.getOriginalFilename(), fileContent);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    @DeleteMapping("/{userId}/files/{fileId}")
    // Generate delete request
    // curl -X DELETE http://localhost:8080/users/1/files/1
    public ResponseEntity<Model> deleteUserFileById(@PathVariable int userId, @PathVariable int fileId)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user_files WHERE user_id = ? AND num_files = ?");
        statement.setInt(1, userId);
        statement.setInt(2, fileId);
        int rowsDeleted = statement.executeUpdate();
        statement.close();
        if (rowsDeleted > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
