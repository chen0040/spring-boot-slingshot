package com.github.chen0040.bootslingshot.controllers;

import com.github.chen0040.bootslingshot.components.SpringAuthentication;
import com.github.chen0040.bootslingshot.components.SpringRequestHelper;
import com.github.chen0040.bootslingshot.models.LoginObj;
import com.github.chen0040.bootslingshot.models.SpringIdentity;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebApiController {

    @Autowired
    private SpringAuthentication springAuthentication;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SpringRequestHelper requestHelper;

    private static final Logger logger = LoggerFactory.getLogger(WebApiController.class);

    @RequestMapping(value = "login-web-api", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    SpringIdentity login(
            @RequestBody LoginObj loginObj,
            HttpServletRequest request) {
        final String username = loginObj.getUsername();
        final String password = loginObj.getPassword();

        if(springAuthentication.isLoggedIn()) {
            if(username.equals(springAuthentication.getUsername())){
                SpringIdentity identity = new SpringIdentity(username, true);
                identity.getTokenInfo().putAll(requestHelper.getTokenInfo(request));
                return identity;
            }
        }


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if does not exists
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(token);

        if(authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SpringIdentity identity = new SpringIdentity(username, true);



            //identity.getTokenInfo().putAll(requestHelper.getTokenInfo(request));

            return identity;
        } else {
            return new SpringIdentity(username, false);
        }
    }

}
