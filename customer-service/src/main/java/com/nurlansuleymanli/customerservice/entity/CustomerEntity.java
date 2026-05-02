package com.nurlansuleymanli.customerservice.entity;


import com.nurlansuleymanli.customerservice.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

    @Column(name = "pin", nullable = false)
    String pin;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "is_active")
    boolean isActive=true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;




}
