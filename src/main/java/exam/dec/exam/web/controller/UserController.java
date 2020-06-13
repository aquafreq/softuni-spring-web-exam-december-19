package exam.dec.exam.web.controller;


import exam.dec.exam.model.binding.UserLoginBindingModel;
import exam.dec.exam.model.binding.UserRegisterBindingModel;
import exam.dec.exam.model.service.LoggedUserModel;
import exam.dec.exam.model.service.UserLoginServiceModel;
import exam.dec.exam.model.service.UserRegisterServiceModel;
import exam.dec.exam.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityExistsException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("user") UserLoginBindingModel userLoginBindingModel,
                           @ModelAttribute("name") String name, @ModelAttribute("error") String error) {
        if (name != null) userLoginBindingModel.setUsername(name);
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("user") UserLoginBindingModel userLoginBindingModel, HttpSession session
            , RedirectAttributes attributes, HttpServletResponse response, HttpServletRequest request) {

        UserLoginServiceModel loginServiceModel = modelMapper.map(userLoginBindingModel, UserLoginServiceModel.class);
        Optional<LoggedUserModel> serviceModel = userService.logUser(loginServiceModel);
        AtomicReference<String> redirection = new AtomicReference<>();
        serviceModel
                .ifPresentOrElse(user -> {
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("userId", user.getId());
                    Cookie userCookie = createCookie("username", user.getUsername());
                    response.addCookie(userCookie);
                    redirection.set("redirect:/home");
                }, () -> {
                    attributes.addFlashAttribute("error", "Such user doesn't exist");
                    redirection.set("redirect:/users/login");
                });
        return redirection.get();
    }

    private Cookie createCookie(String key, String value) {
        Cookie userCookie = new Cookie(key, value);
        userCookie.setPath("/");
        userCookie.setMaxAge(-1);
        userCookie.setComment("holds user info");
        return userCookie;
    }

    @GetMapping("/register")
    public String getRegister(@ModelAttribute(value = "user") UserRegisterBindingModel userRegisterBindingModel,
                              @ModelAttribute ArrayList<Object> userErrors
    ) {
//        userErrors = new ArrayList<>((Collection<?>) Objects.requireNonNull(model.getAttribute("userErrors")));
//        if (model.getAttribute("userErrors") == null) {
//            model.addAttribute("userErrors", new ArrayList<>());
//        }

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("user", userRegisterBindingModel);
            attributes.addFlashAttribute(result.getFieldErrors());
//            return "redirect:/users/register";
            return "/register";
        }

        UserRegisterServiceModel serviceModel = modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);
        UserRegisterServiceModel registeredUser;

        try {
            registeredUser = userService.registerUser(serviceModel);
            attributes.addFlashAttribute("name", registeredUser.getUsername());
        } catch (EntityExistsException e) {
            attributes.addFlashAttribute("error", "Such user already exists");
            return "redirect:/users/register";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
