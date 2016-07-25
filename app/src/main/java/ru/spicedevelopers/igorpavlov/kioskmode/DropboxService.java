package ru.spicedevelopers.igorpavlov.kioskmode;

import android.util.Log;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxDeltaC;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by igorpavlov on 22.07.16.
 */
public class DropboxService {

    final static private String APP_KEY = "70e1p6e72fecncq";
    final static private String APP_SECRET = "cdg01scd4akk9jw";
    final static private String TOKEN = "Vb9-3xOWs3UAAAAAAAB1d-MjoSCjnwtmCIh_c7TyG1BEns27J1VIH_k3kyaST0dB";
    final static private String APP_PATH = "/sdcard/Android/data/kiosk";


    private static DropboxService instance;

    private ArrayList<File> files;
    public DbxClient client;

    public DropboxService() {
        // And later in some initialization function:

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());

        client = new DbxClient(config, TOKEN);
        try {
            System.out.println(client.getAccountInfo().displayName);
        } catch (DbxException ignored) {
        }
    }

    public static DropboxService getInstance() {
        if (instance == null)
            EstablishConnection();
        return instance;
    }

    public static void EstablishConnection() {
        instance = new DropboxService();
    }

    public ArrayList<File> VideoFiles() throws DbxException {
        DbxEntry.WithChildren children = client.getMetadataWithChildren("/videos");
        files = new ArrayList<>();

        File directory = new File(APP_PATH);
        if (directory.mkdirs())
            Log.i("kiosk", "Folder created");


        for (DbxEntry entry : children.children) {
            DbxEntry.File file = entry.asFile();
            File f = new File(directory.getAbsolutePath() + file.name);
            DropboxFile dbFile = new DropboxFile(f, file);
            if (dbFile.Store()) {
                Log.i("kiosk", f.getPath());
                files.add(f);
            }
        }
        return files;
    }

}

