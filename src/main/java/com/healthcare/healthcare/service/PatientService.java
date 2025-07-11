package com.healthcare.healthcare.service;

// import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare.entity.Patient;
import com.healthcare.healthcare.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    // Create a new patient
    public Patient createPatient(Patient patient) {
        log.info("Creating new patient: {}", patient.getFirstName() + " " + patient.getLastName());

        // Validate required fields
        // validatePatientData(patient);
        Patient savePatient = patientRepository.save(patient);
        return savePatient;
    }

    // Get all patients
    @Transactional()
    public List<Patient> getAllPatients() {
        return patientRepository.findAll(); // Returns List (empty if no records)
    }

    // Get patient by patient ID
    @Transactional()
    public Optional<Patient> getPatientById(String patiendId) {
        log.info("Fetching patient by patient ID: {}", patiendId);
        return patientRepository.findByPatientId(patiendId);
    }

    // Get patient by email
    @Transactional()
    public Optional<Patient> getPatientByEmail(String email) {
        log.info("Fetching patient by Email: {}", email);
        return patientRepository.findByEmail(email);
    }

    // Get patient by phone number
    @Transactional()
    public Optional<Patient> getPatientByPhoneNumber(String phoneNumber) {
        log.info("Fetching patient by phoneNumber: {}", phoneNumber);
        return patientRepository.findByPhoneNumber(phoneNumber);
    }

    // Get patient by identification number
    @Transactional()
    public Optional<Patient> getPatientIdentificationNumber(String identificationNumber) {
        return patientRepository.findByIdentificationNumber(identificationNumber);
    }

    // Search patients by name
    @Transactional
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByFullNameContaining(name);
    }

}
