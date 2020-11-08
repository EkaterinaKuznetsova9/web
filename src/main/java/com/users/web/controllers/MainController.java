package com.users.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class responsible for controlling the main page (initial)
 */
@Controller
public class MainController {
    /**
     * Only returns the 'home' template
     *
     * @param model holder for model attributes
     * @return name of the home(main) page template
     */
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
}
