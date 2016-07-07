package lv.ctco.student;

import lv.ctco.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired //same as Inject
    StudentRepository studentRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentById(@PathVariable("id") long id) {

        if (studentRepository.exists(id)) {
            Student student = studentRepository.findOne(id);
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(path = " /{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudentByID(@PathVariable("id") long id,
                                               @RequestBody Student student) {

        if (studentRepository.exists(id)) {
            Student editedStudent = studentRepository.findOne(id);
            editedStudent.setName(student.getName());
            editedStudent.setSurname(student.getSurname());
            studentRepository.save(editedStudent);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        if (studentRepository.exists(id)) {
            studentRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}/assignment", method = RequestMethod.POST)
    public ResponseEntity<?> addAssignment(@PathVariable("id") long id, @RequestBody Assignment assignment) {
        if (studentRepository.exists(id)) {
            Student student = studentRepository.findOne(id);
            student.addAssignment(assignment);
            studentRepository.save(student);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
