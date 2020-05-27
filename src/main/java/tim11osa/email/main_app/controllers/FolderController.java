package tim11osa.email.main_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Folder;
import tim11osa.email.main_app.services.FolderService;

import java.util.Set;

@RestController
@RequestMapping("/")
public class FolderController {

    @Autowired
    FolderService folderService;


    @GetMapping("/folders/{account_id}")
    public Set<Folder> getAllFolders(@PathVariable("account_id") int account_id){

        return folderService.getAllFolders(account_id);

    }

    @GetMapping("/subfolders/{account_id}/{parent_id}")
    public Set<Folder> getSubFolders(@PathVariable("account_id") int account_id, @PathVariable("parent_id") int parent_id){

        return folderService.getSubFolders(account_id, parent_id);
    }

    @PostMapping("/folder/{account_id}")
    public Folder createFolder(@RequestBody Folder folder, @PathVariable("account_id") int account_id){

        return folderService.createFolder(folder, account_id);
    }

    @PostMapping("/folder/{account_id}/{parent_folder_id}")
    public Folder createSubFolder(@RequestBody Folder folder, @PathVariable("account_id") int acc_id, @PathVariable("parent_folder_id") int parent_id){

        return folderService.createSubFolder(folder, acc_id, parent_id);
    }
}
