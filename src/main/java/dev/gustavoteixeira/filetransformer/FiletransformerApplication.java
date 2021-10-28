package dev.gustavoteixeira.filetransformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {BatchAutoConfiguration.class, DataSourceAutoConfiguration.class})
//@EnableTask
public class FiletransformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiletransformerApplication.class, args);
    }

}
