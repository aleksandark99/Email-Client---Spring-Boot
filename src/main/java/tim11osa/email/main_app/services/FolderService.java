package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.FolderInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Folder;
import tim11osa.email.main_app.repository.AccountRepository;
import tim11osa.email.main_app.repository.FolderRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class FolderService implements FolderInterface {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Folder getOneByAccount(int folder_id, int account_id) {

        return folderRepository.getOneByIdAndAccount(folder_id, account_id).get();
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
