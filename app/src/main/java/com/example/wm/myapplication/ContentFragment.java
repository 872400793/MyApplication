package com.example.wm.myapplication;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment
{
    private TextView text_content;

    public ContentFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        text_content = (TextView)view.findViewById(R.id.text_content);
        return view;
    }

    public void ChangeText(String value)
    {
        text_content.setText(value);
    }
}
