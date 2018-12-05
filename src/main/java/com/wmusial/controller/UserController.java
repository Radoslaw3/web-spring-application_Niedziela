package com.wmusial.controller;

import com.wmusial.model.User;
import com.wmusial.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String showUsers(Model model,
                            @RequestParam(required = false) String search) {
        List<User> users;

        if (search != null) {
            users = userRepository.search("%"+search+"%");
        } else {
            users = userRepository.findAll();
        }

        model.addAttribute("usersList", users);
        return "users";
    }

    @RequestMapping(value = "/users-tiles", method = RequestMethod.GET)
    public String showUsersTiles(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("usersList", users);
        return "users-tiles";
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.GET)
    public String getUserCreate() {
        return "formularz";
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String createUser(@RequestParam(name = "imie") String firstName,
                             @RequestParam(name = "nazwisko") String lastName,
                             @RequestParam String email,
                             @RequestParam(required = false) String avatarUrl) {

        User user = new User(firstName, lastName, email, avatarUrl);
        userRepository.save(user);
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.GET)
    public String getUserUpdate(@RequestParam Long id, Model model) {

        User user = userRepository.findOne(id);
        model.addAttribute("user", user);

        return "formularz-edycji";
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String updateUser(@RequestParam Long id,
                             @RequestParam(name = "imie") String firstName,
                             @RequestParam(name = "nazwisko") String lastName,
                             @RequestParam String email,
                             @RequestParam(required = false) String avatarUrl) {

        User user = new User(id, firstName, lastName, email, avatarUrl);
        userRepository.save(user);

        return "redirect:/users";
    }

    @RequestMapping(value = "/users/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam Long id) {
        userRepository.delete(id);
        return "redirect:/users";
    }
}

