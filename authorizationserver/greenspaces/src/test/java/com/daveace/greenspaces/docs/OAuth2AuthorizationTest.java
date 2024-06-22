package com.daveace.greenspaces.docs;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.operation.QueryParameters;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class OAuth2AuthorizationTest {

    public static final String OAUTH2_AUTH_QUERY_PARAMS =
            "response_type=%s&" +
            "client_id=%s&" +
            "scope=%s&" +
            "redirect_uri=%s&" +
            "code_challenge=%s&" +
            "code_challenge_method=%s";

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Verify authorization server redirects user to obtain authorization code")
    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    void verifyAuthorizationServerRedirectsToObtainAuthorizationCode() throws Exception{

        mockMvc.perform(get(format("https://localhost:1000/oauth2/authorize?" + OAUTH2_AUTH_QUERY_PARAMS,
                        "code",
                        "76e09d7a-1095-4b47-80c5-5f8bcba70f1d",
                        "read",
                        "https://localhost:1000",
                        "BUJilj4kra3vxk6TPzZH_yNEkBZVB9yfAvrvlpmDM3E",
                        "S256")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("https://localhost:1000?code=*"))
                .andDo(document("OAuth2.0 Authorization",
                        queryParameters(
                            parameterWithName("response_type").description("grant type of the client"),
                            parameterWithName("client_id").description("id of the client"),
                            parameterWithName("scope").description("scope of user data available to the client"),
                            parameterWithName("redirect_uri").description("Uri that user will be directed to after successful login"),
                            parameterWithName("code_challenge").description("Verifier Code or Proof Key for Code Exchange (PKCE) security enhancement provided by the client"),
                            parameterWithName("code_challenge_method").description("Algorithm for generating the PKCE e.g SHA-256")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("The Current URI containing the authorization code after a successful redirect operation")
                        )
                  ));
    }
}
