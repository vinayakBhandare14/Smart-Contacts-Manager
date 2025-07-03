package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.impl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    private UserServiceImpl userServiceImpl;
    // cunstructor injection
    public PageController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @GetMapping("/")
    public String index() {
    	return "redirect:/home";
    }

    @RequestMapping("/home" )   
    public String home(Model model) {

        model.addAttribute("name", "Vinayak");
        model.addAttribute("Gmail","vinayakbhandare200 @gmail.com");
        model.addAttribute("sername","Bhandare");
        return "home";
    }

    //About routing
    @GetMapping("/about")    
    public String aboutPage() {
        System.out.println("About page loding...!");
        return "about";
    }

    //services
     @GetMapping("/services")    
    public String servicesPage() {
        System.out.println("services page loding...!");
        return "services";
    }

   //Contact page
    @GetMapping("/contact")    
    public String basePage() {
        System.out.println("contact page loding...!");
        return "contact" ;
    }
    //Login page
    @GetMapping("/login")
    public String loginPage() {
        System.out.println("Login page loding...!");
        return "login";
    }

    //Register page 
    @GetMapping("/register")
    public String registerPage( Model model){
        System.out.println("Register page loding...!");
        UserForm userForm = new UserForm();

        // Seting the default value
        // userForm.setName("Vinayak");
        // userForm.setEmail("vinayakbhandare2000@gmail.com");
        // userForm.setPassword("4455");
        // userForm.setPhoneNumber("1234567890");
        // userForm.setAbout("This is demo.....!");
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // processing page register
    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,  HttpSession session) {
        System.out.println("Processing registration...!");
        
        //fatch the data
        System.out.println(userForm);
        //validate form data
        
        if (rBindingResult.hasErrors()) {
			return "register";
		}
        
        
        //save data in database
        // UserForm => User
        
    /*    User user = User.builder()
        .name(userForm.getName())
        .email(userForm.getEmail())
        .password(userForm.getPassword())
        .phoneNumber(userForm.getPhoneNumber())
        .about(userForm.getAbout())
        .build(); */
        
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());


       User savedUser = userServiceImpl.saveUser(user);
        //message to user
//       add the massage 
      Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message",message );
        // redirect to login page
        return "redirect:/register";
    }

}
