package free.tech.uschedule.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.ProcessingInstruction;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import free.tech.uschedule.Activities.AssistentActivity;
import free.tech.uschedule.Item;
import free.tech.uschedule.ItemclickListener;
import free.tech.uschedule.Model.Assistance;
import free.tech.uschedule.Model.Schedule;
import free.tech.uschedule.Model.Subject;
import free.tech.uschedule.R;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by root on 29-10-17.
 */

public class AdapterRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemclickListener {

    private static final int TYPE_SUBJECT = 1;
    private static final int TYPE_SCHEDULE = 2;
    private static final int TYPE_ASSITENT = 3;
    private List<RealmObject> itemList;
    private Activity activity;
    private Realm realm;


    public AdapterRecycler(List<RealmObject> itemList, Activity activity){
        this.activity = activity;
        this.itemList = itemList;
    }

    public AdapterRecycler(List<RealmObject> itemList, Activity activity, Realm realm){
        this.activity = activity;
        this.itemList = itemList;
        this.realm = realm;
    }
    public void addAll(List<RealmObject> itemList){
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void clearAll(){
        this.itemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_SUBJECT:
                viewHolder = new SubjectHolder(inflater.inflate(R.layout.item_subject, parent, false), this, realm);
                break;
            case TYPE_SCHEDULE:
                viewHolder = new ScheduleHolder(inflater.inflate(R.layout.item_schedule, parent, false));
                break;
            case TYPE_ASSITENT:
                viewHolder = new AssistantHolder(inflater.inflate(R.layout.item_assitant, parent, false), activity);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof Subject){
            return TYPE_SUBJECT;
        }else if (itemList.get(position) instanceof Schedule){
            return  TYPE_SCHEDULE;
        }else if (itemList.get(position) instanceof Assistance){
            return TYPE_ASSITENT;
        }
        else {
            throw new RuntimeException("ItemViewType, unknown");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SUBJECT){
            ((SubjectHolder)holder).binData((Subject)itemList.get(position));
        }else if (getItemViewType(position) == TYPE_SCHEDULE){
            ((ScheduleHolder)holder).binData((Schedule)itemList.get(position));
        }else if (getItemViewType(position) == TYPE_ASSITENT){
            ((AssistantHolder) holder).binData((Assistance)itemList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (getItemViewType(position)){
            case TYPE_SUBJECT:
                if (view.getId() == R.id.star_image) {
                    ImageView imageView = (ImageView) view;
                    if (view.getTag().equals(R.drawable.star_yellow)) {
                        Subject subject_D = (Subject)itemList.get(position);
                        Log.e("Adapter", subject_D.getAcronym());
                        RealmResults<Subject> subjects = realm.where(Subject.class).findAll();
                        for (Subject subject: subjects) {
                            if (subject.getAcronym().equals(subject_D.getAcronym())){
                                Log.e("Adapter", subject.getAcronym());
                                DeleteSubject(subject, imageView);
                            }
                        }
                    }
                    else if (view.getTag().equals(R.drawable.star)){
                        InsertSubject((Subject) itemList.get(position), imageView);
                    }
                }else {
                    AssistentActivity.createInstance(activity, (Subject) itemList.get(position));
                }
                break;
        }
    }

    private void ChangeColorStar(ImageView imageView, int color){
        imageView.setImageResource(color);
        imageView.setTag(color);
    }

    private void InsertSubject(final Subject subject, final ImageView imageView){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (subject.isValid()){
                    realm.insert(subject);
                    Toast.makeText(activity, "Añadido a favoritos", Toast.LENGTH_LONG).show();
                    ChangeColorStar(imageView, R.drawable.star_yellow);
                }
            }
        });
    }

    private void DeleteSubject(final Subject subject, final ImageView imageView){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (subject != null && subject.isValid()){
                    subject.deleteFromRealm();
                    ChangeColorStar(imageView, R.drawable.star);
                    Toast.makeText(activity, "Eliminado de favoritos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private static class SubjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, acronym, teacher, semester;
        private ItemclickListener listener;
        private ImageView star_image;
        private Realm realm;

        private SubjectHolder(View itemView, ItemclickListener listener, Realm realm) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            acronym = itemView.findViewById(R.id.text_acronym);
            teacher = itemView.findViewById(R.id.text_teacher);
            semester = itemView.findViewById(R.id.semester);
            star_image = itemView.findViewById(R.id.star_image);
            star_image.setTag(R.drawable.star);
            this.listener = listener;
            itemView.setOnClickListener(this);
            this.realm = realm;
            star_image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }

        private void binData(Subject subject){
            name.setText(subject.getName());
            acronym.setText(subject.getAcronym());
            teacher.setText(subject.getTeacher());
            semester.setText("Semestre "+subject.getSemester());

            if (CheckIfExistsSubject(subject.getAcronym())) ChangeColorStar(R.drawable.star_yellow);
            else ChangeColorStar(R.drawable.star);
        }

        private void ChangeColorStar(int color){
            star_image.setImageResource(color);
            star_image.setTag(color);
        }

        private boolean CheckIfExistsSubject(String id){
            Subject subject = realm.where(Subject.class).equalTo("acronym", id).findFirst();
            return subject != null;
        }
    }

    private static class AssistantHolder extends RecyclerView.ViewHolder{

        RecyclerView recycler_schedule_assistant;
        TextView name_assistant, email_assistant, phone_assistant;
        Activity activity;
        private AssistantHolder(View itemView, Activity activity) {
            super(itemView);
            name_assistant = itemView.findViewById(R.id.name_assistant);
            email_assistant = itemView.findViewById(R.id.email_assistant);
            phone_assistant = itemView.findViewById(R.id.phone_assistant);

            recycler_schedule_assistant = itemView.findViewById(R.id.recycler_schedule_assistant);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
            recycler_schedule_assistant.setLayoutManager(layoutManager);
            recycler_schedule_assistant.setHasFixedSize(true);

            this.activity = activity;
        }

        private void binData(Assistance assistance){
            name_assistant.setText(assistance.getName());
            email_assistant.setText(assistance.getEmail());
            phone_assistant.setText(assistance.getPhone());

            List<RealmObject> objectList = new ArrayList<>();
            objectList.addAll(assistance.getSchedules());

            if (!objectList.isEmpty()){
                recycler_schedule_assistant.setAdapter(new AdapterRecycler(objectList, activity));
            }
        }
    }

    private static class ScheduleHolder extends RecyclerView.ViewHolder{
        TextView text_classroom, text_day, text_init, text_finish;

        private ScheduleHolder(View itemView) {
            super(itemView);

            text_classroom = itemView.findViewById(R.id.text_classroom);
            text_day = itemView.findViewById(R.id.text_day);
            text_init = itemView.findViewById(R.id.text_init);
            text_finish = itemView.findViewById(R.id.text_finish);
        }

        private void binData(Schedule schedule){
            text_classroom.setText(schedule.getClassroom());
            text_day.setText(schedule.getDay());
            text_init.setText(schedule.getInit());
            text_finish.setText(schedule.getFinish());
        }
    }

}
