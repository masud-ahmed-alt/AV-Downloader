package www.rb.allvideodownload.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import www.rb.allvideodownload.R;
import www.rb.allvideodownload.Util;
import www.rb.allvideodownload.databinding.WhatsappItemBinding;
import www.rb.allvideodownload.model.WhatsappStatusModel;


public class WhatsappAdapter extends RecyclerView.Adapter<WhatsappAdapter.ViewHolder> {
    private ArrayList<WhatsappStatusModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String saveFilePath = Util.RootDirectoryWhatsapp+"/";

    public WhatsappAdapter(ArrayList<WhatsappStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if (inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        return new ViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.whatsapp_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WhatsappAdapter.ViewHolder holder, int position) {

        WhatsappStatusModel item = list.get(position);
        if (item.getUri().toString().endsWith(".mp4"))
            holder.binding.playButton.setVisibility(View.VISIBLE);
        else holder.binding.playButton.setVisibility(View.GONE);

        Glide.with(context).load(item.getPath()).into(holder.binding.statusImage);

        holder.binding.download.setOnClickListener(v -> {
            Util.createFileFolder();
            final String path = item.getPath();
            final File file = new File(path);
            File destinationFile = new File(saveFilePath);

            try {
                FileUtils.copyFileToDirectory(file,destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Saved to :"+saveFilePath, Toast.LENGTH_SHORT).show();
            holder.binding.txDownload.setText("Downloaded");
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WhatsappItemBinding binding;

        public ViewHolder(WhatsappItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
