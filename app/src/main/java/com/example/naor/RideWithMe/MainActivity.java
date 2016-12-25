package com.example.naor.RideWithMe;


import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.key;


public class MainActivity extends Activity implements Addtremp.getDataFromDB {
    public ListView mainListView;
    public ArrayList<_7Data> msg_List = new ArrayList<>();
    public EditText search_box;
    public Firebase firebase;
    public CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //INITIALIZE UI
        mainListView = (ListView) findViewById(R.id.listview_main);
        search_box = (EditText) findViewById(R.id.search_box);
        //INITIALIZE FIREBASE DATABASE
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://ridewithme-c7f3a.firebaseio.com");
        adapter = new CustomAdapter(this, R.layout.listview_row_style, msg_List);
        get_data_from_Db();
    }


    //OnClick btn --> Show the add tremp dialog when btn clicked//
    public void showAddTrempDialog(View view) {
        FragmentManager manager = getFragmentManager();
        Addtremp trempDialog = new Addtremp();
        trempDialog.setListener(this);
        trempDialog.show(manager, "Addtremp");
    }

    //this function getting the data added to the dataBase and retrieve to the listView
    public void get_data_from_Db() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }



    //HELPER METHOD FOR get_data_from_Db()
    public void getUpdates(DataSnapshot dataSnapshot) {

            for (DataSnapshot str : dataSnapshot.getChildren()) {
                _7Data _7data = new _7Data();
                _7data.set_name(str.getValue(_7Data.class).get_name());
                _7data.set_phone(str.getValue(_7Data.class).get_phone());
                _7data.set_from(str.getValue(_7Data.class).get_from());
                _7data.set_to(str.getValue(_7Data.class).get_to());
                _7data.set_date(str.getValue(_7Data.class).get_date());
                _7data.set_time(str.getValue(_7Data.class).get_time());
                _7data.set_extras(str.getValue(_7Data.class).get_extras());
                msg_List.add(0, _7data);
                mainListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

    }

}

