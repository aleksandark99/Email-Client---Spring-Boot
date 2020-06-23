package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.FolderInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Folder;
import tim11osa.email.main_app.repository.AccountRepository;
import tim11osa.email.main_app.repository.FolderRepository;
import tim11osa.email.main_app.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class FolderService implements FolderInterface {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public Folder getOneByAccount(int folder_id, int account_id) {

        return folderRepository.getOneByIdAndAccount(folder_id, account_id).get();
    }

    @Override
    public Folder getInboxByAccount(int account_id) {

        return folderRepository.getInboxByAccount(account_id).get();
    }

    @Override
    public Folder getSentByAccount(int account_id) {

        return folderRepository.getSentByAccount(account_id).get();
    }

    @Override
    public Folder getDraftsByAccount(int account_id) {

        return folderRepository.getDraftsByAccount(account_id).get();
    }

    @Override
    public Folder getFavoritesByAccount(int account_id) {

        return folderRepository.getFavoritesByAccount(account_id).get();
    }

    @Override
    public Folder getTrashByAccount(int account_id) {

        return folderRepository.getTrashByAccount(account_id).get();
    }

    @Override
    public Set<Folder> getAllFolders(int account_id) {

        return folderRepository.getAllFoldersByAccount(account_id);
    }

    @Override
    public Set<Folder> getSubFolders(int account_id, int parent_folder_id) {

        return folderRepository.getSubFoldersByParentAndAccount(account_id, parent_folder_id);
    }

    @Override
    public Set<Folder> getSomeFolders(int account_id, int message_id) {

        if(!messageRepository.existsById(message_id)){

            throw new ResourceNotFoundException("The message " + message_id + " is not exist!");
        }

        int folder_id = folderRepository.getFolderForSomeMessage(message_id).getId();

        return folderRepository.getSomeFolders(account_id, folder_id);
    }

    @Override
    public Folder createFolder(Folder folder, int account_id) {

        System.out.println(folder);

        return accountRepository.findById(account_id).map(account -> {

            folder.setAccount(account);
            folder.setActive(true);

            int parent_folder_id = (folder.getParent_folder() != null) ? folder.getParent_folder().getId() : 0;

            if(parent_folder_id != 0) {

                Folder parent_folder = folderRepository.findById(parent_folder_id).get();
                folder.setParent_folder(parent_folder);

            }else{

                folder.setParent_folder(null);
            }

            return folderRepository.save(folder);

        }).orElseThrow(() -> new ResourceNotFoundException("The account " + account_id + " is not found!"));
    }

    @Override
    public Folder createSubFolder(Folder folder, int account_id, int parent_folder_id) {

        return accountRepository.findById(account_id).map(account -> {

            folder.setAccount(account);

            Folder parent_folder = folderRepository.findById(parent_folder_id).get();

            folder.setParent_folder(parent_folder);

            return folderRepository.save(folder);

        }).orElseThrow(() -> new ResourceNotFoundException("The account " + account_id + " is not found"));
    }

    @Override
    public ResponseEntity<?> createInitialFolders(Account account){

        List<Folder> initialFolders = new ArrayList<>();

        Folder inbox = new Folder(true, "Inbox", account);
        Folder sent = new Folder(true, "Sent", account);
        Folder drafts = new Folder(true, "Drafts", account);
        Folder trash = new Folder(true, "Trash", account);
        Folder favorites = new Folder(true, "Favorites", account);

        initialFolders.add(inbox);
        initialFolders.add(sent);
        initialFolders.add(drafts);
        initialFolders.add(trash);
        initialFolders.add(favorites);

        folderRepository.saveAll(initialFolders);

        return ResponseEntity.ok().build();
    }

    @Override
    public Folder updateFolder(Folder folderToUpdate, int account_id) {

        if(!accountRepository.existsById(account_id))

            throw new ResourceNotFoundException("The account " + account_id + " is not found!");

        return folderRepository.findById(folderToUpdate.getId()).map(folder -> {

            folder.setName(folderToUpdate.getName());

            return folderRepository.save(folder);

        }).orElseThrow(() -> new ResourceNotFoundException("The folder " + folderToUpdate.getId() + "is not found!"));
    }


    @Override
    public ResponseEntity<?> removeFolder(int folder_id, int account_id) {

        if(!accountRepository.existsById(account_id))

            throw new ResourceNotFoundException("The account " + account_id + " is not found!");

        return folderRepository.getOneByIdAndAccount(folder_id, account_id).map(folder -> {

            folder.setParent_folder(null);

            folderRepository.delete(folder);

            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new ResourceNotFoundException("The account " + account_id + " is not found!"));
    }
}
