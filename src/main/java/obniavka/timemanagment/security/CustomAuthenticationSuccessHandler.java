package obniavka.timemanagment.security;

import obniavka.timemanagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    SimpleUrlAuthenticationSuccessHandler userSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/");
    SimpleUrlAuthenticationSuccessHandler adminSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/admin/homeAdmin");
    @Autowired HttpSession session; //autowiring session

    @Autowired
    UserRepository repository; //autowire the user repo

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            System.out.println("Is Admin " + authorityName.equals("ADMIN"));

            if (authorityName.equals("ADMIN")) {
                // if the user is an ADMIN delegate to the adminSuccessHandler

                this.adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }
            this.userSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
        // if the user is not an admin delegate to the userSuccessHandler

    }
}
