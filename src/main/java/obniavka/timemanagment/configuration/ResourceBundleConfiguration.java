package obniavka.timemanagment.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceBundleConfiguration implements WebMvcConfigurer {

    @Bean("messageSource")
    public MessageSource messageSource(){
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/labels");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
