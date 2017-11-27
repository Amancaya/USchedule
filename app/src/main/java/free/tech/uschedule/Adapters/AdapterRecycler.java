package free.tech.uschedule;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import free.tech.uschedule.Model.Schedule;
import free.tech.uschedule.Model.Subject;

/**
 * Created by root on 29-10-17.
 */

public class AdapterRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemclickListener{

    private static final int TYPE_SUBJECT = 1;
    private static final int TYPE_SCHEDULE = 2;
    private List<Item> itemList;
    private Activity activity;

    public AdapterRecycler(List<Item> itemList, Activity activity){
        this.activity = activity;
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_SUBJECT:
                viewHolder = new SubjectHolder(inflater.inflate(R.layout.item_subject, parent, false), this);
                break;
            case TYPE_SCHEDULE:
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof Subject){
            return TYPE_SUBJECT;
        }
        else {
            throw new RuntimeException("ItemViewType, unknown");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SUBJECT){
            ((SubjectHolder)holder).binData((Subject)itemList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private static class SubjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, acronym, teacher;
        private ItemclickListener listener;

        public SubjectHolder(View itemView, ItemclickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            acronym = itemView.findViewById(R.id.text_acronym);
            teacher = itemView.findViewById(R.id.text_teacher);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }

        private void binData(Subject subject){
            name.setText(subject.getName());
            acronym.setText(subject.getAcronym());
            teacher.setText(subject.getTeacher());
        }
    }


}
