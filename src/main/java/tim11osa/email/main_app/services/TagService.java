package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.TagInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Tag;
import tim11osa.email.main_app.model.User;
import tim11osa.email.main_app.repository.TagRepository;
import tim11osa.email.main_app.repository.UserRepository;

import java.util.Set;
@Service
public class TagService implements TagInterface {



    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag createNewTag(Tag tag, int userId) {
        User u = null;

        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("UserId " + userId + " not found!");
        }
        u = userRepository.findById(userId).get();
        tag.setUser(u);

        Tag createdTag = tagRepository.save(tag);

        u.add(createdTag);
        return  createdTag;

    }
    @Override
    public Set<Tag> getAllTagsForUser(int userId) {
        return tagRepository.getAllTagsForUser(userId);
    }

    @Override
    public ResponseEntity<?> removeTag(int tagId) {

       return tagRepository.findById(tagId).map(tag -> {
            tag.setActive(false);
            tagRepository.save(tag);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));
    }

    @Override
    public Tag updateTag(Tag tag) {
        return tagRepository.findById(tag.getId()).map(tag1 -> {
            tag1.setName(tag.getName());
            return tagRepository.save(tag1);
        }).orElseThrow(() -> new ResourceNotFoundException("tag with id : " + tag.getId() + "not found"));
    }

    @Override
    public Tag getTagById(int id){
        return tagRepository.getTagById(id);
    }
}
