package org.binar.kamihikoukiairlines.response;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<String> roles;
    private String token;



    public UserInfoResponse( Long id, String name, String email, String phoneNumber, List<String> roles, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.token = token;
    }
}
