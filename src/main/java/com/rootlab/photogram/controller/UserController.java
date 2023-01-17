package com.rootlab.photogram.controller;

import com.rootlab.photogram.config.auth.PrincipalUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/{id}")
    public String story(@PathVariable Long id) {
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable Long id,
                         @AuthenticationPrincipal PrincipalUserDetails userDetails) {
//        log.info("[UserController] @AuthenticationPrincipal: " + userDetails); // PrincipalUserDetails
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("[UserController] authentication.getPrincipal: " + authentication.getPrincipal()); // PrincipalUserDetails
//        log.info("[UserController] authentication.getDetails(): " + authentication.getDetails()); // WebAuthenticationDetails

        return "user/update";
    }

}