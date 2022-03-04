package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSessionEvent;
import java.text.SimpleDateFormat;

@Controller
public class WebController {
    @GetMapping("/")
    public String mainPage(Model model, HttpSessionEvent hse) {
        model.addAttribute("time", new SimpleDateFormat("dd.MM").format(System.currentTimeMillis()));
        return "index.html";
    }
}
