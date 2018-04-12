package com.example.wm.myapplication;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.HttpClient;

import java.util.List;

public class MainActivity7 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Dog>>
{
    private DataBaseAdapter dbAdapter;
    private MyAsyncTaskLoader loader;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        dbAdapter = new DataBaseAdapter(this);

        adapter = new MyAdapter(this, dbAdapter.searchAll());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);

    }

    public void onClick_add(View view)
    {
        dbAdapter.add(new Dog("doge",4));
        loader.onContentChanged();
    }

    @Override
    public Loader<List<Dog>> onCreateLoader(int id, Bundle args)
    {
        loader = new MyAsyncTaskLoader(this,dbAdapter);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Dog>> loader, List<Dog> data)
    {
        adapter.setList(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Dog>> loader)
    {
        adapter.setList(null);
    }

    /**
     * 自定义loader
     */
    private static class MyAsyncTaskLoader extends AsyncTaskLoader<List<Dog>>
    {
        private DataBaseAdapter dbAdapter;
        private List<Dog> data;

        public MyAsyncTaskLoader(Context context, DataBaseAdapter dbAdapter)
        {
            super(context);
            this.dbAdapter = dbAdapter;
        }

        /**
         * 在后台线程中执行，加载数据
         */
        @Override
        public List<Dog> loadInBackground()
        {
            data = dbAdapter.searchAll();
            return data;
        }

        @Override
        public void deliverResult(List<Dog> data)
        {
            if (isReset())
            {
                return;
            }
            if (isStarted())
            {
                super.deliverResult(data);
            }
        }

        @Override
        protected void onStartLoading()
        {
            if (null != data)
            {
                deliverResult(data);
            }

            if (takeContentChanged())
            {
                // 强制加载数据
                forceLoad();
            }

            super.onStartLoading();
        }
    }

    private static class MyAdapter extends BaseAdapter
    {
        private List<Dog> list;
        private Context context;

        public MyAdapter(Context context, List<Dog> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setList(List<Dog> list)
        {
            this.list = list;
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder myHolder;
            if (convertView == null)
            {
                myHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
                myHolder.text_id = (TextView) convertView.findViewById(R.id.text_id);
                myHolder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                myHolder.text_age = (TextView) convertView.findViewById(R.id.text_age);
                convertView.setTag(myHolder);
            }
            else
            {
                myHolder = (ViewHolder) convertView.getTag();
            }

            Dog dog = list.get(position);

            myHolder.text_id.setText(dog.getId() + "");
            myHolder.text_name.setText(dog.getName());
            myHolder.text_age.setText(dog.getAge() + "");

            return convertView;
        }

        private static class ViewHolder
        {
            public TextView text_id;
            public TextView text_name;
            public TextView text_age;
        }
    }
}
