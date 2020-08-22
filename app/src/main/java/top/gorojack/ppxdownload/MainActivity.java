package top.gorojack.ppxdownload;

import android.annotation.SuppressLint;
import android.app.*;
import android.os.*;
import android.widget.*;
import okhttp3.*;
import java.io.*;
import android.view.View.*;
import android.view.*;
import android.webkit.*;
import android.content.*;
import android.text.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
	
	AlertDialog.Builder builder;
	EditText mEditText;
	Button mButton;
	Button downloadButton;
	Button clearButton;
	TextView mTextView;
	WebView webView;
	Context context=MainActivity.this;
	private static final int SUCCEED = 993;
    private static final int FALL = 814;
	private static final int URL_SUCCEED = 1;
	private static final int URL_FALL=2;
	private static final int ISITEM=101;
	private static final int ISCELL=102;
	private static final int ISVIDEO=201;
	private static final int ISPICTURE=202;
	
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCEED:
                    String source = (String) msg.obj;

                    mTextView=(TextView)findViewById(R.id.textview);

                    boolean if_cell_id = source.contains("cell_id=");
                    if (if_cell_id){
						String cell_id;
						cell_id = source.substring(0,source.indexOf("&carrier_region="));
						cell_id = cell_id.substring(cell_id.indexOf("cell_id=")+8);

						getVideoJson("https://h5.pipix.com/bds/webapi/cell/detail/?cell_id="+cell_id+"&cell_type="+"8"+"&source=share",ISCELL);
					}else {
						String item_id;
						item_id = source.substring(0,source.indexOf("?"));
						item_id = item_id.substring(item_id.indexOf("item\\/")+6);

						getVideoJson("https://h5.pipix.com/bds/webapi/item/detail/?item_id="+item_id+"&source=share",ISITEM);
					}

                    break;
                case FALL:
                    Toast.makeText(MainActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
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
                    String fullJson = (String) msg.obj;
                    boolean ISCELL = msg.arg1==102;
                    final String url;

					downloadButton=(Button)findViewById(R.id.download_button);

					webView.getSettings().setJavaScriptEnabled(true);
					webView.getSettings().setPluginState(WebSettings.PluginState.ON);
					webView.setVisibility(View.VISIBLE);
					webView.getSettings().setUseWideViewPort(true);

					if(ISCELL){
						url=ParseData.getCellData(MainActivity.this,fullJson);
						webView.loadUrl(url);
						downloadButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								String id;
								id = url.substring(url.indexOf(".com/")+6);
								id = id.substring(0,id.indexOf("/"));
								DownloadMG.downloadVideo(context,url,id);
							}
						});
					}else {
						url=ParseData.getItemData(MainActivity.this,fullJson);
						webView.loadUrl(url);
						downloadButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								String id;
								id = url.substring(url.indexOf(".com/")+6);
								id = id.substring(0,id.indexOf("/"));
								DownloadMG.downloadVideo(context,url,id);
							}
						});
					}

                    break;
                case URL_FALL:
                    Toast.makeText(MainActivity.this, "获取视频链接失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		webView=(WebView)findViewById(R.id.webview);
		webView.loadUrl("file:////android_asset/web/index.html");
		mEditText=(EditText)findViewById(R.id.edittext);
		mButton=(Button)findViewById(R.id.getbutton);
		clearButton=(Button)findViewById(R.id.clearbutton);

		clearButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mEditText.setText("");
			}
		});
		
		mButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{
					
					if(TextUtils.isEmpty(mEditText.getText())){
						Toast.makeText(MainActivity.this,"链接为空",Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(MainActivity.this,"正在解析",Toast.LENGTH_SHORT).show();
						String share_url=mEditText.getText().toString();
						getShareId("http://47.93.39.209/?url="+share_url);
					}
				}
			});
		
    }
	
	private void getShareId(String share_url){
		OkHttpClient okHttpClient = new OkHttpClient();
		Request.Builder builder = new Request.Builder();
		Request request = new Request.Builder().url(share_url).removeHeader("User-Agent").addHeader("User-Agent","Mozilla/5.0 (Linux; Android 10; MIX 2S) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.117 Mobile").build();
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
	
	private void getVideoJson(String share_id , final int type){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
		Request request = new Request.Builder().url(share_id).removeHeader("User-Agent").addHeader("User-Agent","Mozilla/5.0 (Linux; Android 10; MIX 2S) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.117 Mobile").build();
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
					obtain.arg1 = type;
					handler_videourl.sendMessage(obtain);
				}
			});
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
				Intent intent = new Intent(MainActivity.this,ParseDouyinActivity.class);
				startActivity(intent);
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
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(MainActivity.this,R.layout.dialog_jz,null);
		dialog.setView(view);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		final WebImageView mWebImageWX = view.findViewById(R.id.skm_wx);
		final WebImageView mWebImageZFB = view.findViewById(R.id.skm_zfb);
		mWebImageWX.setImageURL("http://47.93.39.209/skm_wx.jpg");
		mWebImageZFB.setImageURL("http://47.93.39.209/skm_zfb.jpg");
	}

}
