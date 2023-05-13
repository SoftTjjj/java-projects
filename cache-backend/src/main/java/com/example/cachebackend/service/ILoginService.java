package com.example.cachebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachebackend.dto.LoginFormDTO;
import com.example.cachebackend.dto.RegisterFormDTO;
import com.example.cachebackend.dto.Result;
import com.example.cachebackend.pojo.User;

public interface ILoginService extends IService<User>{

    Result login(LoginFormDTO loginFormDTO);

    Result sendCode(String phone);

    Result register(RegisterFormDTO registerFormDTO);

}
