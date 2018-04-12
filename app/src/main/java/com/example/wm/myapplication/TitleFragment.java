package com.example.wm.myapplication;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment implements View.OnClickListener
{
    private MyListener listener;

    public TitleFragment()
    {
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        listener = (MyListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        view.findViewById(R.id.button).setOnClickListener(this);
        view.findViewById(R.id.button2).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button:
                listener.change("A");
                break;
            case R.id.button2:
                listener.change("B");
                break;
        }
    }

    public static interface MyListener
    {
        public void change(String value);
    }
}
