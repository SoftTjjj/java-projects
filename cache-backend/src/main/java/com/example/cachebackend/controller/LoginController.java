package com.example.cachebackend.controller;

import com.example.cachebackend.dto.LoginFormDTO;
import com.example.cachebackend.dto.RegisterFormDTO;
import com.example.cachebackend.dto.Result;
import com.example.cachebackend.service.ILoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private ILoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginFormDTO){
        return loginService.login(loginFormDTO);
    }

    @GetMapping("/code/{phone}")
    public Result sendCode(@PathVariable("phone") String phone){
        return loginService.sendCode(phone);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterFormDTO registerFormDTO){
        return loginService.register(registerFormDTO);
    }
}
