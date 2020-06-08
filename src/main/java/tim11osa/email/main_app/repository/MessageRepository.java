package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {


}
