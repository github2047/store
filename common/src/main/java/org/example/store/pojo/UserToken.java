package org.example.store.pojo;

import lombok.Data;

@Data
public class UserToken {
    private String token;
    private User user;
    public void creatToken(){
        this.token="login_token:"+this.user.getAccount();
    }
}
