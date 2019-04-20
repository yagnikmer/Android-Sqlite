package com.mer.SqliteEx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SqliteActivity extends Activity {

    public static final String TAG = "SqliteExample";
    public Option opration;
    SqliteHelper SqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SqlHelper = new SqliteHelper(this);
    }

    public void display(View view) {
        opration = Option.DISPLAY;
        SqlHelper.display();
    }

    public void update(View view) {
        opration = Option.UPDATE;
        showDialog("Update");
    }

    public void delete(View view) {
        opration = Option.DELETE;
        showDialog("Delete");
    }

    public void add(View view) {
        opration = Option.ADD;
        showDialog("Add");
    }

    public void showDialog(String title) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.layout_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(title);
        dialog.setCancelable(false);
        final EditText name = view.findViewById(R.id.etComments);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strname = name.getText().toString();
                if (null != strname && strname.length() > 0) {
                    if (opration == Option.ADD)
                        SqlHelper.insert(strname);
                    if (opration == Option.DELETE)
                        SqlHelper.delete(strname);
                    if (opration == Option.UPDATE)
                        SqlHelper.update("yagnik", strname);
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    enum Option {
        ADD, DELETE, UPDATE, DISPLAY
    }
}
