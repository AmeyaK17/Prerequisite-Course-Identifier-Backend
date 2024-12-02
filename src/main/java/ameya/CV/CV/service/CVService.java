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
    public List<Course> getOrdering(List<Course> courses) {
        HashMap<Integer, String> courseDetails = new HashMap<>();
        for(Course course: courses) {
            courseDetails.put(course.getId(), course.getTitle());
        }

        HashMap<Integer, List<Integer>> adj = new HashMap<>();

        for(Course course: courses) {
            adj.put(course.getId(), adj.getOrDefault(course.getId(), new ArrayList<>()));
            
            if(course.getPreRequisiteID() == course.getId()){
                continue;
            }

            List<Integer> currList = adj.getOrDefault(course.getPreRequisiteID(), new ArrayList<>());
            currList.add(course.getId());
            adj.put(course.getPreRequisiteID(), currList);
        }

        System.out.println("Adj: ");
        System.out.println(adj);
        System.out.println();

        HashMap<Integer, Integer> inDegree = new HashMap<>();

        for(Map.Entry<Integer, List<Integer>> entry: adj.entrySet()) {
            inDegree.put(entry.getKey(), inDegree.getOrDefault(entry.getKey(), 0));

            for(int nextCourseId: entry.getValue()) {
                inDegree.put(nextCourseId, inDegree.getOrDefault(nextCourseId, 0) + 1);
            }
        }

        System.out.println("inDegree: ");
        System.out.println(inDegree);
        System.out.println();

        Queue<Integer> q = new LinkedList<>();
        List<Integer> ordering = new ArrayList<>();
        
        for(Map.Entry<Integer, Integer> entry: inDegree.entrySet()) {
            if(entry.getValue() == 0) {
                q.add(entry.getKey());
            }
        }

        System.out.println("Queue: ");
        System.out.println(q);
        System.out.println();

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

        System.out.println("Init Ordering: ");
        System.out.println(ordering);
        System.out.println();

        List<Course> orderedCourses = new ArrayList<>();
        for(int courseId: ordering) {
            orderedCourses.add(new Course(courseId, courseDetails.get(courseId), 0));
        }
        
        System.out.println("Ordering: ");
        System.out.println(orderedCourses);
        System.out.println();

        return orderedCourses;
    }
}
