package br.com.ifsp.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class TestController {

    @GetMapping("/seguranca")
    public String olaMundo(Principal principal) {
            return principal.getName();
    }
}
