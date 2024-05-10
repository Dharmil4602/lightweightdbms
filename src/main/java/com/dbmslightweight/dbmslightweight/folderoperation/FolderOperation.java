package com.dbmslightweight.dbmslightweight.folderoperation;

import java.io.File;

public class FolderOperation {
    public void createFolder(String folderPath) {
        File folder = new File(folderPath);

        // Check if the folder already exists
        if (folder.exists()) {
            System.out.println("The folder already exists.");
        } else {
            // Create the folder
            boolean folderCreated = folder.mkdir();

            if (folderCreated) {
                System.out.println("Folder created successfully.");
            } else {
                System.out.println("Failed to create the folder.");
            }
        }
    }
}
