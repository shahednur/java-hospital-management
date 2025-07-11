package com.healthcare.healthcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.healthcare.entity.Patient;
import com.healthcare.healthcare.service.PatientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.util.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    // Create a new patient
    @PostMapping
    public ResponseEntity<ApiResponse<Patient>> createPatient(@Valid @RequestBody Patient patient) {
        log.info("REST request to create patient: {}", patient.getFirstName() + " " + patient.getLastName());

        try {
            Patient createdPatient = patientService.createPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<Patient>builder()
                            .success(true)
                            .message("Patient created successfully")
                            .data(createdPatient)
                            .build());
        } catch (Exception e) {
            log.error("Error creating patient: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Patient>builder()
                            .success(false)
                            .message("Failed to create patient: " + e.getMessage())
                            .build());
        }
    }

    // Get all patients
    @GetMapping
    public ResponseEntity<ApiResponse<List<Patient>>> getAllPatients() {
        log.info("REST request to get all patients");

        try {
            List<Patient> patients = patientService.getAllPatients();
            return ResponseEntity.ok(ApiResponse.<List<Patient>>builder()
                    .success(true)
                    .message("Patients retrieved successfully")
                    .data(patients)
                    .count(patients.size())
                    .build());
        } catch (Exception e) {
            log.error("Error retrieving patients: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<Patient>>builder()
                            .success(false)
                            .message("Failed to retrieve patients: " + e.getMessage())
                            .build());
        }
    }

    // Get patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Patient>> getPatientById(@PathVariable @Min(1) String id) {
        log.info("REST request to get patiend by ID: {}", id);

        try {
            Optional<Patient> patient = patientService.getPatientById(id);
            if (patient.isPresent()) {
                return ResponseEntity.ok(ApiResponse.<Patient>builder()
                        .success(true)
                        .message("Patient retrieved successfully")
                        .data(patient.get())
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Patient>builder()
                                .success(false)
                                .message("Patiend not found with ID: " + id)
                                .build());
            }
        } catch (Exception e) {
            log.error("Patiend not found with ID: ", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Patient>builder()
                            .success(false)
                            .message("Failed to retrieve patient: " + e.getMessage())
                            .build());
        }
    }

    // Get patient by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<Patient>> getPatientByEmail(@PathVariable @Min(1) String email) {
        log.info("REST request to get patient by Email: {}", email);

        try {
            Optional<Patient> patient = patientService.getPatientByEmail(email);
            if (patient.isPresent()) {
                return ResponseEntity.ok(ApiResponse.<Patient>builder()
                        .success(true)
                        .message("Patient retrieved successfully")
                        .data(patient.get())
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Patient>builder()
                                .success(false)
                                .message("Patient not found with ID: " + email)
                                .build());
            }
        } catch (Exception e) {
            log.error("Error retrieving patient by ID{}: {}", email, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Patient>builder()
                            .success(false)
                            .message("Failed to retrieve patient: " + e.getMessage())
                            .build());
        }
    }

    // Get patient by phoneNumber
    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<ApiResponse<Patient>> getPatientByPhoneNumber(@PathVariable @Min(1) String phoneNumber) {
        log.info("REST request to get patient by phoneNumber: {}", phoneNumber);

        try {
            Optional<Patient> patient = patientService.getPatientByPhoneNumber(phoneNumber);
            if (patient.isPresent()) {
                return ResponseEntity.ok(ApiResponse.<Patient>builder()
                        .success(true)
                        .message("Patient retrieved successfully")
                        .data(patient.get())
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Patient>builder()
                                .success(false)
                                .message("Patient not found with phoneNumber: " + phoneNumber)
                                .build());
            }
        } catch (Exception e) {
            log.error("Failed to retrieve patient: ", phoneNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Patient>builder()
                            .success(false)
                            .message("Failed to retrieve patient: " + e.getMessage())
                            .build());
        }
    }

    // Get patient by identification number
    @GetMapping("/identificationNumber/{identificationNumber}")
    public ResponseEntity<ApiResponse<Patient>> getPatientByIdentificationNumber(
            @PathVariable @Min(1) String identificationNumber) {
        log.info("REST request to get patient by identificationNumber: {}", identificationNumber);

        try {
            Optional<Patient> patient = patientService.getPatientIdentificationNumber(identificationNumber);
            if (patient.isPresent()) {
                return ResponseEntity.ok(ApiResponse.<Patient>builder()
                        .success(true)
                        .message("Patient retrieved successfully")
                        .data(patient.get())
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Patient>builder()
                                .success(false)
                                .message("Patient not found with identificationNumber: " + identificationNumber)
                                .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Patient>builder()
                            .success(false)
                            .message("Failed to retrieved patient: " + e.getMessage())
                            .build());
        }
    }

    // Search patients by name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Patient>>> searchPatientsByName(@RequestParam @NotBlank String name) {
        log.info("REST request to search patients by name: {}", name);

        try {
            List<Patient> patients = patientService.searchPatientsByName(name);
            return ResponseEntity.ok(ApiResponse.<List<Patient>>builder()
                    .success(true)
                    .message("Patients search completed successfully")
                    .data(patients)
                    .count(patients.size())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<Patient>>builder()
                            .success(false)
                            .message("Failed to search patients: " + e.getMessage())
                            .build());
        }
    }

    // Inner class for API response wrapper
    @lombok.Data
    @lombok.Builder
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private Integer count;
        private String timestamp;

        public ApiResponse(boolean success, String message, T data, Integer count, String timestamp) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.count = count;
            this.timestamp = timestamp != null ? timestamp : java.time.LocalDateTime.now().toString();
        }
    }
}
