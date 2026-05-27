package com.example.annualleave.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepo extends JpaRepository<Invitation,Long> {
}
