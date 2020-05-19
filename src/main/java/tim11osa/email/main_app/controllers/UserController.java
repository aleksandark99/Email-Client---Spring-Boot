package tim11osa.email.main_app.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.AuthenticationRequest;
import tim11osa.email.main_app.model.AuthenticationResponse;
import tim11osa.email.main_app.model.User;
import tim11osa.email.main_app.repository.UserRepository;
import tim11osa.email.main_app.security.LoggedUserDetailsService;
import tim11osa.email.main_app.services.UserService;
import tim11osa.email.main_app.utility.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private LoggedUserDetailsService loggedUserDetailService;



    @PutMapping("/users")
    public AuthenticationResponse updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }



    @PostMapping("/register")
    public boolean registerNewUser(@RequestBody User potentiallyNewUser){

        return userService.addUser(potentiallyNewUser);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    //@PostMapping("/authenticate")
    //ResponseEntity<?>
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

         UserDetails userDetails = loggedUserDetailService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        Optional<User> u = userRepository.findByUsername(userDetails.getUsername());

        //return ResponseEntity.ok(new AuthenticationResponse(jwt, u.get()));

        return  new AuthenticationResponse(jwt, u.get());
    }



}
