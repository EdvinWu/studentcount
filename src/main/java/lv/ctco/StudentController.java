package lv.ctco;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    List<Student> students = new ArrayList<Student>() {{
        Student student = new Student();
        student.setName("Name");
        student.setSurname("Surname");
        add(student);
    }};


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllStudents() {
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        students.add(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentById(@PathVariable("id") long id) {
        Student student = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .get();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }


    @RequestMapping(path = " /{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudentByID(@PathVariable("id") long id,
                                               @RequestBody Student student) {
        Student student2 = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .get();
        student2.setName(student.getName());
        student2.setSurname(student.getSurname());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        students.remove(students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .get());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
