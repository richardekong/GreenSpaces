package com.daveace.greenspaces.docs;


import com.daveace.greenspaces.granttypes.GrantType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Objects;

import static com.daveace.greenspaces.util.OAuth2TestUtils.ACCESS_TOKEN_QUERY_PARAMS;
import static com.daveace.greenspaces.util.OAuth2TestUtils.OAUTH2_AUTH_QUERY_PARAMS;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class OAuth2AuthorizationTest {

    public static final ParameterDescriptor[] AUTHORIZATION_CODE_QUERY_PARAMETER_DESCRIPTORS = {
            parameterWithName("response_type").description("grant type of the client"),
            parameterWithName("client_id").description("id of the client"),
            parameterWithName("scope").description("scope of user data available to the client"),
            parameterWithName("redirect_uri").description("Uri that user will be directed to after successful login"),
            parameterWithName("code_challenge").description("Verifier Code or Proof Key for Code Exchange (PKCE) security enhancement provided by the client"),
            parameterWithName("code_challenge_method").description("Algorithm for generating the PKCE e.g SHA-256")};
    public static final ParameterDescriptor[] ACCESS_TOKEN_QUERY_PARAMETERS = {parameterWithName("client_id").description("Id of the client"),
            parameterWithName("redirect_uri").description("The redirect URI registered for this client"),
            parameterWithName("grant_type").description("The grant type or oauth2.0 authentication flow"),
            parameterWithName("code").description("Authorization code from the authorization server"),
            parameterWithName("code_verifier").description("Random verifier for proofing the client is authorized to use the authorization code")};


    @Autowired
    MockMvc mockMvc;

    private record OAuth2AuthorizationCodeRequestProperties(
            String responseType,
            String clientId,
            String scope,
            String redirectUri,
            String codeChallenge,
            String codeChallengeMethod
    ) {
    }

    private record OAuth2AuthorizationAccessTokenRequestProperties(
            String clientId,
            String redirectUri,
            String grantType,
            String code,
            String codeVerifier
    ) {
    }

    private String getAuthorizationCode(ResultActions authorizationCodeRequestResultActions) {
        return fromUriString(
                Objects
                        .requireNonNull(authorizationCodeRequestResultActions
                                .andReturn()
                                .getResponse()
                                .getHeader("Location")))
                .build()
                .getQueryParams()
                .getFirst("code");
    }

    private OAuth2AuthorizationCodeRequestProperties getAuth2AuthorizationCodeRequestProperties() {
        return new OAuth2AuthorizationCodeRequestProperties(
                "code",
                "76e09d7a-1095-4b47-80c5-5f8bcba70f1d",
                "read",
                "https://localhost:1000",
                "7VwNGtsKGYcg3ZvDSqr6BcF4E-iFKkmwGpIMiXZ6xrU",
                "S256"
        );
    }

    private ResultActions performAuthorizationCodeRequestResultActions() throws Exception {
        OAuth2AuthorizationCodeRequestProperties oAuth2AuthorizationCodeRequestProperties = getAuth2AuthorizationCodeRequestProperties();
        return mockMvc
                .perform(get(format("https://localhost:1000/oauth2/authorize?" +
                                OAUTH2_AUTH_QUERY_PARAMS,
                        oAuth2AuthorizationCodeRequestProperties.responseType,
                        oAuth2AuthorizationCodeRequestProperties.clientId,
                        oAuth2AuthorizationCodeRequestProperties.scope,
                        oAuth2AuthorizationCodeRequestProperties.redirectUri,
                        oAuth2AuthorizationCodeRequestProperties.codeChallenge,
                        oAuth2AuthorizationCodeRequestProperties.codeChallengeMethod))
                );
    }


    @Test
    @DisplayName("Verify authorization server redirects user to obtain authorization code")
    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @Order(1)
    void verifyAuthorizationServerRedirectsToObtainAuthorizationCode() throws Exception {
        ResultActions authorizationCodeRequestResultActions;
        authorizationCodeRequestResultActions = performAuthorizationCodeRequestResultActions();
        authorizationCodeRequestResultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("https://localhost:1000?code=*"))
                .andDo(document("OAuth2.0 Authorization",
                        queryParameters(AUTHORIZATION_CODE_QUERY_PARAMETER_DESCRIPTORS),
                        responseHeaders(
                                headerWithName("Location").description(
                                        "The Current URI containing" +
                                                " the authorization code after a " +
                                                "successful redirect operation")
                        )
                ));
        String authorizationCode = getAuthorizationCode(authorizationCodeRequestResultActions);
        assertThat(authorizationCode).isNotEmpty();
        assertThat(authorizationCode.length()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Verify that access token can be obtained")
    @WithMockUser(username = "admin@greenspaces.com", password = "password", roles = "ADMIN")
    @Order(2)
    void verifyAccessTokenCanBeObtained() throws Exception {
        ResultActions authorizationActions = performAuthorizationCodeRequestResultActions();
        String authorizationCode = getAuthorizationCode(authorizationActions);
        OAuth2AuthorizationAccessTokenRequestProperties oAuth2AuthorizationAccessTokenRequestProperties =
                new OAuth2AuthorizationAccessTokenRequestProperties(
                        "76e09d7a-1095-4b47-80c5-5f8bcba70f1d",
                        "https://localhost:1000",
                        GrantType.GrantTypes.AUTHORIZATION_CODE.grantType(),
                        authorizationCode,
                        "uLQHUrpX3VL3cIfVT41XmHqrEp6pdYIJaO-5hgvHN0I"
                );
        mockMvc.perform(post(format("https://localhost:1000/oauth2/token?" +
                                ACCESS_TOKEN_QUERY_PARAMS,
                        oAuth2AuthorizationAccessTokenRequestProperties.clientId,
                        oAuth2AuthorizationAccessTokenRequestProperties.redirectUri,
                        oAuth2AuthorizationAccessTokenRequestProperties.grantType,
                        oAuth2AuthorizationAccessTokenRequestProperties.code,
                        oAuth2AuthorizationAccessTokenRequestProperties.codeVerifier))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(httpBasic(
                                oAuth2AuthorizationAccessTokenRequestProperties.clientId,
                                "Secret4Client01"
                        ))
                )
                .andExpect(status().isOk())
                .andDo(document("OAuth2.0 Access Token",
                        queryParameters(ACCESS_TOKEN_QUERY_PARAMETERS),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic Authentication through client id and client secret")
                        ),
                        responseFields(
                                fieldWithPath("access_token").description("Token supplied by Authorization server").type(String.class),
                                fieldWithPath("scope").description("The action the client can perform upon user's detail with the access token").type(String.class),
                                fieldWithPath("token_type").description("The type of token issued by the Authorization server e.g Bearer token"),
                                fieldWithPath("expires_in").description("Expiry time of the access token")
                        )
                        ));

    }
}
