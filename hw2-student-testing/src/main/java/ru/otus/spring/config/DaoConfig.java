package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.FAQDao;
import ru.otus.spring.dao.FAQDaoSimple;

@PropertySource("classpath:application.properties")
@Configuration
public class DaoConfig {
    @Bean
    public FAQDao faqDao(){
        return new FAQDaoSimple();
    }
}
