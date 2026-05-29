package com.example.annualleave.app.auth;

import com.example.annualleave.app.auth.service.RUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final RUserService rUserService;

    @GetMapping("/valid")
    public ResponseEntity<?> tokenValid(@RequestParam String token){

        rUserService.tokenValid(token);
    }
}
