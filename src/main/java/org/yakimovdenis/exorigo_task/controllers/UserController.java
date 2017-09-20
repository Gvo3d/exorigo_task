package org.yakimovdenis.exorigo_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import projectpackage.model.AuthEntities.User;
import projectpackage.model.AuthEntities.UserSession;
import projectpackage.service.SecurityServices.SecurityService;
import projectpackage.service.UserService;
import projectpackage.service.UserSessionService;
import projectpackage.support.SessionTool;
import projectpackage.validators.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Gvozd on 07.01.2017.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    UserSessionService userSessionService;

    @Autowired
    LocaleResolver localeResolver;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationHandler(@ModelAttribute("userFrom") User userForm, BindingResult bindingResult, Model model, HttpServletRequest request){
        System.out.println("controller: "+userForm.toString());
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("error", bindingResult.getFieldErrors());
            return "redirect:/registration";
        }
        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getConfirmPassword());
        User newUser = userService.findByUsername(userForm.getUsername());
        UserSession newUserSession= userSessionService.createUserSession(newUser);
        SessionTool.fillSessionWithUserParameters(request.getSession(), newUserSession, true);
        return "redirect:/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout){

        if (error!=null){
            model.addAttribute("error", "Username or password incorrect");
        }

        if (logout!=null){
            model.addAttribute("message", "Logged out succesfully");
        }

        return "login";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String username, String password, HttpServletResponse response, HttpServletRequest request, Model model){

        boolean result = (boolean) securityService.autologin(username, password);

        if (!result){
            model.addAttribute("error", "Username or password incorrect");
            return "redirect:/login";
        }

        User user = userService.findByUsername(securityService.findLoggedInUsername());
        UserSession userSession = userSessionService.findByUserId(user.getId());
        userSession.setUsername(user.getUsername());
        userSession.setFullname(user.getFullname());
        SessionTool.fillSessionWithUserParameters(request.getSession(), userSession, true);
        Locale locale = new Locale.Builder().setLanguage(userSession.getLocale()).setRegion(userSession.getLocale()).build();
        localeResolver.setLocale(request,response,locale);
        return "redirect:/index";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model){
        return "admin";
    }

    @RequestMapping(value = "/useronly", method = RequestMethod.GET)
    public String useronly(HttpServletRequest request, HttpServletResponse response){
        return "index";
    }
}
