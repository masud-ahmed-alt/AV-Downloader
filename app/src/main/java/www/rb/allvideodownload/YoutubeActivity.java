package www.rb.allvideodownload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import www.rb.allvideodownload.databinding.ActivityYoutubeBinding;

public class YoutubeActivity extends AppCompatActivity {

    private ActivityYoutubeBinding binding;
    private  String videoUrl, video;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_youtube);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading! Please Wait");
        dialog.setCancelable(false);

        binding.downloadBtn.setOnClickListener(v -> {
//            TODO: feature need to add download video from youtube
            binding.ytUrl.setText("");
        });
    }
}