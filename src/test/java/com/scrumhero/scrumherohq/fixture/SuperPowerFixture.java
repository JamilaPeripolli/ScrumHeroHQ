package com.scrumhero.scrumherohq.fixture;

import com.scrumhero.scrumherohq.model.dto.SuperPowerDto;
import com.scrumhero.scrumherohq.model.entity.SuperPower;

public class SuperPowerFixture {

    public static SuperPower create() {
        SuperPower superPower = new SuperPower();
        superPower.setId(1L);
        superPower.setName("Java");
        superPower.setDescription("The ability to write functional code in Java");

        return superPower;
    }

    public static SuperPowerDto createDto() {
        SuperPowerDto superPower = new SuperPowerDto();
        superPower.setId(1L);
        superPower.setName("Java");
        superPower.setDescription("The ability to write functional code in Java");

        return superPower;
    }
}
