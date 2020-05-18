package tim11osa.email.main_app.crud_interfaces;

import tim11osa.email.main_app.model.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserInterface {

    ArrayList<User> getAllUsers();

    Optional<User> getUserById(int idUser);

    boolean addUser(User newUser);

    void removeUser(int idUser);

    User updateUser(User user);

    boolean exists(User potentiallyNewUser);


}
