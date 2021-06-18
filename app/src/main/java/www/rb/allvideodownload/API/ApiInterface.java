package www.rb.allvideodownload.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import www.rb.allvideodownload.model.InstaModel;

public interface ApiInterface {
    @GET("video")
        Call<InstaModel> getInfo(
                @Query("link") String link
    );
}
