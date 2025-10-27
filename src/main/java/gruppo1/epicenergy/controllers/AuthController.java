package gruppo1.epicenergy.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public String login(){
        return "POST LOGIN";
    }

    @PostMapping("/register")
    public String register(){
        return "POST REGISTER";
    }
}
