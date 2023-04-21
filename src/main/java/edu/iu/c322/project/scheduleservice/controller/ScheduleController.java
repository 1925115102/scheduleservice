package edu.iu.c322.project.scheduleservice.controller;

import edu.iu.c322.project.scheduleservice.model.Course;
import edu.iu.c322.project.scheduleservice.model.Request;
import edu.iu.c322.project.scheduleservice.model.EnrollRequest;
import edu.iu.c322.project.scheduleservice.model.Student;
import edu.iu.c322.project.scheduleservice.repository.ScheduleRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final WebClient courseservice;
    ScheduleRepository repository;

    public ScheduleController(WebClient.Builder webClientBuilder, ScheduleRepository repository) {
        courseservice = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.repository = repository;
    }

    @GetMapping("/student/{studentId}")
    public Optional<Student> findStudent(@PathVariable int studentId){
        return repository.findById(studentId);
    }

    @GetMapping("/courses")
    public List<Course> browseCourse(){
        return courseservice.get().uri("/courses/course").retrieve()
                .bodyToFlux(Course.class)
                .collectList().block();
    }

    @PostMapping("/student")
    public int registerStudent(Student student){
        Student student1 = repository.save(student);
        return student1.getId();
    }

    @PutMapping("/student/{studentId}/enroll")
    public void enroll(@PathVariable int studentId,@RequestBody EnrollRequest request){
        Student student = repository.findById(studentId).orElseThrow();
        student.setRequest(request.getPreference());
        repository.save(student);
    }

    @PutMapping("/student/{studentId}")
    public void addCourse(@PathVariable int studentId, @RequestBody Request request){
        int courseId = request.getCourseId();
        Student student = repository.findById(studentId).orElseThrow();
        List<Request> shoppingCart = student.getShoppingCart();
        shoppingCart.add(request);
        student.setShoppingCart(shoppingCart);
        repository.save(student);
    }

    @DeleteMapping("/student/{studentId}")
    public void removeCourse(@PathVariable int studentId, @RequestBody Request request){
        int courseId = request.getCourseId();
        Student student = repository.findById(studentId).orElseThrow();
        List<Request> shoppingCart = student.getShoppingCart();
        shoppingCart.remove(request);
        student.setShoppingCart(shoppingCart);
        repository.save(student);
    }


}
