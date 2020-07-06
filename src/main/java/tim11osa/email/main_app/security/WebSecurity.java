package tim11osa.email.main_app.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tim11osa.email.main_app.controllers.AccountController;
import tim11osa.email.main_app.model.User;

import java.util.Optional;

public class WebSecurity   {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);


    public  static boolean checkUserId(Authentication authentication, int id) {
        LOGGER.info("Acess control for users");

        LoggedUserDetails loggedUserDetails= (LoggedUserDetails) authentication.getPrincipal();

        System.out.println(loggedUserDetails.getId());
        if(loggedUserDetails.getId()==id){
            return true;
        }


        return false;
    }
}
