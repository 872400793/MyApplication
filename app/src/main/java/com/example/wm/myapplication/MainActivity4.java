package com.example.wm.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity
{
    private TextView text_show;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        text_show = (TextView) findViewById(R.id.text_show);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    /**
     * 下载
     */
    public void onClick_downLoad(View view)
    {
        new MyAsyncTask(this)
                .execute("http://cdnq.duitang.com/uploads/item/201409/18/20140918231127_LZQjr.png");
    }

    /**
     * 通过AsyncTask实现异步任务
     */
    private static class MyAsyncTask extends AsyncTask<String, Integer, Integer>
    {
        private MainActivity4 activity;

        public MyAsyncTask(MainActivity4 activity)
        {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            activity.text_show.setText("准备下载。。。");
            activity.progressBar.setProgress(0);
        }

        @Override
        protected Integer doInBackground(String... params)
        {
            try
            {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 获取文件大小
                int size = conn.getContentLength();
                // 设置进度条最大值
                publishProgress(0, size);
                // 每次读取的字节数
                byte[] bytes = new byte[10];
                int len = -1;
                // 读取字节流
                InputStream in = conn.getInputStream();
                // 写入本地文件流
                FileOutputStream out = new FileOutputStream(new File("/sdcard/1.png"));

                while (-1 != (len = in.read(bytes)))
                {
                    out.write(bytes, 0, len);
                    // 更新进度条
                    publishProgress(1, len);

                    out.flush();
                }

                out.close();
                in.close();
            }
            catch (Exception e)
            {
                return -1;
            }
            return 200;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            switch (values[0])
            {
                case 0:
                    activity.progressBar.setMax(values[1]);
                    break;
                case 1:
                    activity.progressBar.incrementProgressBy(values[1]);
                    break;
            }
        }

        @Override
        protected void onPostExecute(Integer integer)
        {
            super.onPostExecute(integer);

            if (200 == integer)
            {
                activity.text_show.setText("下载完成");
            }
            else
            {
                activity.text_show.setText("下载失败");
            }
        }
    }

    /**
     * XML解析
     *
     * @param view
     */
    public void onClick_pull(View view)
    {
        List<People> list = new ArrayList<>();
        People people = null;

        XmlPullParser pull = Xml.newPullParser();
        try
        {
            InputStream in = getResources().openRawResource(R.raw.people);
            pull.setInput(in, "UTF-8");
            /**
             * 获取当前的事件类型
             * 读到xml的声明返回数字0    START_DOCUMENT
             * 读到xml的结束返回数字1    END_DOCUMENT
             * 读到xml的开始标签返回数字2 START_TAG
             * 读到xml的结束标签返回数字3 END_TAG
             * 读到xml的文本返回数字4    TEXT
             */
            int eventType = pull.getEventType();

            while (XmlPullParser.END_DOCUMENT != eventType)
            {
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        String tag = pull.getName();
                        if ("people".equals(tag))
                        {
                            people = new People();
                            people.setId(pull.getAttributeValue(null, "id"));
                        }
                        else if ("name".equals(tag))
                        {
                            people.setName(pull.nextText());
                        }
                        else if ("sex".equals(tag))
                        {
                            people.setSex(pull.nextText());
                        }
                        else if ("age".equals(tag))
                        {
                            people.setAge(Integer.parseInt(pull.nextText()));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("people".equals(pull.getName()))
                        {
                            list.add(people);
                        }
                        break;
                }

                // 获取下一个事件类型
                eventType = pull.next();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // 输出解析结果
        for (People p : list)
        {
            System.out.println(p.getId() + "," + p.getName() + "," + p.getSex() + "," + p.getAge());
        }
    }

    public void onClick_reader2json(View view)
    {
        List<People> list = new ArrayList<>();
        People p1 = new People();
        p1.setId("1");
        p1.setName("亚当");
        p1.setAge(20);
        p1.setSex("男");

        People p2 = new People();
        p2.setId("2");
        p2.setName("夏娃");
        p2.setAge(18);
        p2.setSex("女");

        list.add(p1);
        list.add(p2);

        JSONArray jsonArray = new JSONArray();
        try
        {
            for (People p : list)
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", p.getId());
                jsonObject.put("name", p.getName());
                jsonObject.put("age", p.getAge());
                jsonObject.put("sex", p.getSex());

                jsonArray.put(jsonObject);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(jsonArray);
    }

    public void onClick_parseJson(View view)
    {
        String json = "[{\"id\":\"1\",\"sex\":\"男\",\"age\":20,\"name\":\"亚当\"},{\"id\":\"2\",\"sex\":\"女\",\"age\":18,\"name\":\"夏娃\"}]";

        JsonReader jsonReader = new JsonReader(new StringReader(json));

        List<People> list = new ArrayList<>();

        try
        {
            // 开始解析数组
            jsonReader.beginArray();

            while (jsonReader.hasNext())
            {
                People people = new People();

                // 开始解析对象
                jsonReader.beginObject();

                while (jsonReader.hasNext())
                {
                    String name = jsonReader.nextName();
                    if("id".equals(name))
                    {
                        people.setId(jsonReader.nextString());
                    }
                    else if("sex".equals(name))
                    {
                        people.setSex(jsonReader.nextString());
                    }
                    else if("age".equals(name))
                    {
                        people.setAge(jsonReader.nextInt());
                    }
                    else if("name".equals(name))
                    {
                        people.setName(jsonReader.nextString());
                    }
                }

                // 结束解析对象
                jsonReader.endObject();

                list.add(people);
            }

            // 结束解析数组
            jsonReader.endArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // 输出解析结果
        for (People p : list)
        {
            System.out.println(p.getId() + "," + p.getName() + "," + p.getSex() + "," + p.getAge());
        }
    }

    public void onClick_gson2json(View view)
    {
        List<People> list = new ArrayList<>();
        People p1 = new People();
        p1.setId("1");
        p1.setName("亚当");
        p1.setAge(20);
        p1.setSex("男");

        People p2 = new People();
        p2.setId("2");
        p2.setName("夏娃");
        p2.setAge(18);
        p2.setSex("女");

        list.add(p1);
        list.add(p2);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<People>>(){}.getType();
        String json = gson.toJson(list,type);
        System.out.println(json);
    }

    public void onClick_gsonParseJson(View view)
    {
        String json =
                "[{\"id\":\"1\",\"sex\":\"男\",\"age\":20,\"name\":\"亚当\"},{\"id\":\"2\",\"sex\":\"女\",\"age\":18,\"name\":\"夏娃\"}]";

        JsonReader jsonReader = new JsonReader(new StringReader(json));

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<People>>(){}.getType();
        List<People> list = gson.fromJson(json,type);

        // 输出解析结果
        for (People p : list)
        {
            System.out.println(p.getId() + "," + p.getName() + "," + p.getSex() + "," + p.getAge());
        }
    }
}
