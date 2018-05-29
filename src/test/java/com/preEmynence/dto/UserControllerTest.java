package com.preEmynence.dto;

import com.preEmynence.dao.Authority;
import com.preEmynence.dao.AuthorityName;
import com.preEmynence.dao.User;
import com.preEmynence.security.jwt.CustomUser;
import com.preEmynence.security.jwt.CustomUserFactory;
import com.preEmynence.security.jwt.JwtTokenUtil;
import com.preEmynence.security.service.CustomUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldGetUnauthorizedWithoutRole() throws Exception {

        mvc.perform(get("/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    public void getPersonsSuccessfullyWithUserRole() throws Exception {

        Authority authority = new Authority();
        authority.setId(1L);
        authority.setAuthorityName(AuthorityName.ROLE_ADMIN);
        List<Authority> authorities = Arrays.asList(authority);

        User user = new User();
        user.setUsername("username");
        user.setAuthorities(authorities);
        user.setEnabled(Boolean.TRUE);
        user.setLastPasswordResetDate(new Date(System.currentTimeMillis() + 1000 * 1000));

        CustomUser customUser = CustomUserFactory.create(user);

        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn(user.getUsername());

        when(customUserDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(customUser);

        mvc.perform(get("/user").header("Authorization", "Bearer AnyBearerTokenStringSentToTest"))
                .andExpect(status().is2xxSuccessful());
    }

}

