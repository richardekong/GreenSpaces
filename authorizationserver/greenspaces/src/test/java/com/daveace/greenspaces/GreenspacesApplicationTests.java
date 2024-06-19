package com.daveace.greenspaces;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.daveace.greenspaces.config.GreenSpacesCorsConfig;
import com.daveace.greenspaces.config.SecurityConfig;
import com.daveace.greenspaces.response.GreenSpacesExceptionHandler;
import com.daveace.greenspaces.client.ClientService;
import com.daveace.greenspaces.redirecturi.RedirectUriService;
import com.daveace.greenspaces.user.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class GreenspacesApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertNotNull(applicationContext.getBean(GreenSpacesApplication.class));
		assertNotNull(applicationContext.getBean(SecurityConfig.class));
		assertNotNull(applicationContext.getBean(GreenSpacesCorsConfig.class));
		assertNotNull(applicationContext.getBean(GreenSpacesExceptionHandler.class));
		assertNotNull(applicationContext.getBean(ClientService.class));
		assertNotNull(applicationContext.getBean(UserService.class));
		assertNotNull(applicationContext.getBean(RedirectUriService.class));

	}

}
