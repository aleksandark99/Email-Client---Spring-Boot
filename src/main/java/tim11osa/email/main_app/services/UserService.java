package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.UserInterface;
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
        userRepository.save(newUser);
        return true;
    }

    @Override
    public void removeUser(int idUser) {

    }

    @Override
    public void updateUser(User user) {

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
