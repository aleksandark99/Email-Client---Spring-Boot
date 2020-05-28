package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Folder;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {


    @Query(value = "Select * from folders f where f.folder_id = ?1 and f.account_id = ?2", nativeQuery = true)
    Optional<Folder> getOneByIdAndAccount(int folder_id, int account_id);


    @Query(value = "Select * from folders f where f.account_id = ?1 and f.folder_id not in " +
            "(select b.folder_id from folders a, folders b where a.folder_id = b.parent_folder_id)", nativeQuery = true)
    Set<Folder> getAllFoldersByAccount(int account_id);


    @Query(value = "Select * from folders f where f.account_id = ?1 and f.parent_folder_id = ?2", nativeQuery = true)
    Set<Folder> getSubFoldersByParentAndAccount(int account_id, int parent_folder);

}
