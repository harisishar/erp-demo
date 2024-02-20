package com.app.crmapp.Common;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.crmapp.R;

public class ToastCustom extends AppCompatActivity {
    private static Toast toast;

    /******** create toast with success message********/
    public  static void toastSuccess(Activity activity, String message) {
        toast = Toast.makeText(activity,message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        //inflate view
        View custom_view = activity.getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText(message);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(activity.getResources().getColor(R.color.green_500));

        toast.setView(custom_view);
        toast.show();
    }

    /******** toast with info message displayed ********/
    public static void toastInfo(Activity activity, String message) {
        toast = Toast.makeText(activity,message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        //inflate view
        View custom_view = activity.getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText(message);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_error_outline);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(activity.getResources().getColor(R.color.blue_500));

        toast.setView(custom_view);
        toast.show();
    }

    /******** toast with error  message displayed ********/
    public static void toastError(Activity activity, String message) {
        toast = Toast.makeText(activity,message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        //inflate view
        View custom_view = activity.getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText(message);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(activity.getResources().getColor(R.color.red_600));

        toast.setView(custom_view);
        toast.show();
    }
}
