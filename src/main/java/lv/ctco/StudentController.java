package lv.ctco;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
