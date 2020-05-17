package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {



    //Integer findTop1ByIdOrderByIdDesc();
    //Optional<Contact> findTop1OrderById();
    Optional<Contact> findTopByOrderById();
    @Query("select c from Contact c where c.user.id = :userid")
    Set<Contact> findByUser(@Param("userid") int userid);
    Set<Contact> findByUser(User user);
}
