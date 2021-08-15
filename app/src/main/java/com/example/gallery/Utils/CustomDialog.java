package com.example.gallery.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gallery.R;

public class CustomDialog extends DialogFragment {

    private String filePath;
    public CustomDialog() {
    }

    public CustomDialog(String filePath) {
        this.filePath = filePath;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Xác nhận !");
        builder.setMessage("Bạn thực sự muốn xóa hình ảnh ?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });


        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

         return  builder.create();

    }

    public void show(Context context) {

    }
}
