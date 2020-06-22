package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Message;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "Select * from message  where active=true and account_id = ?1 and folder_folder_id=?2", nativeQuery = true)
    Set<Message> getAllmessagesForAccount(int idAccount,int idFolderInbox);

}
