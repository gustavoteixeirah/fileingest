package dev.gustavoteixeira.filetransformer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


@Configuration
public class AppConfig {

    @Bean
    public Function<String, String> upper() {
        return String::toUpperCase;
    }

}