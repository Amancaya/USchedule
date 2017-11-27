package free.tech.uschedule.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import free.tech.uschedule.Item;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by root on 29-10-17.
 */

public class Schedule extends RealmObject implements Parcelable {

    private String classroom;
    private String day;
    private String init;
    private String finish;

    public Schedule(){}

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.classroom);
        dest.writeString(this.day);
        dest.writeString(this.init);
        dest.writeString(this.finish);
    }

    protected Schedule(Parcel in) {
        this.classroom = in.readString();
        this.day = in.readString();
        this.init = in.readString();
        this.finish = in.readString();
    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
