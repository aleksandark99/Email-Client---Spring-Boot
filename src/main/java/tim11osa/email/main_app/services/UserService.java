package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.UserInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.AuthenticationResponse;
import tim11osa.email.main_app.model.User;
import tim11osa.email.main_app.repository.UserRepository;
import tim11osa.email.main_app.security.LoggedUserDetails;
import tim11osa.email.main_app.utility.JwtUtil;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> getUserById(int idUser) {
        return null;
    }

    @Override
    public boolean  addUser(User newUser) {
        boolean exist = userRepository.findByUsername(newUser.getUsername()).isPresent();
        if (exist) return false;
        return userRepository.save(newUser) instanceof  User ? true : false;
    }

    @Override
    public void removeUser(int idUser) {

    }

    @Override
    public AuthenticationResponse updateUser(User user) {

        if(!userRepository.existsById(user.getId())){
            throw new ResourceNotFoundException("UserId: " + user.getId() + " not found");
        }

        Optional<User> u = userRepository.findByUsername(user.getUsername());
        if (u.isPresent()){
            if (u.get().getId() != user.getId()){
                return null;
            }
        }

        User newlySavedUser = userRepository.save(user);


        LoggedUserDetails ud = new  LoggedUserDetails(newlySavedUser);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                     new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        final String jwt = jwtTokenUtil.generateToken(ud);


        System.out.println("SIZE: " + userRepository.findById(newlySavedUser.getId()).get().getContacts().size());
        return  new AuthenticationResponse(jwt, newlySavedUser);


    }

    @Override
    public boolean exists(User potentiallyNewUser) {

        String username = potentiallyNewUser.getUsername();
        String password = potentiallyNewUser.getPassword();

        Optional<User> existingUser = userRepository.findByUsernameOrPassword(username, password);

        if(existingUser.isPresent()) return true;

        return false;
    }


}
