package com.scrumhero.scrumherohq.fixture;

import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.model.type.AuthorityType;

public class UserFixture {

    public static User create() {
        return create(AuthorityType.USER);
    }

    public static User create(AuthorityType authority) {
        User user = new User();
        user.setId(1L);
        user.setName("player");
        user.setEmail("player@mail.com");
        user.setPassword("1234");
        user.setAuthority(authority);

        return user;
    }

    public static UserDto createDto() {
        return createDto(AuthorityType.USER);
    }

    public static UserDto createDto(AuthorityType authority) {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("player");
        user.setEmail("player@mail.com");
        user.setPassword("1234");
        user.setAuthority(authority);

        return user;
    }

}
