package com.arkadiy.enter.smp3;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DialogDescriptionTask extends AppCompatDialogFragment {

    private TextView dialogDescriptionEditText;
    private DialogCloseTask.DialogCloseTaskListener listener;
    private String description;

    public DialogDescriptionTask(String description){
        this.description  = description;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dailog_description,null);
        builder.setView(view).setTitle("Task name: "+getTag())
                .setNegativeButton("CENCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialogDescriptionEditText = view.findViewById(R.id.descriptionTask_EditText);
        dialogDescriptionEditText.setText(description);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogCloseTask.DialogCloseTaskListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement DialogDescriptionTaskListener");
        }
    }

    public interface DialogCloseTaskListener{
        void applyText(String reason , String tag );
    }
}