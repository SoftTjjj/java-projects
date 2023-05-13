package com.example.cachebackend.dto;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String phone;
    private String nickname;
    private String avatar;
}
