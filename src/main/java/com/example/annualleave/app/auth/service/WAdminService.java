package com.example.annualleave.app.auth.service;

import com.example.annualleave.app.auth.MailService;
import com.example.annualleave.app.auth.dto.ReqInvite;
import com.example.annualleave.auth.domain.Invitation;
import com.example.annualleave.auth.domain.InvitationRepo;
import com.example.annualleave.department.domain.Department;
import com.example.annualleave.department.domain.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WAdminService {

   private final DepartmentRepo departmentRepo;
   private final InvitationRepo invitationRepo;
   private final MailService mailService;

   @Transactional
   public void createInvitation(ReqInvite reqInvite) {

       Department department = departmentRepo.findById(reqInvite.departmentId())
                                        .orElseThrow(()-> new NoSuchElementException("해당 부서를 찾을 수 없습니다"));
       String token = UUID.randomUUID().toString();
       String hashToken = hash(token);

       LocalDateTime expires = LocalDateTime.now().plusHours(1);
       Invitation invitation = new Invitation(reqInvite.email(), reqInvite.name(), reqInvite.employeeNumber()
               ,reqInvite.hireDate(),reqInvite.role(),department);
       String link = "http://localhost:5173/signup?token=" + token;
       invitationRepo.save(invitation);

       TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
           @Override
           public void afterCommit() {
              mailService.sendMail(reqInvite.email(),link);
           }
       });
   }

   private String hash(String token){
       try{
           MessageDigest digest = MessageDigest.getInstance("SHA-256");
           byte[] hashed =digest.digest(token.getBytes(StandardCharsets.UTF_8));
           return HexFormat.of().formatHex(hashed);
       } catch (NoSuchAlgorithmException e) {
           throw new IllegalStateException("토큰암호화에 실패했습니다.", e);
       }
   }



}
