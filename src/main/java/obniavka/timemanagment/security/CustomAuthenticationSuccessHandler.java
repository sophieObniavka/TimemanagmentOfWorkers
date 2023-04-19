package obniavka.timemanagment.security;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        if(authentication.getPrincipal() instanceof UserDto userDto){
            setDefaultTargetUrl(userDto.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ? "/admin/homeAdmin" : "/report");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }


}
