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

	//      获取视频帖子中视频评论中的视频链接       //
	public static String getVideoCommentVideoUrl(Context context , String json){

	    String videocommentvideourl="";

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
            videocommentvideourl = url;
        }catch (JSONException e){
            e.printStackTrace();
        }

	    return videocommentvideourl;

    }


    //      获取视频帖子中图片评论中的图片链接       //
    public static String getVideoCommentPictureUrl(Context context , String json){

	    String videocommentpictureurl="";

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
            videocommentpictureurl=url;
        }catch (JSONException e){
	        e.printStackTrace();
        }

	    return videocommentpictureurl;

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

	//      获取图片帖子中视频评论的链接      //
	public static String getPictureCommentVideoUrl(Context context , String json){

	    String piccommentvideourl="";

	    try {
	        JSONObject jsonObject201 = new JSONObject(json);
	        String data_json = jsonObject201.getString("data");
	        JSONObject jsonObject202 = new JSONObject(data_json);
	        String comment_json = jsonObject202.getString("comment");
	        JSONObject jsonObject203 = new JSONObject(comment_json);
	        String video_json = jsonObject203.getString("video");
	        JSONObject jsonObject204 = new JSONObject(video_json);
	        String url_list_json = jsonObject204.getString("url_list");
	        url_list_json = url_list_json.replace("[","");
	        url_list_json = url_list_json.replace("]","");
	        JSONObject jsonObject205 = new JSONObject(url_list_json);
	        String url = jsonObject205.getString("url");
	        piccommentvideourl=url;
        }catch (JSONException e){
	        e.printStackTrace();
        }

	    return piccommentvideourl;

    }

    //      获取图片帖子中图片评论的链接          //
    public static String getPictureCommentPictureUrl(Context context , String json){

	    String picturecommentpictureurl="";

	    try {
	        JSONObject jsonObject301 = new JSONObject(json);
	        String data_json = jsonObject301.getString("data");
	        JSONObject jsonObject302 = new JSONObject(data_json);
	        String comment_json = jsonObject302.getString("comment");
	        JSONObject jsonObject303 = new JSONObject(comment_json);
	        String cover_json = jsonObject303.getString("cover");
	        JSONObject jsonObject304 = new JSONObject(cover_json);
	        String url_list_json = jsonObject304.getString("url_list");
	        url_list_json = url_list_json.replace("[","");
	        url_list_json = url_list_json.replace("]","");
	        JSONObject jsonObject305 = new JSONObject(url_list_json);
	        String url = jsonObject305.getString("url");
	        picturecommentpictureurl = url;
        }catch (JSONException e){
	        e.printStackTrace();
        }

	    return picturecommentpictureurl;
    }

    //后面的内容估计没用了

	public static String getItem(Context context , String json){

	    String itemurl="";
	    int TYPE;

            try {

                JSONObject jsonObject = new JSONObject(json);
                String data_json = jsonObject.getString("data");
                JSONObject jsonObject1 = new JSONObject(data_json);
                String item_json = jsonObject1.getString("item");
                JSONObject jsonObject2 = new JSONObject(item_json);
                String item_type = jsonObject2.getString("item_type");

                try {
                    TYPE = Integer.parseInt(item_type);

                    if (TYPE == 1) {
                        try {

                            Toast.makeText(context, "这是图片帖子链接", Toast.LENGTH_SHORT).show();
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

                            itemurl = img_url;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (TYPE == 2) {

                        Toast.makeText(context, "这是视频帖子链接", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject10 = new JSONObject(json);
                            String video_data_json = jsonObject10.getString("data");
                            JSONObject jsonObject11 = new JSONObject(video_data_json);
                            String video_item_json = jsonObject11.getString("item");
                            JSONObject jsonObject12 = new JSONObject(video_item_json);
                            String origin_video_download = jsonObject12.getString("origin_video_download");
                            JSONObject jsonObject13 = new JSONObject(origin_video_download);
                            String origin_video_download_url_list = jsonObject13.getString("url_list");
                            origin_video_download_url_list = origin_video_download_url_list.replace("[", "");
                            origin_video_download_url_list = origin_video_download_url_list.replace("]", "");
                            JSONObject jsonObject14 = new JSONObject(origin_video_download_url_list);
                            String url = jsonObject14.getString("url");

                            itemurl = url;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "获取视频链接失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "判断type失败", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show();
            }
	    return itemurl;
    }

    public static String getCell(Context context , String json){

	    String cellurl="";

	    int TYPE;

	    try{
            JSONObject jsonObject31 = new JSONObject(json);
            String cell_data_json = jsonObject31.getString("data");
            JSONObject jsonObject32 = new JSONObject(cell_data_json);
            String cell_comment_json = jsonObject32.getString("comment");
            JSONObject jsonObject33 = new JSONObject(cell_comment_json);
            String cell_item_json = jsonObject33.getString("item");
            JSONObject jsonObject34 = new JSONObject(cell_item_json);
            String cell_item_type = jsonObject34.getString("item_type");

            cellurl = cell_item_type;

            TYPE = Integer.parseInt(cell_item_type);

            if (TYPE==1){
                Toast.makeText(context,"这是图片评论链接",Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject51 = new JSONObject(json);
                    String data_json = jsonObject51.getString("data");
                    JSONObject jsonObject52 = new JSONObject(data_json);
                    String comment_json = jsonObject52.getString("comment");
                    JSONObject jsonObject53 = new JSONObject(comment_json);
                    String item_json = jsonObject53.getString("item");
                    JSONObject jsonObject54 = new JSONObject(item_json);
                    String cover_json = jsonObject54.getString("cover");
                    JSONObject jsonObject55 = new JSONObject(cover_json);
                    String url_list_json = jsonObject55.getString("url_list");
                    url_list_json = url_list_json.replace("[","");
                    url_list_json = url_list_json.replace("]","");
                    JSONObject jsonObject56 = new JSONObject(url_list_json);
                    String url = jsonObject56.getString("url");

                    cellurl = url;
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }else if(TYPE==2){
                Toast.makeText(context,"这是视频评论链接",Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject41 = new JSONObject(json);
                    String data_json = jsonObject41.getString("data");
                    JSONObject jsonObject42 = new JSONObject(data_json);
                    String comment_json = jsonObject42.getString("comment");
                    JSONObject jsonObject43 = new JSONObject(comment_json);
                    String video_json = jsonObject43.getString("video");
                    JSONObject jsonObject44 = new JSONObject(video_json);
                    String url_list = jsonObject44.getString("url_list");
                    url_list = url_list.replace("[","");
                    url_list = url_list.replace("]","");
                    JSONObject jsonObject45 = new JSONObject(url_list);
                    String url = jsonObject45.getString("url");
                    cellurl = url;
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(context,"获取视频链接失败",Toast.LENGTH_SHORT).show();
                }
            }

        }catch (JSONException e){
	        e.printStackTrace();
	        Toast.makeText(context,"获取失败了",Toast.LENGTH_SHORT).show();
        }

	    return cellurl;
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

}