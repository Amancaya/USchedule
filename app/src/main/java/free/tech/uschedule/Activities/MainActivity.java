package free.tech.uschedule.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import free.tech.uschedule.Adapters.AdapterRecycler;
import free.tech.uschedule.Model.Assistance;
import free.tech.uschedule.Model.Schedule;
import free.tech.uschedule.Model.Subject;
import free.tech.uschedule.R;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String SUBJECT_NODE = "subject";
    public static final String SCHEDULE_NODE = "schedule";
    public static final String ASSISTANT_NODE = "assistance";

    private List<RealmObject> itemList;
    private Realm realm;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        if (databaseReference == null) FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressBar.setVisibility(View.VISIBLE);
        itemList = new ArrayList<>();
        LoadDataFireBase();
        if (itemList != null || !itemList.isEmpty()){
            progressBar.setVisibility(View.GONE);
            AdapterRecycler adapterRecycler = new AdapterRecycler(itemList, this, realm);
            recyclerView.setAdapter(adapterRecycler);
        }
    }

    private void LoadDataFireBase(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(SUBJECT_NODE).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RealmList<Schedule> scheduleRealmList = new RealmList<>();
                RealmList<Assistance> assistanceRealmList = new RealmList<>();
                if (dataSnapshot.exists()){
                    Subject subject = dataSnapshot.getValue(Subject.class);
                    if (dataSnapshot.hasChild("schedule")){
                        for (DataSnapshot snapshot: dataSnapshot.child(SCHEDULE_NODE).getChildren()) {
                            Schedule schedule = snapshot.getValue(Schedule.class);
                            scheduleRealmList.add(schedule);
                        }
                        subject.setSchedules(scheduleRealmList);
                    }

                    if (dataSnapshot.hasChild("assistance")){
                        for (DataSnapshot snapshot: dataSnapshot.child(ASSISTANT_NODE).getChildren()) {
                            Assistance assistance = snapshot.getValue(Assistance.class);
                            scheduleRealmList = new RealmList<>();
                            if (snapshot.hasChild("schedule")){
                                for (DataSnapshot snapshot1: snapshot.child(SCHEDULE_NODE).getChildren()) {
                                    Schedule schedule_a = snapshot1.getValue(Schedule.class);
                                    scheduleRealmList.add(schedule_a);
                                }
                            }
                            assistance.setSchedules(scheduleRealmList);
                            assistanceRealmList.add(assistance);
                        }
                    }
                    String ID = UUID.randomUUID().toString();
                    subject.setId(ID);
                    subject.setAssistances(assistanceRealmList);
                    itemList.add(subject);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
