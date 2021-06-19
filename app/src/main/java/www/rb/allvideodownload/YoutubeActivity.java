package www.rb.allvideodownload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import www.rb.allvideodownload.databinding.ActivityYoutubeBinding;

public class YoutubeActivity extends AppCompatActivity {

    private ActivityYoutubeBinding binding;
    private  String videoUrl, video;
    private ProgressDialog dialog;
    private YoutubeActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_youtube);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait! It may take few seconds");
        dialog.setCancelable(false);

        binding.downloadBtn.setOnClickListener(v -> {
//            TODO: feature need to add download video from youtube

        });
    }
}