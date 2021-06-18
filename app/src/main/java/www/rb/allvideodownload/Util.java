package www.rb.allvideodownload;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class Util {
    public static String RootDirectoryFacebook = "/AVD Download/facebook/";
    public static String RootDirectoryShareChat = "/AVD Download/sharechat/";
    public static String RootDirectoryInstagram = "/AVD Download/instagram/";
    public static String RootDirectoryYoutube = "/AVD Download/youtube/";

    public static File RootDirectoryWhatsapp =
            new File(Environment.getExternalStorageDirectory()
                    + "/Download/AVD Download/Whatsapp");

    public static void createFileFolder() {
        if (!RootDirectoryWhatsapp.exists()) {
            RootDirectoryWhatsapp.mkdirs();
        }
    }

    public static void download(String downloadPath, String destinationPath, Context context, String fileName) {
        Toast.makeText(context, "Downloading Started", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(downloadPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, destinationPath + fileName);
        ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
    }
}
