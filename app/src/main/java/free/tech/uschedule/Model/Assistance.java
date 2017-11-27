package free.tech.uschedule.Model;

import io.realm.RealmObject;

/**
 * Created by root on 25-11-17.
 */

public class assistance extends RealmObject {

    private String email;
    private String name;
    private String phone;

    public assistance() {
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
}
