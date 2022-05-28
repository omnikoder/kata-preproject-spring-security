package root.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import root.entities.Role;
import root.entities.User;
import root.services.UserService;

@Controller
@RequestMapping(path = "/")
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping(path = "/user")
    public String getUserPage(Authentication authentication, Model model) {
        // TODO: замена на пользователя из базы данных
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user",
                new User(
                        0L,
                        authentication.getName(),
                        0,
                        userDetails.getUsername(),
                        Role.from(userDetails.getAuthorities()),
                        true));
        return "users/info";
    }
}
