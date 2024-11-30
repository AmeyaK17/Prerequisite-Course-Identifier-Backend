package ameya.CV.CV.model;

import lombok.Getter;

@Getter
public class Course {
    int id, preRequisiteID;
    String title;

    public Course(int id, String title, int preRequisiteID) {
        this.id = id;
        this.title = title;
        this.preRequisiteID = preRequisiteID;
    }
}
