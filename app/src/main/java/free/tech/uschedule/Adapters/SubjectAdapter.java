package free.tech.uschedule.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import java.util.List;

import free.tech.uschedule.ItemclickListener;
import free.tech.uschedule.Model.Schedule;
import free.tech.uschedule.Model.Subject;
import free.tech.uschedule.R;

/**
 * Created by root on 29-10-17.
 */

public class SubjectAdapter extends ExpandableRecyclerAdapter<SubjectAdapter.SubjectHolder,SubjectAdapter.ScheduleHolder> {

    Context context;
    List<ParentObject> objectList;
    public SubjectAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        this.context = context;
        this.objectList = parentItemList;
    }

    @Override
    public SubjectHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public ScheduleHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindParentViewHolder(SubjectHolder subjectHolder, int i, Object o) {

    }

    @Override
    public void onBindChildViewHolder(ScheduleHolder scheduleHolder, int i, Object o) {

    }

    public static class SubjectHolder extends ParentViewHolder implements View.OnClickListener{
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

    public static class ScheduleHolder extends ChildViewHolder {
        TextView day, classroom, init, finish;

        public ScheduleHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.text_day);
            classroom = itemView.findViewById(R.id.text_classroom);
            init = itemView.findViewById(R.id.text_init);
            finish = itemView.findViewById(R.id.text_finish);
        }

        private void binData(Schedule schedule){
            day.setText(schedule.getDay());
            classroom.setText(schedule.getClassroom());
            init.setText(schedule.getInit());
            finish.setText(schedule.getFinish());
        }
    }
}
