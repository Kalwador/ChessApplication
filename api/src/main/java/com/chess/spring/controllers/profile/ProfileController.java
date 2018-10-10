package com.chess.spring.controllers.profile;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.services.account.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {

    private AccountService accountService;

    public ProfileController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Update profile avatar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful update of profile avatar")})
    @PostMapping(path = "/avatar")
    public void updateAvatar(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
    }

    @GetMapping
    public AccountDTO getProfile() throws ResourceNotFoundException {
        return this.accountService.getCurrentAccount();
    }
}
