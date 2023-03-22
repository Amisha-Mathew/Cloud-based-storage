// package com.example.demo;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.io.File;
// import java.sql.SQLException;
// import java.util.List;

// @Service
// public class UserService {

//     private final Controller controller;

//     @Autowired
//     public UserService(Controller controller) {
//         this.controller = controller;
//     }

//     // public Model addUserFile(Integer userId, int numFiles, File filename) {
//     //     try {
//     //         return controller.addUserFile(userId, numFiles, filename).getBody();
//     //     } catch (SQLException e) {
//     //         // TODO Auto-generated catch block
//     //         e.printStackTrace();
//     //     }
//     // }

//     public List<Model> getUserFiles(Integer userId) {
//         return controller.getUserFiles(userId).getBody();
//     }

//     public Model getUserFileById(Integer userId, Integer fileId) {
//         return controller.getUserFileById(userId, fileId).getBody();
//     }

//     public Model updateUserFileById(Integer userId, Integer fileId, int numFiles, File filename) {
//         return controller.updateUserFileById(userId, fileId, numFiles, filename).getBody();
//     }

//     public void deleteUserFileById(Integer userId, Integer fileId) {
//         controller.deleteUserFileById(userId, fileId);
//     }
// }
