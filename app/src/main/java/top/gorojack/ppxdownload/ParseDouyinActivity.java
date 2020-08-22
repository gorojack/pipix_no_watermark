package top.gorojack.ppxdownload;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParseDouyinActivity extends AppCompatActivity implements View.OnClickListener{

    AlertDialog.Builder builder;
    TextView mTextView;
    EditText mEditText;
    Button mButton;
    Button mDownButton;
    Button mClearButton;
    WebView mWebView;
    Context mContext = ParseDouyinActivity.this;

    private static final int SUCCEED = 993;
    private static final int FALL = 814;
    private static final int URL_SUCCEED = 1;
    private static final int URL_FALL=2;

    String parseApi = "http://47.93.39.209/?url=";
    String douyinApi = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCEED:
                    String source = (String) msg.obj;
                    initView();
                    String itemId = source.substring(0,source.indexOf("\\/?region="));
                    itemId = itemId.substring(itemId.indexOf("video\\/")+7);
                    getVideoJson(itemId);
                    mTextView.setText(itemId);

                    break;
                case FALL:

                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handler_videourl = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case URL_SUCCEED:
                    String source = (String) msg.obj;
                    initView();

                    String videoJson = source.substring(source.indexOf("play_addr")+11);
                    final String videoUrl;
                    mTextView.setText(videoJson);

                    videoUrl = ParseData.getDouyinVideo(videoJson);
                    mTextView.setText(videoUrl);
                    mWebView.loadUrl(videoUrl);

                    mDownButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String videoId;
                            videoId = videoUrl.substring(videoUrl.indexOf("video_id=")+9);
                            videoId = videoId.substring(0,videoId.indexOf("&ratio="));
                            DownloadMG.downloadVideo(mContext,videoUrl,videoId);
                        }
                    });

                    break;
                case URL_FALL:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_douyin);

        initView();
        mButton.setOnClickListener(this);
        mClearButton.setOnClickListener(this);
        mDownButton.setOnClickListener(this);

    }

    private void initView(){
        mTextView = (TextView)findViewById(R.id.headtext);
        mEditText = (EditText)findViewById(R.id.douyin_edittext);
        mButton = (Button)findViewById(R.id.douyin_parsebt);
        mDownButton = (Button)findViewById(R.id.douyin_download);
        mClearButton = (Button)findViewById(R.id.douyin_clear);
        mWebView= (WebView) findViewById(R.id.douyin_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    private void getShareId(String share_url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = new Request.Builder().url(parseApi+share_url).removeHeader("User-Agent").addHeader("User-Agent","Mozilla/5.0 (Linux; Android 10; MIX 2S) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.117 Mobile").build();
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

    private void getVideoJson(String share_id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = new Request.Builder().url(douyinApi+share_id).removeHeader("User-Agent").addHeader("User-Agent","Mozilla/5.0 (Linux; Android 10; MIX 2S) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.117 Mobile").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler_videourl.sendEmptyMessage(URL_FALL);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message obtain = Message.obtain();
                obtain.obj = string;
                obtain.what = URL_SUCCEED;
                handler_videourl.sendMessage(obtain);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.douyin_parsebt:
                String shareUrl = mEditText.getText().toString();
                shareUrl = shareUrl.substring(shareUrl.indexOf("http"));
                if (shareUrl.contains("复制")){
                    shareUrl = shareUrl.substring(0,shareUrl.indexOf("复制"));
                    mEditText.setText(shareUrl);
                    getShareId(shareUrl);
                    Toast.makeText(this,shareUrl,Toast.LENGTH_SHORT).show();
                }else {
                    mEditText.setText(shareUrl);
                    getShareId(shareUrl);
                    Toast.makeText(this,shareUrl,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.douyin_clear:
                mEditText.setText("");
                break;
            case R.id.douyin_download:

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_about:
                showAbout();
                break;
            case R.id.menu_douyin:
                //
                Toast.makeText(mContext,"已是当前页面",Toast.LENGTH_SHORT).show();
                //
                break;
            case R.id.menu_juanzeng:
                //
                showJZDialog();
                //
                break;
        }

        return true;
    }

    private void showAbout() {

        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher_round).setTitle("关于软件")
                .setMessage("作者皮皮虾：芒K熏12138\n"+
                        "作者抖音：gorojack（很少很少刷抖音）\n"+
                        "作者微信：sun18184076425\n"+
                        "作者QQ：1937047112\n"+
                        "此软件永久免费使用\n"+
                        "请勿用于商业用途\n"+
                        "喜欢的话可以捐赠作者\n"+
                        "代码实在是太乱了（捂脸）\n"+
                        "更多功能正在开发中...");
        builder.create().show();
    }

    public void showJZDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ParseDouyinActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(ParseDouyinActivity.this,R.layout.dialog_jz,null);
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final WebImageView mWebImageWX = view.findViewById(R.id.skm_wx);
        final WebImageView mWebImageZFB = view.findViewById(R.id.skm_zfb);
        mWebImageWX.setImageURL("http://47.93.39.209/skm_wx.jpg");
        mWebImageZFB.setImageURL("http://47.93.39.209/skm_zfb.jpg");
    }
}
