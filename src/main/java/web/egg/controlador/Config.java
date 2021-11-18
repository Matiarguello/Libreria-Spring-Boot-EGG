package web.egg.controlador;

import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@Configuration
public class Config {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar(){
        return new Error();
    } 
    

    
}
