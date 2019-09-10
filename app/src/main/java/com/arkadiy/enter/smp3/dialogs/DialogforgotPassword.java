package com.arkadiy.enter.smp3.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.arkadiy.enter.smp3.R;

@SuppressLint("ValidFragment")
public class DialogforgotPassword extends AppCompatDialogFragment  {
    private EditText forgotPasswordTaskEditText;
    private DialogforgotPassword.DialogforgotPasswordListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_forgot_password,null);
        builder.setView(view).setTitle("User Name "+getTag())
                .setNegativeButton("CENCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = forgotPasswordTaskEditText.getText().toString();
                        listener.applyText(email);

                    }
                });
        forgotPasswordTaskEditText = view.findViewById(R.id.forgotPasswordTask_EditText);

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogforgotPasswordListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement DialogCloseTaskListener");
        }
    }

    public interface DialogforgotPasswordListener{
        void applyText(String email );
    }

}
