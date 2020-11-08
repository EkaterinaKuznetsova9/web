package com.users.web.controllers;

import com.users.web.models.User;
import com.users.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Class responsible for controlling actions related to logging in to the site
 */
@Controller
public class SignInController {
    @Autowired
    UserService userService;

    /**
     * Triggered when you go to the page with the login form
     *
     * @param model holder for model attributes
     * @return name of template with a login form (a very simplified authorization type)
     */
    @GetMapping("/sign_in")
    public String signIn(Model model) {
        return "sign-in";
    }

    /**
     * Triggered when the user confirms the input on the login form by clicking on the button, processing the entered
     * data. If nothing is entered or only spaces are entered, the template with the input form is re-applied. If
     * there is no user with the same name and password among the users in the database, the template is returned with
     * an error. Otherwise, you will be redirected to a page with complete information about all users
     *
     * @param name user name entered
     * @param password password entered
     * @param model holder for model attributes
     * @return again, the name of the template with the login form, if nothing is entered or only spaces;
     * the name of the template with an error, if the user with the specified data is not in the database;
     * redirect to the template with information about users
     */
    @PostMapping("/sign_in")
    public String signInPost(@RequestParam String name, @RequestParam String password, Model model) {
        if (name.trim().isEmpty() || password.trim().isEmpty()) {
            return "/sign-in";
        }
        User user = userService.getUserByNameAndPassword(name, password);
        if (user == null) {
            return "sign-in-error";
        }
        return "redirect:/users";
    }
}
