package com.project.innova.layer.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/main")
    public String main(){
        return "main";
    }

}
