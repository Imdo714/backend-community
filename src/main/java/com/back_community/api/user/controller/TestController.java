package com.back_community.api.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping
    public String docs(){
        return "/docs/index";
    }

}
