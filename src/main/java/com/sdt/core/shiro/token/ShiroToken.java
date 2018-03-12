package com.sdt.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroToken extends UsernamePasswordToken implements java.io.Serializable {
    private static final long serialVersionUID = -6451794657814516274L;
    private String pswd;

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public ShiroToken(String username, String password){
        super(username, password);
        this.pswd = password;
    }
}
