package com.example.annualleave.leave.domain;

import com.example.annualleave.auth.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
@Table(
        name = "leave_balances",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "balance_year"})
)
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "balance_year", nullable = false)
    private int year;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal grantedDays;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal usedDays;

    public LeaveBalance(User user, int year, BigDecimal grantedDays) {
        this.user = user;
        this.year = year;
        this.grantedDays = grantedDays;
        this.usedDays = BigDecimal.ZERO;
    }
}
