package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{userId}/files")
    public ResponseEntity<List<Model>> getUserFiles(@PathVariable Integer userId) {
        List<Model> files = new ArrayList<>();
        for (Model Model : userFilesList) {
            if (Model.getUserID().equals(userId)) {
                files.add(Model);
            }
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/{userId}/files/{fileId}")
    public ResponseEntity<Model> getUserFileById(@PathVariable Integer userId, @PathVariable Integer fileId) {
        for (Model Model : userFilesList) {
            if (Model.getUserID().equals(userId) && Model.getNumFiles().equals(fileId)) {
                return new ResponseEntity<>(Model, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userId}/files/{fileId}")
    public ResponseEntity<Model> updateUserFileById(@PathVariable Integer userId, @PathVariable Integer fileId,
            @RequestParam("numFiles") int numFiles, @RequestParam("filename") File filename) {
        for (Model Model : userFilesList) {
            if (Model.getUserID().equals(userId) && Model.getNumFiles().equals(fileId)) {
                Model.setNumFiles(numFiles);
                Model.setFile(filename);
                return new ResponseEntity<>(Model, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}/files/{fileId}")
    public ResponseEntity<Void> deleteUserFileById(@PathVariable Integer userId, @PathVariable Integer fileId) {
        Model userFileToRemove = null;
        for (Model Model : userFilesList) {
            if (Model.getUserID().equals(userId) && Model.getNumFiles().equals(fileId)) {
                userFileToRemove = Model;
                break;
            }
        }
        if (userFileToRemove != null) {
            userFilesList.remove(userFileToRemove);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
