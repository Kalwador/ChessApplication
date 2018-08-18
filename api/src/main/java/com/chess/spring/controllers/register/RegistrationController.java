package com.chess.spring.controllers;

import com.chess.spring.dto.PlayerDTO;
import com.chess.spring.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/register")
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(value = "/checkEmail/{email}")
    public void checkIsEmailUnique(@PathVariable String email){
        this.registrationService.checkIfEmailUnique(email);
    }

    @GetMapping(value = "/checkUsername/{username}")
    public void checkIsUsernameUnique(@PathVariable String username){
        this.registrationService.checkIfUsernameUnique(username);
    }

    @PostMapping
    public void registerPlayer(@RequestBody @Valid PlayerDTO playerDTO){
        this.registrationService.registerPlayer(PlayerDTO.convert(playerDTO));
    }

    /**
     * Testing body of PlayerDTO
     * @return
     */
    @GetMapping
    public PlayerDTO getTEST(){
        return new PlayerDTO(1L, "usertest","passwordtest","testemail",18);
    }
}
