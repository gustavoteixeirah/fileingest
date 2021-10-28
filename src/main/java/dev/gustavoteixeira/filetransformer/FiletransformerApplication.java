package dev.gustavoteixeira.filetransformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class FiletransformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiletransformerApplication.class, args);
    }

}
