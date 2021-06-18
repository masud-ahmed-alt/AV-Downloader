package www.rb.allvideodownload.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import www.rb.allvideodownload.R;
import www.rb.allvideodownload.adapter.WhatsappAdapter;
import www.rb.allvideodownload.databinding.FragmentImageBinding;
import www.rb.allvideodownload.model.WhatsappStatusModel;

public class VideosFragment extends Fragment {

    private FragmentImageBinding binding;
    private ArrayList<WhatsappStatusModel> list;
    private WhatsappAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_image,container,false);

        list = new ArrayList<>();
        getData();

        binding.refresh.setOnRefreshListener(()->{
            list = new ArrayList<>();
            getData();
            binding.refresh.setRefreshing(false);
        });

        return binding.getRoot();
    }
    private void getData() {
        WhatsappStatusModel model;

        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp/Media/.statuses";
        File targetDirectory = new File(targetPath);
        File[] allFiles = targetDirectory.listFiles();

        String targetPathBusinnes = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp Business/Media/.statuses";
        File targetDirectoryBusiness = new File(targetPathBusinnes);
        File[] allFilesBusiness = targetDirectoryBusiness.listFiles();

        Arrays.sort(allFiles,((o1, o2) -> {
            if (o1.lastModified()>o2.lastModified()) return -1;
            else if (o1.lastModified()<o2.lastModified()) return +1;
            else return 0;
        }));
        for (int i =0; i<allFiles.length;i++){
            File file = allFiles[i];
            if (Uri.fromFile(file).toString().endsWith(".mp4")){
                model = new WhatsappStatusModel("whats"+i,
                        Uri.fromFile(file),
                        allFiles[i].getAbsolutePath(),
                        file.getName());
                list.add(model);

            }
        }
// For Whatsapp Business
        Arrays.sort(allFilesBusiness,((o1, o2) -> {
            if (o1.lastModified()>o2.lastModified()) return -1;
            else if (o1.lastModified()<o2.lastModified()) return +1;
            else return 0;
        }));
        for (int i =0; i<allFilesBusiness.length;i++){
            File file = allFilesBusiness[i];
            if (Uri.fromFile(file).toString().endsWith(".mp4")){
                model = new WhatsappStatusModel("whatsBusiness"+i,
                        Uri.fromFile(file),
                        allFilesBusiness[i].getAbsolutePath(),
                        file.getName());
                list.add(model);

            }
        }

        adapter = new WhatsappAdapter(list,getActivity());
        binding.whatsappRec.setAdapter(adapter);
    }

}