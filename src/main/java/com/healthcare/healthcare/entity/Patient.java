package com.healthcare.healthcare.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", unique = true, nullable = false, length = 20)
    private String patientId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup;

    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 15)
    private String emergencyContactPhone;

    @Column(name = "emergency_contact_relation", length = 50)
    private String emergencyContactRelation;

    @Column(name = "insurance_number", length = 50)
    private String insuranceNumber;

    @Column(name = "insurance_provider", length = 100)
    private String insuranceProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "occupation", length = 100)
    private String occupation;

    @Column(name = "nationality", length = 50)
    private String nationality;

    @Column(name = "identification_type", length = 20)
    private String identificationType;

    @Column(name = "identification_number", length = 50)
    private String identificationNumber;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "last_visit_date")
    private LocalDateTime lastVisitDate;

    @Enumerated
    @Column(name = "status", nullable = false)
    private PatientStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        registrationDate = LocalDateTime.now();
        status = PatientStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Enums
    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum MaritalStatus {
        SINGLE, MARRIED, DIVORCED, WIDOWED, SEPARATED
    }

    public enum PatientStatus {
        ACTIVE, INACTIVE, DECEASED, TRANSFERRED
    }
}
