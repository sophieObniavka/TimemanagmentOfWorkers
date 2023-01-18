package obniavka.timemanagment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ResourceBundleConfiguration {

    @Bean
    public ResourceBundleMessageSource messageSource(){
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n");

        return messageSource;
    }
}
