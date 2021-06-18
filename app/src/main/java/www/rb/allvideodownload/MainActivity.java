package www.rb.allvideodownload;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import www.rb.allvideodownload.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        MobileAds.initialize(this);

        binding.shareChat.setOnClickListener(v -> {
//            showAds();
            startActivity(new Intent(this,ShareChatActivity.class));
        });
        binding.whatsApp.setOnClickListener(v -> {
//            showAds();
            startActivity(new Intent(this,WhatsAppActivity.class));
        });
        binding.facebook.setOnClickListener(v -> {
//            showAds();
            startActivity(new Intent(this,FacebookActivity.class));
        });

        binding.instagram.setOnClickListener(v -> {
//            showAds();
            startActivity(new Intent(this,InstagramActivity.class));
        });
        binding.youtube.setOnClickListener(v -> {
//            showAds();
            startActivity(new Intent(this,YoutubeActivity.class));
        });
        binding.aboutUs.setOnClickListener(v -> {
//            showAds();
            startActivity(new Intent(this,AboutActivity.class));
        });
        binding.share.setOnClickListener(v -> {
            shareApp();
        });


        checkPermission();
    }

    private void shareApp() {
            final String appPackageName = getPackageName();

        Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey Dear, Check out the AV Downloader App which " +
                "is download all kind of videos easily and it quality is awesome at: https://play.google.com/store/apps/details?id=" + appPackageName);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void showAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @NotNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                interstitialAd.show(MainActivity.this);
            }

            @Override
            public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }
        });
    }

    private void checkPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (!report.areAllPermissionsGranted()){
                    checkPermission();
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }
}