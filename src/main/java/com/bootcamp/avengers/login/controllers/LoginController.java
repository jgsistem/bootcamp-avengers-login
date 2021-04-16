package com.bootcamp.avengers.login.controllers;

import com.bootcamp.avengers.login.business.LoginService;
import com.bootcamp.avengers.login.models.entity.LoginResponse;
import com.bootcamp.avengers.login.models.entity.ReniecResponse;
import com.bootcamp.avengers.login.models.entity.User;
import com.bootcamp.avengers.login.models.service.IReniecService;
import com.bootcamp.avengers.login.models.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private IReniecService reniecService;

    @Autowired
    private IUserService userService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping(value = "/bootcamp/v1/login/user/reniec/{dni}",
            produces = {MediaType.APPLICATION_JSON_VALUE}  )
    public ResponseEntity<ReniecResponse> buscaUsaurioRenic(@PathVariable String dni) {
        ReniecResponse reniecResponse = reniecService.searchReniec(dni);
        return ResponseEntity.ok(reniecResponse);
    }

    @GetMapping(value = "/bootcamp/v1/login/user/db/{dni}",
            produces = {MediaType.APPLICATION_JSON_VALUE}  )
    public ResponseEntity<User> buscaUsaurioDB(@PathVariable String dni) {
        User user = userService.searchBD(dni);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/bootcamp/v1/login/user/save/{dni}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void grabarUsuarioDB(@PathVariable String dni,
                                @RequestBody ReniecResponse reniecResponse) {
        userService.save(dni, reniecResponse);
    }

    @PostMapping(value = "/bootcamp/v1/login/user/{dni}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoginResponse> login(@PathVariable String dni) {
        LoginResponse loginResponse = new LoginService().loginUsaurio(dni);
        if (loginResponse.getDni() != "") {
            return ResponseEntity.ok(loginResponse);
        } else  {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginResponse);
        }


    }

}
