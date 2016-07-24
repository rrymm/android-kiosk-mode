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
            //Log.i("java", "Linked account: " + client.getAccountInfo().displayName);
            System.out.println(client.getAccountInfo().displayName);
        } catch (DbxException e) {
            //Log.i("java", e.toString());
        }
    }

    public static DropboxService getInstance() {
        if (instance == null)
            instance = new DropboxService();
        return instance;
    }

    public void Authentificate() {


    }


    private void ListFiles(String dirName) {
        try {
            //DbxEntry entry = client.getMetadata("/videos");
            DbxEntry.WithChildren data = client.getMetadataWithChildren("/videos");
            for (DbxEntry entry : data.children) {
                Log.i("entry", entry.path);
            }
            /*
            List<DbxEntry> items = client.(dirName, "mp4", 100, false);


            if (items.size() == 0) {
                items = DBApi().search("/", "videos", 100, true);
                Log.i("DBAuthLog", "Created videos folder");
                if (items.size() == 0)
                    DBApi().createFolder("videos");
            }

            ArrayList<File> videos = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                String name = items.get(i).path + items.get(i).fileName();
                Log.i("DbAuthLog", name);
                videos.add(DownloadFile(name));
            }
            files = videos;*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<File> VideoFiles() throws DbxException {
        DbxEntry.WithChildren children = client.getMetadataWithChildren("/videos");
        files = new ArrayList<>();


        for (DbxEntry entry : children.children) {
            DbxEntry.File file = entry.asFile();
            File f = new File(file.name);
            try {
                FileOutputStream outputStream = new FileOutputStream(f);
                client.getFile(file.path, file.rev, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            files.add(f);
        }
        return files;
    }

}
