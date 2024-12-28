package ameya.CV.CV.controller;

import ameya.CV.CV.model.Course;
import ameya.CV.CV.service.CVService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CVController {
    @Autowired
    CVService cvService;
    
    @PostMapping("/getOrdering")
    public ResponseEntity<List<Course>> getOrdering (@RequestBody List<Course> courses) {
        List<Course> orderedCourses = cvService.getOrdering(courses);
        return new ResponseEntity<>(orderedCourses, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testAPI () {
        System.out.println("From test");
        return new ResponseEntity<>("Test API hit", HttpStatus.OK);
    }
}