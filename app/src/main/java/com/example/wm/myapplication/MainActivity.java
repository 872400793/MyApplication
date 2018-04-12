package com.example.wm.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity implements TitleFragment.MyListener
{
    private TitleFragment titleFragment;
    private ContentFragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleFragment = new TitleFragment();
        contentFragment = new ContentFragment();

    }

    public void addA(View view)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.li_title, titleFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    public void addB(View view)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.li_content, contentFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void change(String value)
    {
        contentFragment.ChangeText(value);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(0 == getFragmentManager().getBackStackEntryCount())
            {
                finish();
            }
            else
            {
                getFragmentManager().popBackStack();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
