package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Tag;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "Select * from tag f where user_id = ?1  and active = true", nativeQuery = true)
    Set<Tag> getAllTagsForUser(int userId);

    @Query(value = "Select * from tag f where tag_id = ?1", nativeQuery = true)
    Tag getTagById(int tagId);
}
