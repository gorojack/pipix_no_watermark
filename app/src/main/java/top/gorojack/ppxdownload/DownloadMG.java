package top.gorojack.ppxdownload;
import android.app.*;
import android.os.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import android.content.*;
import android.net.*;
import android.widget.*;

public class DownloadMG
{
	
	public static void downloadVideo(Context context , String url , String fileName){

		DownloadManager downloadManager;
		long id;

        boolean isvideo=url.contains("video");
        boolean isDouyin=url.contains("ppx");

        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
        if (isDouyin){
            request.setTitle("皮皮虾"+fileName);
        }else {
            request.setTitle("抖音"+fileName);
        }
        request.setAllowedOverRoaming(true);

        if(isvideo){
            if (isDouyin) {
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ppx" + fileName + ".mp4");
            }else {
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "douyin" + fileName + ".mp4");
            }
        }else {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS ,"ppx"+fileName+".jpeg" ) ;
        }
        downloadManager= (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        id = downloadManager.enqueue(request);
		Toast.makeText(context,"文件正在被下载到/Download/",Toast.LENGTH_SHORT).show();
	}

}
