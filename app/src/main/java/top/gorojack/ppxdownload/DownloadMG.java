package top.gorojack.ppxdownload;
import android.app.*;
import android.os.*;
import java.util.*;
import android.content.*;
import android.net.*;
import android.widget.*;

public class DownloadMG
{
	
	public static void downloadVideo(Context context , String url){
		
		Calendar calendar = Calendar.getInstance();
		String time;
		DownloadManager downloadManager;
		long id;
		
		time = "";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        time = ""+year+month+day+hour+minute+second;

        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
        request.setTitle("皮皮虾"+time);
        request.setAllowedOverRoaming(true);

        boolean isvideo=url.contains("video");

        if(isvideo){
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS ,"ppx"+time+".mp4" ) ;
        }else {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS ,"ppx"+time+".jpeg" ) ;
        }
        downloadManager= (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        id = downloadManager.enqueue(request);
		Toast.makeText(context,"文件正在被下载到/Download/",Toast.LENGTH_SHORT).show();
	}
	
}
