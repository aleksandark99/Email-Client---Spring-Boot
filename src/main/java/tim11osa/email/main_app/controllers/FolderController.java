package tim11osa.email.main_app.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Folder;
import tim11osa.email.main_app.repository.FolderRepository;
import tim11osa.email.main_app.services.FolderService;

import javax.naming.Context;
import java.util.Set;

@RestController
@RequestMapping("/{user_id}")
public class FolderController {

    private static final Logger LOGGER = LogManager.getLogger(FolderController.class);

    @Autowired
    FolderService folderService;


    @GetMapping("/folder/{folder_id}/{account_id}")
    public Folder getOneByAccount(@PathVariable("folder_id") int id, @PathVariable("account_id") int account_id){

        LOGGER.info("Method who returns folder " + id + " for account " + account_id + " has called");

        return folderService.getOneByAccount(id, account_id);
    }

    @GetMapping("/folders/{account_id}")
    public Set<Folder> getAllFolders(@PathVariable("account_id") int account_id){

        LOGGER.info("Method who returns set of folders for account " + account_id + " has called");
        return folderService.getAllFolders(account_id);
    }

    @GetMapping("/subfolders/{account_id}/{parent_id}")
    public Set<Folder> getSubFolders(@PathVariable("account_id") int account_id, @PathVariable("parent_id") int parent_id){

        LOGGER.info("Method who returns set of subfolders for account " + account_id + " and parent folder " + parent_id  + " has called");
        return folderService.getSubFolders(account_id, parent_id);
    }

    @GetMapping("/some_folders/{account_id}/{message_id}")
    public Set<Folder> getFoldersForCheck (@PathVariable("account_id") int account_id, @PathVariable("message_id") int message_id){

        LOGGER.info("Method who returns set of folders  by account " + account_id + " for manually message movement has called");
        return folderService.getSomeFolders(account_id, message_id);
    }

    @PostMapping("/folder/{account_id}")
    public Folder createFolder(@RequestBody Folder folder, @PathVariable("account_id") int account_id){

        LOGGER.info("Method for creating a new folder for account " + account_id + " has called");
        return folderService.createFolder(folder, account_id);
    }

    @PostMapping("/folder/{account_id}/{parent_folder_id}")
    public Folder createSubFolder(@RequestBody Folder folder, @PathVariable("account_id") int acc_id, @PathVariable("parent_folder_id") int parent_id){

        return folderService.createSubFolder(folder, acc_id, parent_id);
    }

    @PutMapping("/folder/{account_id}")
    public Folder updateFolder(@RequestBody Folder folder, @PathVariable("account_id") int acc_id){

        LOGGER.info("Method for updating an existing folder for account " + acc_id + " has called");
        return folderService.updateFolder(folder, acc_id);
    }

    @DeleteMapping("/folder/{folder_id}/{account_id}")
    public ResponseEntity<?> removeFolder(@PathVariable("folder_id") int folder_id, @PathVariable("account_id") int account_id){

        LOGGER.info("Method for removing an existing folder for account " + account_id + " has called");
        return folderService.removeFolder(folder_id, account_id);
    }

}
