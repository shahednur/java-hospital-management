package com.healthcare.healthcare.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.healthcare.healthcare.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Find by unique patient ID
    Optional<Patient> findByPatientId(String patientId);

    // Check if patient Id exists
    boolean existsByPatientId(String patientId);

    // Find by email
    Optional<Patient> findByEmail(String email);

    // Find by phone number
    Optional<Patient> findByPhoneNumber(String phoneNumber);

    // Find by identification number
    Optional<Patient> findByIdentificationNumber(String identificationNumber);

    // Find patients by name (case-insensitive)
    List<Patient> findAll();

    // Find patients by name (case-insensitive)
    @Query("SELECT p FROM Patient p WHERE " +
            "LOWER(CONCAT(p.firstName, ' ', p.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByFullNameContaining(@Param("name") String name);

    // Find patients by gender
    List<Patient> findByGender(Patient.Gender gender);

    // Find patients by blood group
    List<Patient> findByBloodGroup(String bloodGroup);

    // Find patients by city
    List<Patient> findByCity(String city);

    // Custom query to find patients by age range
    @Query("SELECT p FROM Patient p WHERE YEAR(CURRENT_DATE) - YEAR(p.dateOfBirth) BETWEEN :minAge AND :maxAge")
    List<Patient> findPatientsByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

    // Custom query to find active patients only
    // @Query("SELECT p FROM Patient p WHERE p.status = 'ACTIVE'")
    // List<Patient> findActivePatients();

    // Custom query to find patients without insurance
    // @Query("SELECT p FROM Patient p WHERE p.insuranceNumber IS NULL OR
    // p.insuranceProvider IS NULL")
    // List<Patient> findPatientsWithoutInsurance();

    // Custom query to find patients with upcoming birthdays (next 30 days)
    // @Query("SELECT p FROM Patient p WHERE"
    // + "DAYOFYEAR(p.dateOfBirth) BETWEEN DAYOFYEAR(CURRENT_DATE) AND
    // DAYOFYEAR(CURRENT_DATE) + 30")
    // List<Patient> findPatientWithUpcomingBirthdays();

    // Custom query to find patients registered today
    // @Query("SELECT p FROM Patient p WHERE DATE(p.registrationDate) =
    // CURRENT_DATE")
    // List<Patient> findPatientsRegisteredToday();

    // Custom query to find patients registered in current month
    // @Query("SELECT p FROM Patient p WHERE MONTH(p.registrationDate) =
    // MONTH(CURRENT_DATE) "
    // + "AND YEAR(p.registrationDate) = YEAR(CURRENT_DATE)")
    // List<Patient> findPatientsRegisteredThisMonth();
}
