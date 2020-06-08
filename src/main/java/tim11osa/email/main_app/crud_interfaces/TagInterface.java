package tim11osa.email.main_app.crud_interfaces;

import org.springframework.http.ResponseEntity;
import tim11osa.email.main_app.model.Tag;

import java.util.Set;

public interface TagInterface {

    Tag createNewTag(Tag tag,int userId);

    Set<Tag> getAllTagsForUser(int userId);

    ResponseEntity<?> removeTag (int tagId);

    Tag updateTag(Tag tag);

    Tag getTagById(int id);



}
