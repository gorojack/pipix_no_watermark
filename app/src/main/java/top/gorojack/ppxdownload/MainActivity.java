package top.gorojack.ppxdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    WebView webView;
    Button button;
    Calendar calendar = Calendar.getInstance();
    String time;
    DownloadManager downloadManager;
    long id;
    EditText editText;

    private static final int SUCCEED = 993;
    private static final int FALL = 814;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCEED:
                    final String text = (String) msg.obj;

                    webView = (WebView)findViewById(R.id.webview);
                    button = (Button)findViewById(R.id.download_button);

                    try{
                        JSONObject jsonObject = new JSONObject(text);
                        String data_json = jsonObject.getString("data");
                        JSONObject jsonObject1 = new JSONObject(data_json);
                        String item_json = jsonObject1.getString("item");
                        JSONObject jsonObject2 = new JSONObject(item_json);
                        String origin_video_download = jsonObject2.getString("origin_video_download");
                        JSONObject jsonObject3 = new JSONObject(origin_video_download);
                        String origin_video_download_url_list = jsonObject3.getString("url_list");
                        origin_video_download_url_list = origin_video_download_url_list.replace("[","");
                        origin_video_download_url_list = origin_video_download_url_list.replace("]","");
                        JSONObject jsonObject4 = new JSONObject(origin_video_download_url_list);
                        final String origin_video_download_url_list_url = jsonObject4.getString("url");

                        textView.setText(origin_video_download_url_list_url);

                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                        webView.setVisibility(View.VISIBLE);
                        webView.getSettings().setUseWideViewPort(true);
                        webView.loadUrl(origin_video_download_url_list_url);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // 下载方法还没写呢  淦！
                                // 2020年7月20日09:21:28 写好了
                                download(origin_video_download_url_list_url);
                                Toast.makeText(MainActivity.this,"开始下载",Toast.LENGTH_SHORT).show();
                            }
                        });


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case FALL:
                    Toast.makeText(MainActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getJson("");


        editText = (EditText)findViewById(R.id.edittext);

    }

    private void getJson(String item_id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request.Builder url = builder.url("https://h5.pipix.com/bds/webapi/item/detail/?item_id=6839078384304134404&source=share");
        Request request = url.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(FALL);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message obtain = Message.obtain();
                obtain.obj = string;
                obtain.what = SUCCEED;
                handler.sendMessage(obtain);
            }
        });
    }

    private void download(String url) {
        time = "";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        time = ""+year+month+day+hour+minute+second;

        //创建request对象
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        //设置什么网络情况下可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏的标题
        request.setTitle("下载");
        //设置通知栏的message
        request.setDescription("正在下载视频.....");
        //设置漫游状态下是否可以下载
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,"ppx"+time+".mp4");
        //获取系统服务
        downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //进行下载
        id = downloadManager.enqueue(request);
    }

}
