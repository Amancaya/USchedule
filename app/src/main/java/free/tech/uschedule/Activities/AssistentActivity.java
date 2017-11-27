package free.tech.uschedule.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import free.tech.uschedule.Adapters.AdapterRecycler;
import free.tech.uschedule.Model.Assistance;
import free.tech.uschedule.Model.Schedule;
import free.tech.uschedule.Model.Subject;
import free.tech.uschedule.R;
import io.realm.RealmList;
import io.realm.RealmObject;

public class AssistentActivity extends AppCompatActivity {

    public static final String TAG = "AssistentActivity";
    public static final String SUBJECT = "Subject";

    public static void createInstance(Activity activity, Subject subject){
        Intent intent = getLaunchIntent(activity, subject)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    public static Intent getLaunchIntent(Activity activity, Subject subject){
        Intent intent = new Intent(activity, AssistentActivity.class);
        intent.putExtra(SUBJECT, subject);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistent);
        if (getIntent().getParcelableExtra(SUBJECT)  != null){
            Subject subject = getIntent().getParcelableExtra(SUBJECT);
            List<RealmObject> objectList = new ArrayList<>();
            objectList.addAll(subject.getSchedules());
            createRecyclers(R.id.recycler_schedule, objectList);

            objectList = new ArrayList<>();
            objectList.addAll(subject.getAssistances());
            createRecyclers(R.id.recycler_assistant, objectList);
        }
    }

    private void createRecyclers(int R, List<RealmObject> objectList){
        RecyclerView recyclerView = (RecyclerView) findViewById(R);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (objectList != null || !objectList.isEmpty()){
            recyclerView.setAdapter(new AdapterRecycler(objectList, this));
        }
    }
}
