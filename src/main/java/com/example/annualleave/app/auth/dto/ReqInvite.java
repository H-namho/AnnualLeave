package com.example.annualleave.app.auth.dto;

import com.example.annualleave.auth.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ReqInvite(
        @NotBlank @Email String email,
        @NotBlank @Size(max = 50) String name,
        @NotNull LocalDate hireDate,
        @NotBlank @Size(max = 30) String employeeNumber,
        @NotNull UserRole role,
        @NotNull Long departmentId
) {
}
