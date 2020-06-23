package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Folder;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

//@Where on Entity class does not work for me :(
    @Query(value = "Select * from folders f where f.folder_id = ?1 and f.account_id = ?2 and f.active = true", nativeQuery = true)
    Optional<Folder> getOneByIdAndAccount(int folder_id, int account_id);

    @Query(value = "Select * from folders f where f.name like '%Inbox%' and f.account_id = ?1", nativeQuery = true)
    Optional<Folder> getInboxByAccount(int account_id);

    @Query(value = "Select * from folders f where f.name like '%Sent%' and f.account_id = ?1", nativeQuery = true)
    Optional<Folder> getSentByAccount(int account_id);

    @Query(value = "Select * from folders f where f.name like '%Drafts%' and f.account_id = ?1", nativeQuery = true)
    Optional<Folder> getDraftsByAccount(int account_id);

    @Query(value = "Select * from folders f where f.name like '%Trash%' and f.account_id = ?1", nativeQuery = true)
    Optional<Folder> getTrashByAccount(int account_id);

    @Query(value = "Select * from folders f where f.name like '%Favorites%' and f.account_id = ?1", nativeQuery = true)
    Optional<Folder> getFavoritesByAccount(int account_id);

    @Query(value = "Select * from folders f where f.account_id = ?1 and f.parent_folder_id is null and f.active = true and" +
            " f.name not like '%Inbox%'", nativeQuery = true)
    Set<Folder> getAllFoldersByAccount(int account_id);

    @Query(value = "Select * from folders f where f.active = true and f.account_id = ?1 and f.name not in ('Inbox', 'Drafts', 'Sent', 'Trash')" +
            " and f.folder_id != ?2", nativeQuery = true)
    Set<Folder> getSomeFolders(int account_id, int folder_id);

    @Query(value = "Select * from folders f where f.account_id = ?1 and f.parent_folder_id = ?2 and f.active = true", nativeQuery = true)
    Set<Folder> getSubFoldersByParentAndAccount(int account_id, int parent_folder);

    @Query(value = "Select * from folders f, message m where m.message_id = ?1 and m.folder_folder_id = f.folder_id", nativeQuery = true)
    Folder getFolderForSomeMessage (int message_id);
}
