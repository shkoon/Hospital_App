package ma.enset.tp3_hospital;

import ma.enset.tp3_hospital.entities.Patient;
import ma.enset.tp3_hospital.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class Tp3HospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp3HospitalApplication.class, args);
    }


    //@Bean
    CommandLineRunner start(PatientRepository patientRepository){

        return args -> {
            Patient patient=new Patient();
            patient.setNom("Mohammed");
            patient.setDateNaissance(new Date());
            patient.setMalade(false);
            patient.setScore(99);


            Patient patient1=new Patient(null,"Hanane",new Date(),false,100);
            Patient patient2=Patient.builder().nom("Imane").dateNaissance(new Date()).score(99).malade(false).build();

            patientRepository.save(patient);
            patientRepository.save(patient1);
            patientRepository.save(patient2);

        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
