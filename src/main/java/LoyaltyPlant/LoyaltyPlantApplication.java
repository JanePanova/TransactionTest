package LoyaltyPlant;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LoyaltyPlantApplication {

    static final Logger logger = Logger.getLogger(LoyaltyPlantApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoyaltyPlantApplication.class, args);
        logger.info("Started");
    }
}
