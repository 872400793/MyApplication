package com.example.wm.myapplication;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity6 extends AppCompatActivity implements PetListFragment.PetListFragmentListener, AddFragment.AddFragmentListener, UpdateFragment.UpdateFragmentListener
{
    private DataBaseAdapter dbAdapter;
    private PetListFragment petListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        dbAdapter = new DataBaseAdapter(this);

        showPetListFragment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (1 == getFragmentManager().getBackStackEntryCount())
            {
                finish();
            }
            else
            {
                // 出栈
                getFragmentManager().popBackStack();
            }
            // 这个返回值很重要哦
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 创建菜单项：添加
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu6, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 点击菜单项：添加
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.menu_add:
                AddFragment.getInstance().show(getFragmentManager(), null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示列表界面
     */
    @Override
    public void showPetListFragment()
    {
        petListFragment = PetListFragment.getInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_main, petListFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * 显示修改界面
     */
    @Override
    public void showUpdateFragment(int id)
    {
        UpdateFragment updateFragment = UpdateFragment.getInstance(id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_main, updateFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public List<Dog> searchAll()
    {
        return dbAdapter.searchAll();
    }

    @Override
    public void add(Dog dog)
    {
        dbAdapter.add(dog);
        petListFragment.updateList();
    }

    @Override
    public void delete(int id)
    {
        dbAdapter.delete(id);
        petListFragment.updateList();
    }

    @Override
    public void update(Dog dog)
    {
        dbAdapter.update(dog);
        petListFragment.updateList();
    }

    @Override
    public Dog searchById(int id)
    {
        return dbAdapter.searchById(id);
    }
}

