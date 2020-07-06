package tim11osa.email.main_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tim11osa.email.main_app.model.User;

import java.util.Optional;

public class WebSecurity   {


    public  static boolean checkUserId(Authentication authentication, int id) {

        LoggedUserDetails loggedUserDetails= (LoggedUserDetails) authentication.getPrincipal();

        System.out.println(loggedUserDetails.getId());
        if(loggedUserDetails.getId()==id){
            return true;
        }


        return false;
    }
}
