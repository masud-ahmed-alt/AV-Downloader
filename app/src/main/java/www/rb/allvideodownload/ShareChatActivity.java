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

import www.rb.allvideodownload.databinding.ActivityShareChatBinding;

public class ShareChatActivity extends AppCompatActivity {
    private ActivityShareChatBinding binding;
    private ProgressDialog dialog;

    ClipboardManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_share_chat);


        manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait! It may take few seconds");
        dialog.setCancelable(false);

        binding.downloadBtn.setOnClickListener(v -> {
            getShareChatData();

        });
        binding.pasteBtn.setOnClickListener(v -> {
            pasteData();
        });

    }

    private void pasteData() {
        ClipData.Item item = manager.getPrimaryClip().getItemAt(0);
        String paste = item.getText().toString();
        if (!paste.isEmpty()){
            binding.shareChatUrl.setText(paste);
        }else binding.shareChatUrl.setError("Clipboard Empty");
    }

    private void getShareChatData() {

        if (binding.shareChatUrl.getText().toString().isEmpty()){
            binding.shareChatUrl.setError("Please paste link");
        }

        URL url = null;
        try {
            url = new URL(binding.shareChatUrl.getText().toString());
            String host = url.getHost();
            if (host.contains("sharechat")){
                new CallGetShareChatData().execute(binding.shareChatUrl.getText().toString());
                dialog.show();
            }else Toast.makeText(ShareChatActivity.this, "Invalid Link", Toast.LENGTH_SHORT).show();
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
            dialog.dismiss();
            if (!videoUrl.equals("")){
                Util.download(videoUrl,Util.RootDirectoryShareChat,ShareChatActivity.this,"Sharechat "+System.currentTimeMillis()+".mp4");
                binding.shareChatUrl.setText("");
            }
        }
    }
}