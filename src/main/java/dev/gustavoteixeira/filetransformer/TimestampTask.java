package dev.gustavoteixeira.filetransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class TimestampTask implements CommandLineRunner {
    private final Log logger = LogFactory.getLog(TimestampTask.class);

    @Autowired
    private TimestampTaskProperties config;

    @Override
    public void run(String... strings) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat(config.getFormat());
        logger.info(dateFormat.format(new Date()));
    }

    public static void main(String[] args) {
        SpringApplication.run(TimestampTask.class, args);
    }
}
