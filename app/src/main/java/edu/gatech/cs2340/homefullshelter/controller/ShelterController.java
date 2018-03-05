package edu.gatech.cs2340.homefullshelter.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.model.Model;

/**
 * Created by abhinavtirath on 3/1/18.
 */

public class ShelterController {
    private View view;
    private Context context;
    public ShelterController(View v, final Context c) {
        view = v;
        context = c;
    }
//    public AlertDialog.Builder makeDialog() {
//        AlertDialog.Builder builder;
//        LayoutInflater mInflater = LayoutInflater.from(view.getContext());
//        builder = new AlertDialog.Builder(view.getContext());
//        builder.setTitle("Login")
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // continue with delete
//
//
//
//                        Model model = Model.getInstance();
//
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//
//                    }
//                })
//                .setView(mInflater.inflate(R.layout.alertdialog_search, null)).show();
//
//        return builder;
//    }
}

