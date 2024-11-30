package ameya.CV.CV.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;

import ameya.CV.CV.model.Course;

@Service
public class CVService {
    public void getCourses(List<Course> courses) {
        for (Course course: courses) {
            System.out.println(course.getId() + ": " + course.getTitle());
        }
    }

    public List<Course> findPreRequisites(List<Course> courses) {
        HashMap<Integer, String> courseDetails = new HashMap<>();
        for(Course course: courses) {
            courseDetails.put(course.getId(), course.getTitle());
        }

        HashMap<Integer, List<Integer>> adj = new HashMap<>();

        for(Course course: courses) {
            adj.getOrDefault(course.getPreRequisiteID(), new ArrayList<>()).add(course.getId());
        }

        HashMap<Integer, Integer> inDegree = new HashMap<>();

        for(Map.Entry<Integer, List<Integer>> entry: adj.entrySet()) {
            for(int nextCourseId: entry.getValue()) {
                inDegree.put(nextCourseId, inDegree.getOrDefault(nextCourseId, 0) + 1);
            }
        }

        Queue<Integer> q = new LinkedList<>();
        List<Integer> ordering = new ArrayList<>();
        
        for(Map.Entry<Integer, Integer> entry: inDegree.entrySet()) {
            if(entry.getValue() == 0) {
                q.add(entry.getKey());
            }
        }

        while(!q.isEmpty()) {
            int currCourse = q.remove();
            ordering.add(currCourse);

            for(int nextCourse: adj.get(currCourse)) {
                inDegree.put(nextCourse, inDegree.get(nextCourse) - 1);

                if(inDegree.get(nextCourse) == 0) {
                    q.add(nextCourse);
                }
            }
        }

        List<Course> orderedCourses = new ArrayList<>();
        for(int courseId: ordering) {
            orderedCourses.add(new Course(courseId, courseDetails.get(courseId), 0));
        }

        return orderedCourses;
    }
}
