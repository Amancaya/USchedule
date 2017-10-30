package free.tech.uschedule.Model;



import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import free.tech.uschedule.Item;

/**
 * Created by root on 29-10-17.
 */

public class Subject extends Item implements ParentObject{
    private String acronym;
    private String name;
    private String teacher;
    private List<Object> scheduleList;

    public Subject(){}

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
    public List<Object> getChildObjectList() {
        return scheduleList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        scheduleList = list;
    }
}
