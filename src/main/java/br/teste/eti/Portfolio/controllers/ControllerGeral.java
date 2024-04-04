package br.teste.eti.Portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
@RequestMapping ("/api")
public class ControllerGeral {

    @CrossOrigin
    @GetMapping("/menu")
    public ModelAndView menu(){
        return new ModelAndView("menu");
    }


}
