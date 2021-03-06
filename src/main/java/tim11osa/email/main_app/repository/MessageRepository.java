package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tim11osa.email.main_app.model.Message;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "Select * from message  where active=true and account_id = ?1 and folder_folder_id=?2", nativeQuery = true)
    Set<Message> getAllmessagesForAccount(int idAccount,int idFolderInbox);

    @Query(value = "Select * from message m where m.active = false and m.account_id = ?1", nativeQuery = true)
    Set<Message> getAllInactiveMessages(int account_id);

    @Query(value = "Select * from message m where m.account_id = ?1 and m.active = true and " +
            "m.from_col = (select a.username_col from account a where a.account_id = ?1)", nativeQuery = true)
    Set<Message> getAllSentMessages(int account_id);


    @Query(value = "Select * from message m where m.active = true and m.message_id in " +
            "(select m.message_id from message m JOIN recipient_to rto ON rto.message_id = m.message_id " +
            "JOIN rules r on r.folder_id = ?2 and  m.folder_folder_id = ?1 where (rto.recipient_to like concat('%', r.value_,'%') and r.operation_ = 0) " +
            "and r.condition_ = 0 and m.account_id = ?3 and m.message_id not in " +
            "(select m.message_id from message m JOIN recipient_to rto ON rto.message_id = m.message_id " +
            "JOIN rules r on r.folder_id = ?2 and m.folder_folder_id = ?1 where (rto.recipient_to like concat('%', r.value_,'%') and r.operation_ = 2) " +
            "and r.condition_ = 0 and m.account_id = ?3))", nativeQuery = true)
    Set<Message> getAllMessageByTO(int inbox_id, int folder_id, int account_id);



    @Query(value = "Select * from message m where m.active = true and m.message_id in " +
            "(select m.message_id from message m JOIN recipient_cc rcc ON rcc.message_id = m.message_id " +
            "JOIN rules r on r.folder_id = ?2 and m.folder_folder_id = ?1 where (rcc.recipient_cc like concat('%', r.value_,'%') and r.operation_ = 0) " +
            "and r.condition_ = 1 and m.account_id = ?3 and m.message_id not in " +
            "(select m.message_id from message m JOIN recipient_cc rcc ON rcc.message_id = m.message_id " +
            "JOIN rules r on r.folder_id = ?2 and m.folder_folder_id = ?1 where (rcc.recipient_cc like concat('%', r.value_,'%') and r.operation_ = 2) " +
            "and r.condition_ = 1 and m.account_id = ?3))", nativeQuery = true)
    Set<Message> getAllMessageByCC(int inbox_id, int folder_id, int account_id);


    @Query(value = "Select * from message m where m.active = true and m.message_id in " +
            "(Select m.message_id from message m JOIN rules r ON r.folder_id = ?2 and m.folder_folder_id = ?1 where " +
            "m.from_col like concat('%', r.value_, '%') and r.operation_ = 0 " +
            "and r.condition_ = 2 and m.account_id = ?3 and m.message_id not in " +
            "(select m.message_id from message m JOIN rules r ON r.folder_id = ?2 and m.folder_folder_id = ?1 where " +
            "m.from_col like concat('%', r.value_, '%') and r.operation_ = 2 " +
            "and r.condition_ = 2 and m.account_id = ?3))", nativeQuery = true)
    Set<Message> getAllMessageByFROM(int inbox_id, int folder_id, int account_id);



    @Query(value = "select * from message m where m.active = true and m.message_id in " +
            "(Select m.message_id from message m JOIN rules r ON r.folder_id = ?2 and m.folder_folder_id = ?1 where " +
            "m.subject like concat('%', r.value_, '%') and r.operation_ = 0 " +
            "and r.condition_ = 3 and m.account_id = ?3 and m.message_id not in " +
            "(select m.message_id from message m JOIN rules r ON r.folder_id = ?2 and m.folder_folder_id = ?1 where " +
            "m.subject like concat('%', r.value_, '%') and r.operation_ = 2 " +
            "and r.condition_ = 3 and m.account_id = ?3))", nativeQuery = true)
    Set<Message> getAllMessageBySUBJECT(int inbox_id, int folder_id, int account_id);


    @Modifying
    @Transactional
    @Query(value = "delete from attachments where id_message = ?1", nativeQuery = true)
    void deleteAttachments(int message_id);

    @Modifying
    @Transactional
    @Query(value = "delete from recipient_cc where message_id = ?1", nativeQuery = true)
    void deleteCC(int message_id);

    @Modifying
    @Transactional
    @Query(value = "delete from recipient_bcc where message_id = ?1", nativeQuery = true)
    void deleteBCC(int message_id);

    @Modifying
    @Transactional
    @Query(value = "delete from recipient_to where message_id = ?1", nativeQuery = true)
    void deleteTO(int message_id);

    @Modifying
    @Transactional
    @Query(value = "delete from tags_messages where message_id = ?1", nativeQuery = true)
    void deleteTags(int message_id);

    @Modifying
    @Transactional
    @Query(value = "delete from message where message_id = ?1 ", nativeQuery = true)
    void deleteMessageForTrash(int message_id);

}
