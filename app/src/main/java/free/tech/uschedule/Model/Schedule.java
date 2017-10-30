package free.tech.uschedule.Model;

import free.tech.uschedule.Item;

/**
 * Created by root on 29-10-17.
 */

public class Schedule {
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
}
