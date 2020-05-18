package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.UserInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.User;
import tim11osa.email.main_app.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> getUserById(int idUser) {
        return null;
    }

    @Override
    public boolean addUser(User newUser) {
        boolean exist = userRepository.findByUsernameOrPassword(newUser.getUsername(), newUser.getPassword()).isPresent();
        if (exist) return false;
        return userRepository.save(newUser) instanceof  User ? true : false;
    }

    @Override
    public void removeUser(int idUser) {

    }

    @Override
    public User updateUser(User user) {

        if(!userRepository.existsById(user.getId())){
            throw new ResourceNotFoundException("UserId: " + user.getId() + " not found");
        }

        return userRepository.findById(user.getId()). map(u -> {
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setUsername(user.getUsername());
            u.setPassword(user.getPassword());
            return userRepository.save(u);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId: " + user.getId() + " not found"));

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
