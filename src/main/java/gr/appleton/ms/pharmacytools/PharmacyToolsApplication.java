package gr.appleton.ms.pharmacytools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The type Pharmacy tools application.
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
public class PharmacyToolsApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PharmacyToolsApplication.class, args);
    }

}
