package www.rb.allvideodownload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import www.rb.allvideodownload.databinding.ActivityFacebookBinding;

public class FacebookActivity extends AppCompatActivity {
    private ActivityFacebookBinding binding;
    private FacebookActivity activity;
    private ProgressDialog dialog;
    private ClipboardManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_facebook);
        activity = this;
        manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait! It may take few seconds");
        dialog.setCancelable(false);

        binding.downloadBtn.setOnClickListener(v -> {
            getFacebookData();
        });
        binding.pasteBtn.setOnClickListener(v -> {
            pasteData();
        });

    }

    private void pasteData() {
        ClipData.Item item = manager.getPrimaryClip().getItemAt(0);
        String paste = item.getText().toString();
        if (!paste.isEmpty()){
            binding.fbUrl.setText(paste);
        }else binding.fbUrl.setError("Clipboard Empty");
    }


    private void getFacebookData() {
        if (binding.fbUrl.getText().toString().isEmpty()){
            binding.fbUrl.setError("Please paste link");
        }
            URL url = null;
            try {
                url = new URL(binding.fbUrl.getText().toString());

                String host = url.getHost();
                if (host.contains("facebook.com") || host.contains("fb.com") || host.contains("fb.watch")) {
                    new CallGetFbData().execute(binding.fbUrl.getText().toString());
                    dialog.show();
                } else {
                    Toast.makeText(activity, "Invalid Url", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

    }

    class CallGetFbData extends AsyncTask<String, Void, Document> {
        Document fbDoc;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                fbDoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fbDoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video\"]")
                    .last().attr("content");
            dialog.dismiss();

            if (!videoUrl.equals("")) {
                Util.download(videoUrl, Util.RootDirectoryFacebook, activity, "facebook " + System.currentTimeMillis() + ".mp4");
                binding.fbUrl.setText("");
            }
        }
    }
}