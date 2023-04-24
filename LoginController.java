package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

@RestController
@RequestMapping("/users")
public class LoginController {
    private Connection connection;

    public LoginController() throws SQLException {
        DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "ala");

    }

    @PostMapping("/login")
    public String loginPost(@RequestParam Integer userId, @RequestParam Integer password) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "ala");
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM login WHERE userId = ? AND `password` = ?");) {
            statement.setInt(1, userId);
            statement.setInt(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return "Login successful";
                } else {
                    return "Login failed";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Login failed";
        }

    }
}