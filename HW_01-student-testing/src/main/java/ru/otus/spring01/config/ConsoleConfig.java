package ru.otus.spring01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring01.domain.ConsoleContext;

@Configuration
public class ConsoleConfig {
    @Bean
    ConsoleContext consoleContext() {
        return new ConsoleContext(System.out, System.in);
    }
}
