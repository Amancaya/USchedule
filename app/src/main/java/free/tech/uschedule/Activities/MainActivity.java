package free.tech.uschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import free.tech.uschedule.Adapters.AdapterRecycler;
import free.tech.uschedule.Model.Subject;
import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String SUBJECT_NODE = "subject";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private List<RealmObject> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressBar.setVisibility(View.VISIBLE);
        LoadDataFireBase();
        if (itemList != null){
            Log.e(TAG, "ENTRAN");
            progressBar.setVisibility(View.GONE);
            recyclerView.setAdapter(new AdapterRecycler(itemList, this));
        }

    }

    private void LoadDataFireBase(){
        itemList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(SUBJECT_NODE).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()){
                    Log.e(TAG, dataSnapshot.toString());
                    if (dataSnapshot.hasChild("schedule")){
                        Log.e(TAG+" schedule", dataSnapshot.child("schedule").toString());
                    }

                    if (dataSnapshot.hasChild("assistance")){
                        Log.e(TAG+"asistente", dataSnapshot.child("assistance").toString());
                        if (dataSnapshot.child("assistance").hasChild("schedule")){
                            Log.e(TAG+"SCH AS", dataSnapshot.child("assistance").child("schedule").toString());
                        }
                    }
//                    Subject subject = dataSnapshot.getValue(Subject.class);
//                    itemList.add(subject);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
