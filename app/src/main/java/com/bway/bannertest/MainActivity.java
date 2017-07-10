package com.bway.bannertest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private String urlPath = "http://api.kkmh.com/v1/topic_new/discovery_list?gender=0&sa_event=eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3ODM5MDM5MzE1LCJwcm9wZXJ0aWVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjIuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tRmluZENhdGVnb3J5VGFiTmFtZSI6IuWFqOmDqCIsIklzQXV0b0xvYWQiOmZhbHNlLCIkbGliX3ZlcnNpb24iOiIxLjYuMzQiLCIkbmV0d29ya190eXBlIjoiV0lGSSIsIiR3aWZpIjp0cnVlLCIkbWFudWZhY3R1cmVyIjoic2Ftc3VuZyIsIkZyb21GaW5kVGFiTmFtZSI6IuaOqOiNkCIsIiRzY3JlZW5faGVpZ2h0Ijo1NzYsIkNhdGVnb3J5Ijoi5peg5rOV6I635Y-WIiwiSG9tZXBhZ2VVcGRhdGVEYXRlIjowLCJQcm9wZXJ0eUV2ZW50IjoiUmVhZEZpbmRQYWdlIiwiRmluZFRhYk5hbWUiOiLmjqjojZAiLCJhYnRlc3RfZ3JvdXAiOjQ2LCIkc2NyZWVuX3dpZHRoIjoxMDI0LCJGaW5kQ2F0ZWdvcnlUYWJOYW1lIjoi5YWo6YOoIiwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNNQ0MiLCIkbW9kZWwiOiJHVC1QNTIxMCIsIiRhcHBfdmVyc2lvbiI6IjMuOC4xIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo5MDUxMDQyNzYzNzU1MTA5Iiwib3JpZ2luYWxfaWQiOiJBOjkwNTEwNDI3NjM3NTUxMDkiLCJldmVudCI6IlJlYWRGaW5kUGFnZSJ9\n";

    private List<MyData.DataBean.InfosBean.BannersBean> beanList = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @ViewInject(R.id.banner)
    private Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);


        RequestParams params = new RequestParams(urlPath); // 网址(请替换成实际的网址)

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                MyData myData = gson.fromJson(result, MyData.class);
                beanList.addAll(myData.getData().getInfos().get(0).getBanners());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                for (int i = 0; i < beanList.size(); i++) {
                    images.add(beanList.get(i).getPic());
                    titles.add("" + i);
                }

                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                banner.start();

            }
        });

    }
    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

        }

    }
}
