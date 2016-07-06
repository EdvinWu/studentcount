package lv.ctco;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentById(@PathVariable("id") long id) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst();

        if (student.isPresent())
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(path = " /{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudentByID(@PathVariable("id") long id,
                                               @RequestBody Student student) {
        Optional<Student> student2 = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        if (student2.isPresent()) {
            student2.get().setName(student.getName());
            student2.get().setSurname(student.getSurname());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        int pre = students.size();
        students = students.stream()
                .filter((s) -> s.getId() != id)
                .collect(Collectors.toList());
        int post = students.size();

        if (pre == post)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }


}
