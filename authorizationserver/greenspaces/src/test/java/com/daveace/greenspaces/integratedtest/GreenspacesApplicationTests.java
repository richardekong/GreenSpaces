package com.daveace.greenspaces.integratedtest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.daveace.greenspaces.GreenSpacesApplication;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethodRepo;
import com.daveace.greenspaces.authenticationmethod.AuthenticationMethodService;
import com.daveace.greenspaces.authority.AuthorityRepo;
import com.daveace.greenspaces.authority.AuthorityService;
import com.daveace.greenspaces.client.ClientController;
import com.daveace.greenspaces.client.ClientRepo;
import com.daveace.greenspaces.component.GreenSpacesAuthenticationProvider;
import com.daveace.greenspaces.csrf.CSRFTokenRepo;
import com.daveace.greenspaces.csrf.CSRFTokenService;
import com.daveace.greenspaces.granttypes.GrantTypeRepo;
import com.daveace.greenspaces.granttypes.GrantTypeService;
import com.daveace.greenspaces.redirecturi.RedirectUriRepo;
import com.daveace.greenspaces.role.RoleRepo;
import com.daveace.greenspaces.role.RoleService;
import com.daveace.greenspaces.scope.ScopeRepo;
import com.daveace.greenspaces.scope.ScopeService;
import com.daveace.greenspaces.tokensettings.TokenSettings;
import com.daveace.greenspaces.tokensettings.TokenSettingsRepo;
import com.daveace.greenspaces.tokensettings.TokenSettingsService;
import com.daveace.greenspaces.user.UserController;
import com.daveace.greenspaces.user.UserRepo;
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
		assertNotNull(applicationContext.getBean(GreenSpacesAuthenticationProvider.class));
		assertNotNull(applicationContext.getBean(GreenSpacesExceptionHandler.class));
		assertNotNull(applicationContext.getBean(ClientService.class));
		assertNotNull(applicationContext.getBean(ClientRepo.class));
		assertNotNull(applicationContext.getBean(ClientController.class));
		assertNotNull(applicationContext.getBean(UserService.class));
		assertNotNull(applicationContext.getBean(UserRepo.class));
		assertNotNull(applicationContext.getBean(UserController.class));
		assertNotNull(applicationContext.getBean(RoleService.class));
		assertNotNull(applicationContext.getBean(RoleRepo.class));
		assertNotNull(applicationContext.getBean(AuthorityService.class));
		assertNotNull(applicationContext.getBean(AuthorityRepo.class));
		assertNotNull(applicationContext.getBean(AuthenticationMethodService.class));
		assertNotNull(applicationContext.getBean(AuthenticationMethodRepo.class));
		assertNotNull(applicationContext.getBean(RedirectUriService.class));
		assertNotNull(applicationContext.getBean(RedirectUriRepo.class));
		assertNotNull(applicationContext.getBean(ScopeService.class));
		assertNotNull(applicationContext.getBean(ScopeRepo.class));
		assertNotNull(applicationContext.getBean(GrantTypeService.class));
		assertNotNull(applicationContext.getBean(GrantTypeRepo.class));
		assertNotNull(applicationContext.getBean(TokenSettingsService.class));
		assertNotNull(applicationContext.getBean(TokenSettingsRepo.class));
		assertNotNull(applicationContext.getBean(CSRFTokenService.class));
		assertNotNull(applicationContext.getBean(CSRFTokenRepo.class));
	}

}
