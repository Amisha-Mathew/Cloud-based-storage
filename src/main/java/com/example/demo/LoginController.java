package com.example.demo;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/users")
public class LoginController {
    private Connection connection;

    public LoginController() throws SQLException {
        DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "ala");
    }

    @GetMapping("/login")
    public String home() {
        // Read the HTML file and return it as a string
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("/home/adarsh/ProjectNimbus/src/main/resources/templates/home.html")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
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
                    // Return home page
                    return home();
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
