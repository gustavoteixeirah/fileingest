package dev.gustavoteixeira.filetransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableTask
@SpringBootApplication
@EnableConfigurationProperties({TimestampTaskProperties.class})
public class TaskApplication {

    private static final Log logger = LogFactory.getLog(TaskApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Bean
    public TimestampTask timeStampTask() {
        return new TimestampTask();
    }

    /**
     * A commandline runner that prints a timestamp.
     */
    public static class TimestampTask implements CommandLineRunner {

        @Autowired
        private TimestampTaskProperties config;

        @Override
        public void run(String... strings) throws Exception {
            DateFormat dateFormat = new SimpleDateFormat(this.config.getFormat());
            logger.info(dateFormat.format(new Date()));
            logger.info("HAKUNA MATATA");
        }
    }
}
