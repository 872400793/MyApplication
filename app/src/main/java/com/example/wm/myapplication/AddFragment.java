package com.example.wm.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddFragment extends DialogFragment
{
    private AddFragmentListener fragmentListener;

    public static AddFragment getInstance()
    {
        AddFragment addFragment = new AddFragment();
        return addFragment;
    }

    public static interface AddFragmentListener
    {
        public void add(Dog dog);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            fragmentListener = (AddFragmentListener) activity;
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
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add, null);

        return new AlertDialog.Builder(getActivity()).setView(view).setTitle("新增")
                .setPositiveButton("保存", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EditText name = (EditText) view.findViewById(R.id.edit_name);
                        EditText age = (EditText) view.findViewById(R.id.edit_age);
                        fragmentListener.add(new Dog(name.getText().toString(),
                                Integer.parseInt(age.getText().toString())));
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }).create();
    }
}
