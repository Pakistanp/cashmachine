package com.ppapierz.cashmachine.Controller;

import com.ppapierz.cashmachine.Model.Card;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class AuthController {

    @GetMapping("/")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("card", new Card());
        return modelAndView;
    }

//    @GetMapping("/")
//    public ModelAndView insertCard() {
//        ModelAndView modelAndView = new ModelAndView("test");
//        return modelAndView;
//    }
//
//    @PostMapping("/")
//    public void saveCard (@RequestParam("file") MultipartFile file, HttpServletResponse response,
//             HttpServletRequest request) throws IOException {
//        request.getSession().setAttribute("file", file);
//        response.sendRedirect("/login");
//    }
}
