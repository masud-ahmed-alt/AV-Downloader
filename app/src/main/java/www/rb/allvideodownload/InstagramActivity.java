package www.rb.allvideodownload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.rb.allvideodownload.API.ApiUtilities;
import www.rb.allvideodownload.databinding.ActivityInstagramBinding;
import www.rb.allvideodownload.model.InstaModel;

public class InstagramActivity extends AppCompatActivity {
    private ActivityInstagramBinding binding;
    private  String videoUrl, video;
    private ProgressDialog dialog;
    private ClipboardManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_instagram);

        manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait! It may take few seconds");
        dialog.setCancelable(false);

        binding.downloadBtn.setOnClickListener(v -> {
            getVideo();
            binding.instaUrl.setText("");
        });
        binding.pasteBtn.setOnClickListener(v -> {
            pasteData();
        });

    }
    private void pasteData() {
        ClipData.Item item = manager.getPrimaryClip().getItemAt(0);
        String paste = item.getText().toString();
        if (!paste.isEmpty()){
            binding.instaUrl.setText(paste);
        }else binding.instaUrl.setError("Clipboard Empty");
    }


    private void getVideo() {
        if (TextUtils.isEmpty(binding.instaUrl.getText().toString())){
            binding.instaUrl.setError("Please Paste Link");
        }else {
            if (binding.instaUrl.getText().toString().contains("instagram")){
                dialog.show();
                String replace;
                if (binding.instaUrl.getText().toString().contains("?utm_source=ig_web_copy_link")){
                    replace = "?utm_source=ig_web_copy_link";
                   video = binding.instaUrl.getText().toString().replace(replace,"");
                }else if (binding.instaUrl.getText().toString().contains("?utm_medium=copy_link")){
                    replace = "?utm_medium=copy_link";
                    video = binding.instaUrl.getText().toString().replace(replace,"");
                }else
                    video = binding.instaUrl.getText().toString();

                ApiUtilities.getApiInterface().getInfo(video).enqueue(new Callback<InstaModel>() {
                    @Override
                    public void onResponse(Call<InstaModel> call, Response<InstaModel> response) {
                        if (response.body()!=null){
                            dialog.dismiss();
                            videoUrl =response.body().getInfo().get(0).getVideo_url();
                            Log.d("TAG","onResponse: "+response.body().getInfo().get(0).getVideo_url());
                            Util.download(videoUrl,Util.RootDirectoryInstagram,InstagramActivity.this,"Instagram"+System.currentTimeMillis()+".mp4");
                        }
                    }

                    @Override
                    public void onFailure(Call<InstaModel> call, Throwable t) {
                        dialog.dismiss();
                        Log.d("Instagram","onFailure: "+t.getMessage());
                    }
                });
                
            }else {
                Toast.makeText(this, "Please provide Instagram Url", Toast.LENGTH_SHORT).show();
            }
        }
    }
}