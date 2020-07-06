package tim11osa.email.main_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public WebSecurity webSecurity(){
        return new WebSecurity();
    }
}
