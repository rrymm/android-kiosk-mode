package ru.spicedevelopers.igorpavlov.kioskmode;

import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igorpavlov on 22.07.16.
 */
public class DropboxService {

    final static private String APP_KEY = "70e1p6e72fecncq";
    final static private String APP_SECRET = "cdg01scd4akk9jw";
    final static private String TOKEN = "Vb9-3xOWs3UAAAAAAAB1d-MjoSCjnwtmCIh_c7TyG1BEns27J1VIH_k3kyaST0dB";

    // In the class declaration section:
    private DropboxAPI<AndroidAuthSession> mDBApi;
    private String accessToken;

    private static DropboxService instance;

    private ArrayList<File> files;

    public DropboxService() {
        // And later in some initialization function:
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys, TOKEN);
        mDBApi = new DropboxAPI<>(session);

    }

    public static DropboxService getInstance() {
        if (instance == null)
            instance = new DropboxService();
        return instance;
    }

    public DropboxAPI<AndroidAuthSession> DBApi() {
        return mDBApi;
    }

    public void Authentificate() {
        Log.i("DbAuthLog", "Trying to access " + DBApi().getSession().authenticationSuccessful());
        if (DBApi().getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                DBApi().getSession().finishAuthentication();

                accessToken = DBApi().getSession().getOAuth2AccessToken();

                Log.i("DbAuthLog", "Got token " + accessToken);
                ListFiles("/videos");
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }


    private void ListFiles(String dirName) {
        try {
            List<DropboxAPI.Entry> items = DBApi().search(dirName, "mp4", 100, false);


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
            files = videos;

        } catch (DropboxException e) {
            e.printStackTrace();
        }
    }

    private File DownloadFile(String filename) {
        Log.i("DropboxLog", filename);

        File file = new File(filename);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DropboxAPI.DropboxFileInfo info = null;
        try {
            info = DBApi().getFile(filename, null, outputStream, null);
        } catch (DropboxException e) {
            e.printStackTrace();
        }
        Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
        return file;
    }

    public ArrayList<File> VideoFiles() {
        return files;
    }

}
