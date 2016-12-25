package com.example.naor.RideWithMe;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Naor on 14/12/2016.
 */

public class Addtremp extends DialogFragment {
    public EditText name,phone,from,to,date,time,extra;
    public Button send_btn;
    public Addtremp.getDataFromDB listener;
    public String str_name,str_phone,str_from,str_to,str_date,str_time,str_extra,str_key;
    public _7Data sevenString;
    public Firebase firebase;
    public String send_time;
    public CustomAdapter ca;

    public Addtremp(){}


    public void setListener(Addtremp.getDataFromDB listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adddialog, null);

        //INITIALIZE DATA_BASE AND VIEWS
        firebase = new Firebase("https://ridewithme-c7f3a.firebaseio.com/");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        send_time = dateFormat.format(new Date());
        name = (EditText) v.findViewById(R.id.name);
        phone = (EditText) v.findViewById(R.id.phone);
        from = (EditText) v.findViewById(R.id.from);
        to = (EditText) v.findViewById(R.id.to);
        date = (EditText) v.findViewById(R.id.date);
        time = (EditText) v.findViewById(R.id.time);
        extra = (EditText) v.findViewById(R.id.extra);
        send_btn = (Button) v.findViewById(R.id.add);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = name.getText().toString();
                str_phone = phone.getText().toString();
                str_from = from.getText().toString();
                str_to = to.getText().toString();
                str_date = date.getText().toString();
                str_time = time.getText().toString();
                str_extra = extra.getText().toString();

                sevenString = new _7Data(str_name, str_phone, str_from, str_to, str_date, str_time, str_extra);
                if (name.length() == 0) {
                    name.requestFocus();
                    name.setHintTextColor(Color.RED);
                    name.setHint("מלא את שמך");
                    return;
                } else if (phone.length() != 10 || !check_phone_number(phone.getText().toString())) {
                    phone.requestFocus();
                    phone.setText("");
                    phone.setHintTextColor(Color.RED);
                    phone.setTextSize(15);
                    phone.setHint("מספר פלאפון לא תקין. נסה שוב");
                    return;
                } else if (from.length() == 0) {
                    from.requestFocus();
                    from.setHintTextColor(Color.RED);
                    from.setHint("מלא מוצא");
                    return;
                } else if (to.length() == 0) {
                    to.requestFocus();
                    to.setHintTextColor(Color.RED);
                    to.setHint("מלא יעד");
                    return;
                } else if (date.length() == 0) {
                    date.requestFocus();
                    date.setHintTextColor(Color.RED);
                    date.setHint("מלא תאריך");
                    return;
                } else if (time.length() == 0) {
                    time.requestFocus();
                    time.setHintTextColor(Color.RED);
                    time.setHint("מלא שעת יציאה");
                    return;
                }

                //firebase.orderByChild(str_date);
                firebase.child(send_time).push().setValue(sevenString);

                Toast.makeText(getActivity(), "הטרמפ נוסף בהצלחה", Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });

        return v;

    }

    public boolean check_phone_number(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public interface getDataFromDB {
        public void get_data_from_Db();
    }


}
