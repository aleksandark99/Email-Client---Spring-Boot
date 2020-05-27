package tim11osa.email.main_app.crud_interfaces;

import tim11osa.email.main_app.model.Folder;

import java.util.Set;

public interface FolderInterface {

    Set<Folder> getAllFolders(int account_id);

    Set<Folder> getSubFolders(int account_id, int parent_folder_id);

    Folder createFolder(Folder folder, int account_id);

    Folder createSubFolder(Folder folder, int account_id, int parent_folder_id);
}