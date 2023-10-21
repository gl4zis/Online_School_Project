package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.school.online.dto.response.MessageResponse;

@RestController
@Hidden
public class RootController {

    //TODO: Remove on release
    @GetMapping
    @ResponseBody
    public MessageResponse getHello() {
        return new MessageResponse("Hello From Spring App!");
    }

    @GetMapping("/info")
    @ResponseBody
    public MessageResponse info(Authentication auth) {
        return new MessageResponse(auth.toString());
    }
}
