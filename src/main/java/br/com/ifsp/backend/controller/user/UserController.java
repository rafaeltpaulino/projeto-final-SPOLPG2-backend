package br.com.ifsp.backend.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/teste")
    public String testRouteUser() {
        return "Teste de RBAC rota user";
    }
}
