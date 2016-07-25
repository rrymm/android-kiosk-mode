package ru.spicedevelopers.igorpavlov.kioskmode;

import android.util.Log;

import com.dropbox.core.DbxEntry;

import java.io.File;
import java.io.FileOutputStream;

public class DropboxFile {


    public File systemFile;
    public DbxEntry.File dropboxFile;

    public DropboxFile(File SystemFile, DbxEntry.File DropboxFile) {
        systemFile = SystemFile;
        dropboxFile = DropboxFile;
    }

    public Boolean Store() {
        if (systemFile.exists() && systemFile.length() != dropboxFile.numBytes) {
            try {
                FileOutputStream outputStream = new FileOutputStream(systemFile);
                DropboxService.getInstance().client.getFile(dropboxFile.path, dropboxFile.rev, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
