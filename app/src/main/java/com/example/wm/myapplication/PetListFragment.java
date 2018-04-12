package com.example.wm.myapplication;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class PetListFragment extends Fragment
{
    private MyAdapter adapter;
    private PetListFragmentListener fragmentListener;

    public static PetListFragment getInstance()
    {
        PetListFragment fragment = new PetListFragment();
        return fragment;
    }

    public static interface PetListFragmentListener
    {
        // 显示列表界面
        public void showPetListFragment();
        // 显示更新界面
        public void showUpdateFragment(int id);

        public void delete(int id);

        public List<Dog> searchAll();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            fragmentListener = (PetListFragmentListener) activity;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        fragmentListener = null;
    }

    /**
     * 创建列表长按菜单项
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("删除/修改");
        getActivity().getMenuInflater().inflate(R.menu.list_menu, menu);
    }

    /**
     * 点击列表长按菜单项
     */
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.menu_delete:
                AdapterView.AdapterContextMenuInfo info =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                TextView text_id = (TextView) info.targetView.findViewById(R.id.text_id);
                fragmentListener.delete(Integer.parseInt(text_id.getText().toString()));
                break;
            case R.id.menu_update:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                text_id = (TextView) info.targetView.findViewById(R.id.text_id);
                fragmentListener.showUpdateFragment(Integer.parseInt(text_id.getText().toString()));
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_show_pet, container, false);
        ListView list = (ListView) view.findViewById(R.id.list);

        // 注册菜单
        registerForContextMenu(list);

        adapter = new MyAdapter(getActivity(), fragmentListener.searchAll());
        list.setAdapter(adapter);
        return view;
    }

    public void updateList()
    {
        adapter.setList(fragmentListener.searchAll());
        adapter.notifyDataSetChanged();
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
