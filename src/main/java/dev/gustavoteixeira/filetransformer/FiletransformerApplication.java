package dev.gustavoteixeira.filetransformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.messaging.MessagingAutoConfiguration;

//@SpringBootApplication(exclude = {BatchAutoConfiguration.class, DataSourceAutoConfiguration.class})
////@EnableTask
//@EnableTask
@SpringBootApplication(exclude = {ContextRegionProviderAutoConfiguration.class,
        ContextStackAutoConfiguration.class,
        MessagingAutoConfiguration.class})
public class FiletransformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiletransformerApplication.class, args);
    }

}
