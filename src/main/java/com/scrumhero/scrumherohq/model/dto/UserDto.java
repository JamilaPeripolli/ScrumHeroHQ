package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.type.AuthorityType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9_.-]+@[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)+")
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private AuthorityType authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(name, userDto.name) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password) &&
                authority == userDto.authority;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, email, password, authority);
    }
}