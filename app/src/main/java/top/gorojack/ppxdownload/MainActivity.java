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

public class MainActivity extends AppCompatActivity
{
	
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
//                  source=source.replace("\\","");

                    mTextView=(TextView)findViewById(R.id.textview);

                    boolean if_cell_id = source.contains("cell_id=");
                    if (if_cell_id){
						String cell_id;
						cell_id = source.substring(0,source.indexOf("&carrier_region="));
						cell_id = cell_id.substring(cell_id.indexOf("cell_id=")+8);

						getVideoJson("https://h5.pipix.com/bds/webapi/cell/detail/?cell_id="+cell_id+"&cell_type="+"8"+"&source=share",ISCELL);
						//mTextView.setText("cell_id="+cell_id+"\ncell_type="+"8");
						//Toast.makeText(context,"正在获取评论内容",Toast.LENGTH_SHORT).show();
					}else {
						String item_id;
						item_id = source.substring(0,source.indexOf("?"));
						item_id = item_id.substring(item_id.indexOf("item\\/")+6);

						getVideoJson("https://h5.pipix.com/bds/webapi/item/detail/?item_id="+item_id+"&source=share",ISITEM);
						//mTextView.setText("item_id="+item_id);
						//Toast.makeText(context,"正在获取视频内容",Toast.LENGTH_SHORT).show();
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
                    //int ISCELL = msg.arg1;
                    boolean ISCELL = msg.arg1==102;
                    final String url;

					//webView=(WebView)findViewById(R.id.webview);
					downloadButton=(Button)findViewById(R.id.download_button);
					//mTextView=(TextView)findViewById(R.id.textview);

					webView.getSettings().setJavaScriptEnabled(true);
					webView.getSettings().setPluginState(WebSettings.PluginState.ON);
					webView.setVisibility(View.VISIBLE);
					webView.getSettings().setUseWideViewPort(true);

					if(ISCELL){
						url=ParseData.getCellData(MainActivity.this,fullJson);
						webView.loadUrl(url);
						//mTextView.setText(url);
						downloadButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								DownloadMG.downloadVideo(context,url);
							}
						});
					}else {
						url=ParseData.getItemData(MainActivity.this,fullJson);
						webView.loadUrl(url);
						//mTextView.setText(url);
						downloadButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								DownloadMG.downloadVideo(context,url);
							}
						});
					}

//					url=ParseData.getPictureCommentPictureUrl(MainActivity.this,fullJson);
//					webView.loadUrl(url);
//					mTextView.setText(url);downloadButton.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View view) {
//						DownloadMG.downloadVideo(context,url);
//						Toast.makeText(MainActivity.this,"开始下载",Toast.LENGTH_SHORT).show();
//						}
//					});

                    break;
                case URL_FALL:
                    Toast.makeText(MainActivity.this, "获取视频链接失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		webView=(WebView)findViewById(R.id.webview);
		webView.loadUrl("file:////android_asset/web/index.html");

		//webView.loadUrl("https://p9-ppx.byteimg.com/img/tos-cn-i-0000/14c4ed093cfb498ca316a96d192beb73~c5_500x500_q60.jpeg");
		//webView.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} img{height:100%25;} </style></head><body><img src='https://p9-ppx.byteimg.com/img/tos-cn-i-0000/14c4ed093cfb498ca316a96d192beb73~c5_500x500_q60.jpeg'/></body></html>" ,"text/html",  "UTF-8");

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
        }

        return true;
    }
	
	private void showAbout() {

        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher_round).setTitle("关于软件")
			.setMessage("作者皮皮虾：芒K熏12138\n"+
			"作者微信：sun18184076425\n"+
			"作者QQ：1937047112\n"+
			"此软件永久免费使用\n"+
			"请勿用于商业用途\n"+
			"喜欢的话可以捐赠作者\n"+
			"更多功能正在开发中...");
        builder.create().show();
    }
}
