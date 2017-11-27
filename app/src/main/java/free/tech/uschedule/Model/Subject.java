package free.tech.uschedule.Model;


import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 29-10-17.
 */

public class Subject extends RealmObject implements Parcelable {
    @PrimaryKey
    private String id;

    private String acronym;
    private String name;
    private String teacher;
    private int semester;
    private RealmList<Assistance> assistances;
    private RealmList<Schedule> schedules;

    public Subject(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(RealmList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public RealmList<Assistance> getAssistances() {
        return assistances;
    }

    public void setAssistances(RealmList<Assistance> assistances) {
       this.assistances = assistances;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.acronym);
        dest.writeString(this.name);
        dest.writeString(this.teacher);
        dest.writeInt(this.semester);
        dest.writeTypedList(this.assistances);
        dest.writeTypedList(this.schedules);
    }

    protected Subject(Parcel in) {
        this.acronym = in.readString();
        this.name = in.readString();
        this.teacher = in.readString();
        this.semester = in.readInt();

        this.assistances = new RealmList<>();
        assistances.addAll(in.createTypedArrayList(Assistance.CREATOR));

        this.schedules = new RealmList<>();
        schedules.addAll(in.createTypedArrayList(Schedule.CREATOR));
    }

    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel source) {
            return new Subject(source);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}
