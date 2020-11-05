INSERT INTO email_project.user_tab ( first_name, last_name, user_name, pass_word, roles) VALUES ( 'a','a','a','a', 'ROLE_USER');

INSERT INTO email_project.user_tab (first_name, last_name, user_name, pass_word, roles) VALUES ( 'b','b','b','b', 'ROLE_USER');

INSERT INTO email_project.contact (active, first_name, last_name, display_name, email, photo_path, note, user_id) VALUES (1, 'a', 'a','a','a','a','a', 1);


INSERT INTO folders (folder_id, active, name, account_id, parent_folder_id) VALUES (1, 1, 'Inbox',  1, null);
INSERT INTO folders (folder_id, active, name, account_id, parent_folder_id) VALUES (2, 1, 'Drafts', 1, null);
INSERT INTO folders (folder_id, active, name, account_id, parent_folder_id) VALUES (3, 1, 'Trash', 1, null);
INSERT INTO folders (folder_id, active, name, account_id, parent_folder_id) VALUES (4, 1, 'Favorites', 1, null);
INSERT INTO folders (folder_id, active, name, account_id, parent_folder_id) VALUES (5, 1, 'Sent', 1, null);

