package com.chess.spring.profile.register;

import com.chess.spring.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {

    private RegisterService registerService;

    @Autowired
    public RegistrationController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public void registerPlayer(@RequestBody @Valid RegisterDTO registerDTO) throws InvalidDataException {
        this.registerService.createNewAccount(registerDTO);
    }
}
