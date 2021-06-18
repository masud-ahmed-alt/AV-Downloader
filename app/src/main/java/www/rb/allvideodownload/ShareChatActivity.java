package www.rb.allvideodownload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import www.rb.allvideodownload.databinding.ActivityShareChatBinding;

public class ShareChatActivity extends AppCompatActivity {
    private ActivityShareChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_share_chat);

        binding.downloadBtn.setOnClickListener(v -> {
            getShareChatData();
        });

    }

    private void getShareChatData() {

        URL url = null;
        try {
            url = new URL(binding.shareChatUrl.getText().toString());
            String host = url.getHost();
            if (host.contains("sharechat")){
                new CallGetShareChatData().execute(binding.shareChatUrl.getText().toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    class CallGetShareChatData extends AsyncTask<String,Void, Document> {

        Document scDocument;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                scDocument = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scDocument;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video:secure_url\"]")
                    .last().attr("content");
            if (!videoUrl.equals("")){
                Util.download(videoUrl,Util.RootDirectoryShareChat,ShareChatActivity.this,"Sharechat "+System.currentTimeMillis()+".mp4");
            }
        }
    }
}