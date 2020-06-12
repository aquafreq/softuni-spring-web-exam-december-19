package exam.dec.exam.web.controller;


import exam.dec.exam.model.binding.UserLoginBindingModel;
import exam.dec.exam.model.binding.UserRegisterBindingModel;
import exam.dec.exam.model.service.UserLoginServiceModel;
import exam.dec.exam.model.service.UserRegisterServiceModel;
import exam.dec.exam.model.view.UserViewModel;
import exam.dec.exam.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
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
                           @ModelAttribute("error") String error) {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("user") UserLoginBindingModel userLoginBindingModel, HttpSession session
    ,RedirectAttributes attributes) {

        UserLoginServiceModel loginServiceModel = modelMapper.map(userLoginBindingModel, UserLoginServiceModel.class);
        Optional<UserLoginServiceModel> serviceModel = userService.logUser(loginServiceModel);
        AtomicReference<String> redirection = new AtomicReference<>();
        serviceModel
                .ifPresentOrElse(user -> {
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("userId", user.getId());
                    redirection.set("redirect:/home");
                }, () -> {
                    attributes.addFlashAttribute("error","Such user doesn't exist");
                    redirection.set("redirect:/users/login");
                });

        return redirection.get();
    }

    @GetMapping("/register")
    public String getRegister(@ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel) {
        return "register";
    }

    @PostMapping("/register")
    public RedirectView postRegister(@Valid @ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult result,
                               HttpSession session,
                               RedirectAttributes attributes,
                               RedirectView view
                               ) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("user", userRegisterBindingModel);
            view.setUrl("/users/register");
            return view;
//            return "redirect:/users/register";
        }

        UserRegisterServiceModel serviceModel = modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);
        UserRegisterServiceModel registeredUser = null;
        try {
            registeredUser = userService.registerUser(serviceModel);
        } catch (EntityExistsException e) {
            attributes.addFlashAttribute("error", "Such user already exists");
//            return "redirect:/users/register";
            view.setUrl("/users/register");
            return view;
        }

        UserViewModel userView = modelMapper.map(registeredUser, UserViewModel.class);

        session.setAttribute("username", userView.getUsername());
        attributes.addFlashAttribute("username", userView.getUsername());
//        return "redirect:/users/login";
        view.setUrl("/users/login");
        return view;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
