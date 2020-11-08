package com.users.web.controllers;

import com.users.web.models.User;
import com.users.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that controls the page with user information
 */
@Controller
public class UsersController {
    @Autowired
    private UserService userService;

    /**
     * Responsible for going to the page with user information (information about the name, password, email)
     *
     * @param model holder for model attributes
     * @return name of the template that displays the specified information from the database as a table with the
     * ability to edit records and delete them
     */
    @GetMapping("/users")
    public String userMain(Model model) {
        Iterable<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "users-main";
    }

    /**
     * Processing the action of clicking the add button
     *
     * @param model holder for model attributes
     * @return name of the template with the form for adding a new user
     */
    @GetMapping("/users/add")
    public String userAdd(Model model) {
        return "users-add";
    }

    /**
     * The action that is responsible for confirming the entered data for adding a new user. if there is no such user
     * in the database yet, it is saved with a redirect to the page with information about users, otherwise the template
     * name is returned with the corresponding error (if nothing is entered or only spaces are entered, the template
     * with the input form is re-applied)
     *
     * @param name user name entered
     * @param password password entered
     * @param email email entered
     * @param model holder for model attributes
     * @return template name with an error if the database has a user with the same name;
     * redirect to the page with user information otherwise;
     * the name of the template with the input form, if nothing is entered or only spaces
     */
    @PostMapping("/users/add")
    public String userPostAdd(@RequestParam String name, @RequestParam String password,
                              @RequestParam String email, Model model) {
        if (name.trim().isEmpty() || password.trim().isEmpty()) {
            return "redirect:/users/add";
        }
        if (userService.getUserByName(name) != null) {
            return "users-add-error";
        }
        User user = new User(name, password, email);
        userService.save(user);
        return "redirect:/users";
    }

    /**
     * Responsible for the user editing action, if the url specifies the id of a user that does not exist in the
     * database, the database is not updated
     *
     * @param id id of the user from the database whose information needs to be changed
     * @param model holder for model attributes
     * @return name of the template with information about all users, if the url has an id that doesn't exist
     * in the database; name of the template with the form for editing a specific user
     */
    @GetMapping("/users/edit/{id}")
    public String userEdit(@PathVariable(value="id") long id, Model model) {
        if (!userService.existsById(id)) {
            return "/users";
        }
        Optional<User> user = userService.getByIdAsContainerObj(id);
        List<User> result = new ArrayList<>();
        user.ifPresent(result::add);
        model.addAttribute("user", result);
        return "user-edit";
    }

    /**
     * Processing data received from the user editing form. By id the user is located in the database and a new
     * information is entered in the database. If nothing is entered or only spaces are entered, the template with
     * the input form is re-applied
     *
     * @param id id of the user from the database whose information needs to be changed
     * @param name changed  user name
     * @param password changed  password
     * @param email changed email
     * @param model holder for model attributes
     * @return name of the template with information about all dB users;
     * the name of the template with the input form, if nothing is entered or only spaces
     */
    @PostMapping("/users/edit/{id}")
    public String userPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name,
                                 @RequestParam String password, @RequestParam String email, Model model) {
        if (name.trim().isEmpty() || password.trim().isEmpty()) {
            return "redirect:/users/edit/{id}";
        }
        User user = userService.getUserById(id);
        user.setUserName(name);
        user.setPassword(password);
        user.setEmail(email);
        userService.save(user);
        return "redirect:/users";
    }

    /**
     * Responsible for the action of deleting a user on the page with user information. The user is being deleted
     * from the database
     *
     * @param id id of the user to be deleted
     * @param model holder for model attributes
     * @return name of the template with full information about all users
     */
    @PostMapping("/users/remove/{id}")
    public String userPostRemove(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        userService.remove(user);
        return "redirect:/users";
    }
}
