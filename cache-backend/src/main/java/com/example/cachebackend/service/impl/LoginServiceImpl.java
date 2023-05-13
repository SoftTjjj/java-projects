package com.example.cachebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachebackend.dto.LoginFormDTO;
import com.example.cachebackend.dto.RegisterFormDTO;
import com.example.cachebackend.dto.Result;
import com.example.cachebackend.dto.UserDTO;
import com.example.cachebackend.mapper.UserMapper;
import com.example.cachebackend.pojo.User;
import com.example.cachebackend.service.ILoginService;
import com.example.cachebackend.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.cachebackend.util.Constants.*;


@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(LoginFormDTO loginFormDTO) {
        String phone=loginFormDTO.getPhone();
        String password = stringRedisTemplate.opsForValue().get(LOGIN_KEY + phone);
        if(password==null || password==""){
            return Result.fail("您的手机号不存在！");
        }
        if(!loginFormDTO.getPassword().equals(password)){
            return Result.fail("您的密码有误！");
        }
        User user = query().eq("phone", phone).one();
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        UserHolder.saveUser(userDTO);
        return Result.ok();
    }

    @Override
    public Result sendCode(String phone) {
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(REGISTER_CODE_KEY +phone,code,REGISTER_CODE_TTL, TimeUnit.MINUTES);
        return Result.ok(code);
    }

    private Boolean checkPhone(String phone){
        String phoneRegex="^1[3-9]\\d{9}$";
        Pattern compile = Pattern.compile(phoneRegex);
        Matcher matcher = compile.matcher(phone);
        return matcher.matches();
    }

    @Override
    public Result register(RegisterFormDTO registerFormDTO) {
        String phone=registerFormDTO.getPhone();
        String code=registerFormDTO.getCode();
        //1.检验手机号是否合法
        Boolean isMatch = checkPhone(phone);
        if(!isMatch){
            return Result.fail("手机号格式有误！");
        }
        //特别步骤：判断该用户是否已存在
        UserDTO testUser = UserHolder.getUser();
        if(testUser!=null && testUser.getPhone().equals(phone)){
            return Result.fail("该用户已存在，请直接登录！");
        }
        //2.根据REGISTER_CODE_KEY查询验证码
        String trueCode = stringRedisTemplate.opsForValue().get(REGISTER_CODE_KEY + phone);
        //3.查不到，说明手机号与之前发验证码的手机号不一致
        if(trueCode==null || trueCode.equals("")){
            return Result.fail("请保持手机号一致！");
        }
        //4.查得到，比对验证码是否一致
        if(!code.equals(trueCode)) {
            //5.不一致，返回验证码错误
            return Result.fail("验证码有误！");
        }
        //6.一致，注册用户
        User user = new User();
        String nickname = RandomUtil.randomString(8);
        user.setPhone(phone);
        user.setNickname("user_"+nickname);
        user.setAvatar("/imgs/icon/1.jpg");
        Date date = new Date();
        user.setCreateTime(date);
        save(user);
        //7.保存到UserHolder
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        UserHolder.saveUser(userDTO);
        //8.保存到redis中
        stringRedisTemplate.opsForValue().set(LOGIN_KEY+phone, "111111",LOGIN_KEY_TTL,TimeUnit.MINUTES);
        return Result.ok(userDTO);
    }
}
