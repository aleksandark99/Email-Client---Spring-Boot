package tim11osa.email.main_app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Tag;
import tim11osa.email.main_app.repository.TagRepository;
import tim11osa.email.main_app.services.TagService;

import java.util.Set;

@RestController
@RequestMapping("/user_id")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/users/{userId}/tags")
    public Set<Tag> getAllTagsForUser(@PathVariable("userId")int userId){
        return tagService.getAllTagsForUser(userId);
    }

    @PostMapping("tags/{userId}")
    public Tag createNewTagForUser(@RequestBody Tag tag,@PathVariable("userId")int userId){
        return  tagService.createNewTag(tag,userId);
    }
    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable("tagId")int tagId ){
        return tagService.removeTag(tagId);
    }

    @PutMapping("/tags/{userId}")
    public Tag updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }

}
