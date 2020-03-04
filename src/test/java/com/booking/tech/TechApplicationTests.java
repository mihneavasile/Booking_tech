package com.booking.tech;

import com.booking.tech.controllers.TechAppController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TechApplicationTests {

    @Autowired
    private TechAppController techAppController;

	@Test
	void contextLoads() throws Exception { assertThat(techAppController).isNotNull(); }

}
