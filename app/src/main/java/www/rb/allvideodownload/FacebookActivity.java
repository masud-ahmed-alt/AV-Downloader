package www.rb.allvideodownload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_facebook);
        activity = this;

        binding.downloadBtn.setOnClickListener(v -> {
            getFacebookData();
            binding.fbUrl.setText("");
        });


    }

    private void getFacebookData() {

        URL url = null;
        try {
            url = new URL(binding.fbUrl.getText().toString());

            String host = url.getHost();
            if (host.contains("facebook.com") || host.contains("fb.com") || host.contains("fb.watch")) {
                new CallGetFbData().execute(binding.fbUrl.getText().toString());
            } else Toast.makeText(activity, "Invalid Url", Toast.LENGTH_SHORT).show();
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

            if (!videoUrl.equals("")) {
                Util.download(videoUrl, Util.RootDirectoryFacebook, activity, "facebook " + System.currentTimeMillis() + ".mp4");
            }
        }
    }
}