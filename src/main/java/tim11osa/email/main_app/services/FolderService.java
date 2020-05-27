package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.FolderInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Folder;
import tim11osa.email.main_app.repository.AccountRepository;
import tim11osa.email.main_app.repository.FolderRepository;

import java.util.Set;


@Service
public class FolderService implements FolderInterface {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    AccountRepository accountRepository;

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

        return accountRepository.findById(account_id).map(account -> {

            folder.setAccount(account);

            return folderRepository.save(folder);

        }).orElseThrow(() -> new ResourceNotFoundException("The account " + account_id + "is not found!"));
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
}
