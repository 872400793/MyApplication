package com.example.wm.myapplication;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class UpdateFragment extends Fragment implements View.OnClickListener
{
    private UpdateFragmentListener fragmentListener;
    private int id;
    private EditText edit_name;
    private EditText edit_age;

    public static UpdateFragment getInstance(int id)
    {
        UpdateFragment updateFragment = new UpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        updateFragment.setArguments(bundle);

        return updateFragment;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_cancel:
                getFragmentManager().popBackStack();
                break;
            case R.id.bt_ok:
                Dog dog = new Dog(id, edit_name.getText().toString(),
                        Integer.parseInt(edit_age.getText().toString()));

                fragmentListener.update(dog);

                getFragmentManager().popBackStack();
                break;
        }
    }

    public static interface UpdateFragmentListener
    {
        public void update(Dog dog);

        public Dog searchById(int id);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            fragmentListener = (UpdateFragmentListener) activity;
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        TextView text_id = (TextView) view.findViewById(R.id.text_id);
        edit_name = (EditText) view.findViewById(R.id.edit_name);
        edit_age = (EditText) view.findViewById(R.id.edit_age);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
        Dog dog = fragmentListener.searchById(id);
        text_id.setText(dog.getId() + "");
        edit_name.setText(dog.getName());
        edit_age.setText(dog.getAge() + "");
        bt_cancel.setOnClickListener(this);
        bt_ok.setOnClickListener(this);

        return view;
    }
}
