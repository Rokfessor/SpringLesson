package com.example.demo.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;

@org.springframework.stereotype.Controller
public class WebController {
    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("time", new SimpleDateFormat("dd.MM").format(System.currentTimeMillis()));
        return "index.html";
    }
}
