package demo.transactionvalidator.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("demo.transactionvalidator.api.*")
public class TransactionValidatorApiDemoApplication {

    public static void main (String[] args) {
        SpringApplication.run(TransactionValidatorApiDemoApplication.class, args);
    }

}
