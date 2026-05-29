package com.example.annualleave.app.auth;

import com.example.annualleave.app.auth.dto.ReqInvite;
import com.example.annualleave.app.auth.service.WAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final WAdminService wAdminService;

    @PostMapping("/invite")
    public ResponseEntity<Void> createInvitation(@Valid @RequestBody ReqInvite reqInvite){

        wAdminService.createInvitation(reqInvite);
        return ResponseEntity.ok().build();
    }

}
