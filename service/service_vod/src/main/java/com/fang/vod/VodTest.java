package com.fang.vod;

import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class VodTest {

    //    点播服务接入区域
    private static final String regionId = "cn-shanghai";

    public static DefaultAcsClient initVodClient(String accseeKeyId, String accessKeySecret) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accseeKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    public static void main(String[] args){
        UploadVideoRequest request = new UploadVideoRequest("","","","");


    }

    public static void getPlayAuth() {
        try {
            //        根据视频ID获取视频播放凭证
            DefaultAcsClient client = VodTest.initVodClient("LTAI4GFWAwzcbvz8yu8TJ1JN", "0cfm6mhu7Of6e9QnUH4ZgAeZFYWSGl");
//        船舰视频request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

//        设置id
            request.setVideoId("1854c4b360bc4e56a4564db47f3384ca");
            System.out.println(response.getPlayAuth());
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

    public static void getPlayUrl() {
        try {
            //        根据ID视频地址来查询视频
//        初始化对象
            DefaultAcsClient client = VodTest.initVodClient("LTAI4GFWAwzcbvz8yu8TJ1JN", "0cfm6mhu7Of6e9QnUH4ZgAeZFYWSGl");

//        创建获取视频地址request和response
            GetPlayInfoRequest request = new GetPlayInfoRequest();
//        GetPlayInfoResponse response = new GetPlayInfoResponse();

//        像request中设置视频ID
            request.setVideoId("1854c4b360bc4e56a4564db47f3384ca");

//        调用初始化对象中的方法 获取数据
            GetPlayInfoResponse response = null;

            response = client.getAcsResponse(request);


            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
