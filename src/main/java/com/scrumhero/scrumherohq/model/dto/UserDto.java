package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.type.AuthorityType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9_.-]+@[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)+")
    private String email;

    @NotNull
    private String password;

    @NotNull
    private AuthorityType authority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthorityType getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityType authority) {
        this.authority = authority;
    }


}
