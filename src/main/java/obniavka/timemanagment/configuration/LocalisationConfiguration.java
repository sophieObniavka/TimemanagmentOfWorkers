package obniavka.timemanagment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocalisationConfiguration {
    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        Locale.setDefault(new Locale("uk"));
        final AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.forLanguageTag("uk"));

        return acceptHeaderLocaleResolver;
    }
}
