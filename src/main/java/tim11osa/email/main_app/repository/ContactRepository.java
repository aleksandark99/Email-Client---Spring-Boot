package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.model.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {



    //Integer findTop1ByIdOrderByIdDesc();
    //Optional<Contact> findTop1OrderById();
    Optional<Contact> findTopByOrderById();
    List<Contact> findByUser(User user);
}
