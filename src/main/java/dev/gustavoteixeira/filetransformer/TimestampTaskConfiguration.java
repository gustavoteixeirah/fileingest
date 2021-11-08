package dev.gustavoteixeira.filetransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A commandline runner that prints a timestamp.
 */
@EnableTask
@Configuration
@EnableConfigurationProperties({TimestampTaskProperties.class})
public class TimestampTaskConfiguration {

    @Bean
    public TimestampTask timeStampTask() {
        return new TimestampTask();
    }

    /**
     * A commandline runner that prints a timestamp.
     */


}

