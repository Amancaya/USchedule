package free.tech.uschedule.Model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by root on 25-11-17.
 */

public class Assistance extends RealmObject implements Parcelable {

    private String email;
    private String name;
    private String phone;
    private RealmList<Schedule> schedules;

    public Assistance() {
    }

    public RealmList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(RealmList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeTypedList(this.schedules);
    }

    protected Assistance(Parcel in) {
        this.email = in.readString();
        this.name = in.readString();
        this.phone = in.readString();

        schedules = new RealmList<>();
        schedules.addAll(in.createTypedArrayList(Schedule.CREATOR));
    }

    public static final Parcelable.Creator<Assistance> CREATOR = new Parcelable.Creator<Assistance>() {
        @Override
        public Assistance createFromParcel(Parcel source) {
            return new Assistance(source);
        }

        @Override
        public Assistance[] newArray(int size) {
            return new Assistance[size];
        }
    };
}
