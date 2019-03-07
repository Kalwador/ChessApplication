package com.chess.spring.profile.account;

import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.exceptions.PreconditionFailedException;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.profile.register.RegisterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/profile")
@Api(value = "Profile", description = "User profile administration")
public class ProfileController {

    private AccountService accountService;

    public ProfileController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public AccountDTO getAccountProfile() throws ResourceNotFoundException {
        return this.accountService.getCurrentDTO();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/all")
    public Page<AccountDTO> getAll(@RequestParam Pageable page) {
        return this.accountService.getAll(page);
    }

    @PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
    @GetMapping(path = "/{id}")
    public AccountDTO getProfile(@PathVariable Long id) throws ResourceNotFoundException {
        return this.accountService.getProfile(id);
    }

    @GetMapping(path = "/nick/{id}")
    public String getProfileNick(@PathVariable Long id) throws ResourceNotFoundException {
        return this.accountService.getNickName(id);
    }

    @ApiOperation(value = "Update profile public informations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profile updated successfuly"),
            @ApiResponse(code = 404, message = "Profile not found"),
    })
    @PutMapping(path = "/info")
    public void updateInfo(@RequestBody AccountDTO accountDTO) throws ResourceNotFoundException {
        this.accountService.updateInfo(accountDTO);
    }

    @ApiOperation(value = "Update profile secured data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profile updated successfuly"),
            @ApiResponse(code = 404, message = "Profile not found"),
    })
    @PutMapping(path = "/details")
    public void updateDetails(@RequestBody RegisterDTO registerDTO) throws ResourceNotFoundException {
        this.accountService.updateDetails(registerDTO);
    }

    @ApiOperation(value = "Get profile avatar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Avatar found"),
            @ApiResponse(code = 400, message = "Avatar not set"),
            @ApiResponse(code = 404, message = "Profile not found"),
    })
    @GetMapping(path = "/avatar")
    public String getAvatar() throws ResourceNotFoundException, InvalidDataException {
        return accountService.getAvatar();
    }

    @ApiOperation(value = "Update profile avatar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful update of profile avatar"),
            @ApiResponse(code = 400, message = "Error, Image could not be processed"),
            @ApiResponse(code = 404, message = "Profile not found"),
            @ApiResponse(code = 412, message = "Image size is to big"),
    })
    @PutMapping(path = "/avatar")
    public void updateAvatar(@RequestParam("file") MultipartFile file) throws ResourceNotFoundException, InvalidDataException, PreconditionFailedException {
        accountService.updateAvatar(file);
    }
}
