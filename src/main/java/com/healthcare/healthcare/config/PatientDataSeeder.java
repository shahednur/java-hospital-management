package com.healthcare.healthcare.config;

import com.healthcare.healthcare.entity.Patient;
import com.healthcare.healthcare.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientDataSeeder implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final Random random = new Random();

    // Sample data arrays
    private final String[] firstNames = {
            "John", "Jane", "Michael", "Sarah", "David", "Emily", "Robert", "Jessica", "William", "Ashley",
            "James", "Amanda", "Daniel", "Jennifer", "Christopher", "Lisa", "Matthew", "Michelle", "Anthony",
            "Kimberly",
            "Mark", "Amy", "Donald", "Angela", "Steven", "Helen", "Paul", "Anna", "Andrew", "Brenda",
            "Kenneth", "Emma", "Kevin", "Olivia", "Brian", "Cynthia", "George", "Marie", "Edward", "Janet",
            "Ronald", "Catherine", "Timothy", "Frances", "Jason", "Christine", "Jeffrey", "Samantha", "Ryan", "Deborah",
            "Jacob", "Rachel", "Gary", "Carolyn", "Nicholas", "Susan", "Eric", "Virginia", "Jonathan", "Maria",
            "Stephen", "Heather", "Larry", "Diane", "Justin", "Ruth", "Scott", "Julie", "Brandon", "Joyce",
            "Benjamin", "Victoria", "Samuel", "Kelly", "Gregory", "Christina", "Alexander", "Joan", "Patrick", "Evelyn",
            "Frank", "Lauren", "Raymond", "Judith", "Jack", "Megan", "Dennis", "Cheryl", "Jerry", "Andrea",
            "Tyler", "Hannah", "Aaron", "Jacqueline", "Jose", "Martha", "Henry", "Gloria", "Adam", "Teresa"
    };

    private final String[] lastNames = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
            "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
            "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
            "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts",
            "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes",
            "Stewart", "Morris", "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan", "Cooper",
            "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward", "Richardson",
            "Watson", "Brooks", "Chavez", "Wood", "James", "Bennett", "Gray", "Mendoza", "Ruiz", "Hughes",
            "Price", "Alvarez", "Castillo", "Sanders", "Patel", "Myers", "Long", "Ross", "Foster", "Jimenez"
    };

    private final String[] cities = {
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego",
            "Dallas", "San Jose", "Austin", "Jacksonville", "Fort Worth", "Columbus", "Indianapolis", "Charlotte",
            "San Francisco", "Seattle", "Denver", "Washington", "Boston", "El Paso", "Detroit", "Nashville",
            "Portland", "Memphis", "Oklahoma City", "Las Vegas", "Louisville", "Baltimore", "Milwaukee", "Albuquerque",
            "Tucson", "Fresno", "Sacramento", "Kansas City", "Long Beach", "Mesa", "Atlanta", "Colorado Springs",
            "Virginia Beach", "Raleigh", "Omaha", "Miami", "Oakland", "Minneapolis", "Tulsa", "Wichita", "New Orleans"
    };

    private final String[] states = {
            "California", "Texas", "Florida", "New York", "Pennsylvania", "Illinois", "Ohio", "Georgia",
            "North Carolina", "Michigan", "New Jersey", "Virginia", "Washington", "Arizona", "Massachusetts",
            "Tennessee", "Indiana", "Missouri", "Maryland", "Wisconsin", "Colorado", "Minnesota", "South Carolina",
            "Alabama", "Louisiana", "Kentucky", "Oregon", "Oklahoma", "Connecticut", "Utah", "Iowa", "Nevada",
            "Arkansas", "Mississippi", "Kansas", "New Mexico", "Nebraska", "West Virginia", "Idaho", "Hawaii",
            "New Hampshire", "Maine", "Montana", "Rhode Island", "Delaware", "South Dakota", "North Dakota",
            "Alaska", "Vermont", "Wyoming"
    };

    private final String[] bloodGroups = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };

    private final String[] countries = { "United States", "Canada", "United Kingdom", "Australia", "Germany", "France",
            "Spain", "Italy" };

    private final String[] occupations = {
            "Software Engineer", "Teacher", "Doctor", "Nurse", "Lawyer", "Accountant", "Manager",
            "Sales Representative",
            "Marketing Specialist", "Chef", "Electrician", "Plumber", "Mechanic", "Artist", "Writer", "Consultant",
            "Architect", "Designer", "Photographer", "Analyst", "Technician", "Administrator", "Coordinator",
            "Supervisor",
            "Director", "Executive", "Entrepreneur", "Student", "Retired", "Unemployed"
    };

    private final String[] insuranceProviders = {
            "Blue Cross Blue Shield", "Anthem", "UnitedHealth", "Aetna", "Cigna", "Humana", "Kaiser Permanente",
            "Molina Healthcare", "Centene", "WellCare", "Medicaid", "Medicare", "Tricare"
    };

    private final String[] emergencyRelations = {
            "Spouse", "Parent", "Child", "Sibling", "Friend", "Partner", "Grandparent", "Cousin", "Uncle", "Aunt"
    };

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (patientRepository.count() > 0) {
            log.info("Patient data already exists. Skipping seeding.");
            return;
        }

        log.info("Starting patient data seeding...");

        List<Patient> patients = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            Patient patient = createRandomPatient(i);
            patients.add(patient);
        }

        // Batch save all patients
        List<Patient> savedPatients = patientRepository.saveAll(patients);

        log.info("Successfully seeded {} patient records", savedPatients.size());
    }

    private Patient createRandomPatient(int index) {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];

        return Patient.builder()
                .patientId(generatePatientId(index))
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(generateRandomDateOfBirth())
                .gender(getRandomGender())
                .phoneNumber(generateRandomPhoneNumber())
                .email(generateEmail(firstName, lastName))
                .address(generateRandomAddress())
                .city(cities[random.nextInt(cities.length)])
                .state(states[random.nextInt(states.length)])
                .zipCode(generateRandomZipCode())
                .country(countries[random.nextInt(countries.length)])
                .bloodGroup(bloodGroups[random.nextInt(bloodGroups.length)])
                .emergencyContactName(generateEmergencyContactName())
                .emergencyContactPhone(generateRandomPhoneNumber())
                .emergencyContactRelation(emergencyRelations[random.nextInt(emergencyRelations.length)])
                .insuranceNumber(generateInsuranceNumber())
                .insuranceProvider(insuranceProviders[random.nextInt(insuranceProviders.length)])
                .maritalStatus(getRandomMaritalStatus())
                .occupation(occupations[random.nextInt(occupations.length)])
                .nationality("American")
                .identificationType(random.nextBoolean() ? "SSN" : "Driver's License")
                .identificationNumber(generateIdentificationNumber())
                .registrationDate(generateRandomRegistrationDate())
                .lastVisitDate(generateRandomLastVisitDate())
                .status(Patient.PatientStatus.ACTIVE)
                .build();
    }

    private String generatePatientId(int index) {
        return String.format("P%08d", index);
    }

    private LocalDate generateRandomDateOfBirth() {
        // Generate age between 18 and 80 years
        int minAge = 18;
        int maxAge = 80;
        LocalDate now = LocalDate.now();
        LocalDate minDate = now.minusYears(maxAge);
        LocalDate maxDate = now.minusYears(minAge);

        long minDay = minDate.toEpochDay();
        long maxDay = maxDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    private Patient.Gender getRandomGender() {
        Patient.Gender[] genders = Patient.Gender.values();
        return genders[random.nextInt(genders.length)];
    }

    private String generateRandomPhoneNumber() {
        return String.format("(%03d) %03d-%04d",
                random.nextInt(900) + 100,
                random.nextInt(900) + 100,
                random.nextInt(9000) + 1000);
    }

    private String generateEmail(String firstName, String lastName) {
        String[] domains = { "gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "email.com" };
        String domain = domains[random.nextInt(domains.length)];
        return String.format("%s.%s%d@%s",
                firstName.toLowerCase(),
                lastName.toLowerCase(),
                random.nextInt(999) + 1,
                domain);
    }

    private String generateRandomAddress() {
        int streetNumber = random.nextInt(9999) + 1;
        String[] streetTypes = { "St", "Ave", "Rd", "Dr", "Blvd", "Ln", "Way", "Ct" };
        String[] streetNames = {
                "Main", "Oak", "Pine", "Maple", "Cedar", "Elm", "Washington", "Park", "Hill", "Church",
                "School", "State", "Broad", "High", "Union", "Water", "Mill", "River", "Lake", "Forest"
        };

        String streetName = streetNames[random.nextInt(streetNames.length)];
        String streetType = streetTypes[random.nextInt(streetTypes.length)];

        return String.format("%d %s %s", streetNumber, streetName, streetType);
    }

    private String generateRandomZipCode() {
        return String.format("%05d", random.nextInt(99999));
    }

    private String generateEmergencyContactName() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        return firstName + " " + lastName;
    }

    private String generateInsuranceNumber() {
        return String.format("INS%09d", random.nextInt(999999999));
    }

    private Patient.MaritalStatus getRandomMaritalStatus() {
        Patient.MaritalStatus[] statuses = Patient.MaritalStatus.values();
        return statuses[random.nextInt(statuses.length)];
    }

    private String generateIdentificationNumber() {
        if (random.nextBoolean()) {
            // SSN format: XXX-XX-XXXX
            return String.format("%03d-%02d-%04d",
                    random.nextInt(900) + 100,
                    random.nextInt(99) + 1,
                    random.nextInt(9999) + 1);
        } else {
            // Driver's License format: DXXXXXXXX
            return String.format("D%08d", random.nextInt(99999999));
        }
    }

    private LocalDateTime generateRandomRegistrationDate() {
        // Generate registration date within last 2 years
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoYearsAgo = now.minusYears(2);

        long minEpoch = twoYearsAgo.toEpochSecond(java.time.ZoneOffset.UTC);
        long maxEpoch = now.toEpochSecond(java.time.ZoneOffset.UTC);
        long randomEpoch = ThreadLocalRandom.current().nextLong(minEpoch, maxEpoch);

        return LocalDateTime.ofEpochSecond(randomEpoch, 0, java.time.ZoneOffset.UTC);
    }

    private LocalDateTime generateRandomLastVisitDate() {
        // 70% chance of having a last visit date
        if (random.nextDouble() < 0.7) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime sixMonthsAgo = now.minusMonths(6);

            long minEpoch = sixMonthsAgo.toEpochSecond(java.time.ZoneOffset.UTC);
            long maxEpoch = now.toEpochSecond(java.time.ZoneOffset.UTC);
            long randomEpoch = ThreadLocalRandom.current().nextLong(minEpoch, maxEpoch);

            return LocalDateTime.ofEpochSecond(randomEpoch, 0, java.time.ZoneOffset.UTC);
        }
        return null; // 30% chance of no last visit (new patients)
    }
}
