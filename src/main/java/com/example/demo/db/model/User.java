package com.example.demo.db.model;


import com.example.demo.util.ErrorMessage;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = ErrorMessage.NAME_REQUIRED)
    @Size(max = 100, message = ErrorMessage.NAME_SIZE)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = ErrorMessage.EMAIL_REQUIRED)
    @Email(message = ErrorMessage.EMAIL_INVALID)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = ErrorMessage.PASSWORD_REQUIRED)
    @Size(min = 6, message = ErrorMessage.PASSWORD_SIZE)
    @Column(nullable = false, length = 255)
    private String password;

    @Size(max = 15, message = ErrorMessage.PHONE_NUMBER_SIZE)
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String address;
}

