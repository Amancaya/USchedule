package free.tech.uschedule.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import free.tech.uschedule.Adapters.AdapterRecycler;
import free.tech.uschedule.Model.Subject;
import free.tech.uschedule.R;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class FavoriteActivity extends AppCompatActivity {

    private static final String TAG = "FavoriteActivity";
    private Realm realm;
    private List<RealmObject> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setTitle(R.string.favorite);
        realm = Realm.getDefaultInstance();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        itemList = new ArrayList<>();
        LoadRealDB();
        recyclerView.setAdapter(new AdapterRecycler(itemList, this, realm));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void LoadRealDB(){
        RealmResults<Subject> subjectRealmResults = realm.where(Subject.class).findAll();
        Log.e(TAG, subjectRealmResults.size()+"");
        itemList.addAll(subjectRealmResults);
    }
}
