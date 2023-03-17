package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class Controller {

    private List<Model> userFilesList = new ArrayList<>();

    @PostMapping("/{userId}/files")
    public ResponseEntity<Model> addUserFile(@PathVariable Integer userId, @RequestParam("numFiles") int numFiles, @RequestParam("filename") File filename) {
        Model Model = new Model(userId, numFiles, filename);
        userFilesList.add(Model);
        return new ResponseEntity<>(Model, HttpStatus.CREATED);
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
    public ResponseEntity<Model> updateUserFileById(@PathVariable Integer userId, @PathVariable Integer fileId, @RequestParam("numFiles") int numFiles, @RequestParam("filename") File filename) {
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
