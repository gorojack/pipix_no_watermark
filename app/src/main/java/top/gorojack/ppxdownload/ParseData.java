package top.gorojack.ppxdownload;

import org.json.*;
import android.widget.*;
import android.content.*;

public class ParseData
{

    public static String getItemData(Context context , String json){

        String item_data_url="";
        int TYPE = getItemType(context,json);

        if(TYPE==2){
            item_data_url = getVideoUrl(context,json);
        }else if(TYPE==1){
            item_data_url = getPictureUrl(context,json);
        }else {
            Toast.makeText(context,"获取失败",Toast.LENGTH_SHORT).show();
        }
        return  item_data_url;
    }

    public static String getCellData(Context context , String json){

        String cell_data_url="";
        int TYPE = getType(context,json);

        if (TYPE==3){
            cell_data_url = getCommentVideoUrl(context,json);
        }else if(TYPE==2){
            cell_data_url = getCommentPictureUrl(context,json);
        }else {
            Toast.makeText(context,"获取失败",Toast.LENGTH_SHORT).show();
        }

        return  cell_data_url;

    }

    //      获取视频帖子的链接       //
	public static String getVideoUrl(Context context , String json){

        String video_url="";

        try{
            JSONObject jsonObject = new JSONObject(json);
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
            video_url = jsonObject4.getString("url");
        }catch(JSONException e){
            e.printStackTrace();
        }

        return video_url;
	}

    //      获取图片帖子的链接       //
	public static String getPictureUrl(Context context , String json){
		
		String picture_url="";

        try {

            JSONObject jsonObject10 = new JSONObject(json);
            String img_data_json = jsonObject10.getString("data");
            JSONObject jsonObject11 = new JSONObject(img_data_json);
            String img_item_json = jsonObject11.getString("item");
            JSONObject jsonObject12 = new JSONObject(img_item_json);
            String img_cover_json = jsonObject12.getString("cover");
            JSONObject jsonObject13 = new JSONObject(img_cover_json);
            String img_url_list_json = jsonObject13.getString("url_list");
            img_url_list_json = img_url_list_json.replace("[", "");
            img_url_list_json = img_url_list_json.replace("]", "");
            JSONObject jsonObject14 = new JSONObject(img_url_list_json);
            String img_url = jsonObject14.getString("url");
            picture_url = img_url;
        } catch (JSONException e) {
            e.printStackTrace();
        }

		return picture_url;
	}

    private static int getItemType(Context context , String json){

        int item_type = 0;

        try {
            JSONObject jsonObject1001 = new JSONObject(json);
            String data_json = jsonObject1001.getString("data");
            JSONObject jsonObject1002 = new JSONObject(data_json);
            String item_json = jsonObject1002.getString("item");
            JSONObject jsonObject1003 = new JSONObject(item_json);
            String item_item_type = jsonObject1003.getString("item_type");
            item_type = Integer.parseInt(item_item_type);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return item_type;
    }

    public static String getCommentVideoUrl(Context context , String json){

        String commentvideourl="";

        try {
            JSONObject jsonObject = new JSONObject(json);
            String data_json = jsonObject.getString("data");
            JSONObject jsonObject1 = new JSONObject(data_json);
            String comment_json = jsonObject1.getString("comment");
            JSONObject jsonObject2 = new JSONObject(comment_json);
            String video_json = jsonObject2.getString("video");
            JSONObject jsonObject3 = new JSONObject(video_json);
            String url_list = jsonObject3.getString("url_list");
            url_list = url_list.replace("[","");
            url_list = url_list.replace("]","");
            JSONObject jsonObject4 = new JSONObject(url_list);
            String url = jsonObject4.getString("url");
            commentvideourl = url;
        }catch (JSONException e){
            e.printStackTrace();
        }

        return commentvideourl;

    }

    public static String getCommentPictureUrl(Context context , String json){

        String commentpictureurl="";

        try {
            JSONObject jsonObject101 = new JSONObject(json);
            String data_json = jsonObject101.getString("data");
            JSONObject jsonObject102 = new JSONObject(data_json);
            String comment_json = jsonObject102.getString("comment");
            JSONObject jsonObject103 = new JSONObject(comment_json);
            String cover_json = jsonObject103.getString("cover");
            JSONObject jsonObject104 = new JSONObject(cover_json);
            String url_list_json = jsonObject104.getString("url_list");
            url_list_json = url_list_json.replace("[","");
            url_list_json = url_list_json.replace("]","");
            JSONObject jsonObject105 = new JSONObject(url_list_json);
            String url = jsonObject105.getString("url");
            commentpictureurl=url;
        }catch (JSONException e){
            e.printStackTrace();
        }

        return commentpictureurl;

    }

    private static int getType(Context context , String json){

        int type = 0;

        try {
            JSONObject jsonObject1004 = new JSONObject(json);
            String data_json = jsonObject1004.getString("data");
            JSONObject jsonObject1005 = new JSONObject(data_json);
            String comment_json = jsonObject1005.getString("comment");
            JSONObject jsonObject1006 = new JSONObject(comment_json);
            String type_json = jsonObject1006.getString("type");
            type = Integer.parseInt(type_json);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return type;
    }

    public static String getDouyinVideo(String json){

        String videoUrl = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            String url_list = jsonObject.getString("url_list");
            url_list = url_list.replace("[","");
            url_list = url_list.replace("]","");
            url_list = url_list.replace("\\","");
            url_list = url_list.replace("\"","");
            url_list = url_list.replace("playwm","play");

            videoUrl = url_list;
        }catch (JSONException e){
            e.printStackTrace();
        }

        return videoUrl;
    }

}