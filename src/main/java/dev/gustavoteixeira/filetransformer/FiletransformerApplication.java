package dev.gustavoteixeira.filetransformer;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

//@SpringBootApplication(exclude = {BatchAutoConfiguration.class, DataSourceAutoConfiguration.class})
////@EnableTask
//@EnableTask
@SpringBootApplication
public class FiletransformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiletransformerApplication.class, args);
    }

}
