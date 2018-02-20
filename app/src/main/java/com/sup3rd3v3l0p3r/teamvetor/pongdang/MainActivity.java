package com.sup3rd3v3l0p3r.teamvetor.pongdang;

        import android.content.ClipData;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.TextView;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;
        import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
    }
    public interface HangangService {
        @GET("/")
        Call<getHangangTempResult> items();
    }
    public void getHangangTemp() {
        Log.i("NOTI", "한강온도");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hangang.dkserver.wo.tc")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HangangService service = retrofit.create(HangangService.class);

        Call<getHangangTempResult> items = (Call<getHangangTempResult>) service.items();
        items.enqueue(new Callback<getHangangTempResult>() {
            @Override
            public void onResponse(Call<getHangangTempResult> call, Response<getHangangTempResult> response) {
                textView.setText("현재 한강온도는 " + response.body().getTemp() + "°C 입니다.\nlast update : " + response.body().getTime());
            }

            List<ClipData.Item> list;

            @Override
            public void onFailure(Call<getHangangTempResult> call, Throwable throwable) {
                textView.setText("정보를 불러오는데 실패하였습니다.");
            }
        });
    }
}
