package com.arkadiy.enter.smp3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogCloseTask extends AppCompatDialogFragment {
    private EditText commentTaskEditText;
    private DialogCloseTaskListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dailog,null);
        builder.setView(view).setTitle("Task name: "+getTag())
                .setNegativeButton("CENCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("CLOSE TASK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tmpStr = commentTaskEditText.getText().toString().trim();
                        listener.applyText(tmpStr , getTag());

                    }
                });
        commentTaskEditText = view.findViewById(R.id.commentTask_EditText);

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogCloseTaskListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement DialogCloseTaskLostener");
        }
    }

    public interface DialogCloseTaskListener{
        void applyText(String reason , String tag );
    }
}

