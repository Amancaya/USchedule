package free.tech.uschedule.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by root on 26-11-17.
 */

public class prueba implements Parcelable {
    private ArrayList<Schedule> schedules = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.schedules);
    }

    public prueba() {
    }

    private prueba(Parcel in) {
        this.schedules = in.createTypedArrayList(Schedule.CREATOR);
    }

    public static final Parcelable.Creator<prueba> CREATOR = new Parcelable.Creator<prueba>() {
        @Override
        public prueba createFromParcel(Parcel source) {
            return new prueba(source);
        }

        @Override
        public prueba[] newArray(int size) {
            return new prueba[size];
        }
    };
}
