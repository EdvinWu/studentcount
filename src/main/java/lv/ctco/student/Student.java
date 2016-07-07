package lv.ctco.student;

import lv.ctco.Assignment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "STUDENT") //if table has different name from class
public class Student {

    @Id //marks column ad id
    @GeneratedValue //generate id automatically
    @Column(name = "ID") //if column in table has different column name
    private long id;
    private String name;
    private String surname;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Assignment> assignments;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments.clear();
        if (assignments != null)
            this.assignments.addAll(assignments);
    }

    public  void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
    }

    public boolean removeAssignmentById(long id) {
        Optional<Assignment> assignment = assignments.stream()
                .filter(a -> a.getId() == id)
                .findAny();
        return assignment.isPresent() && assignments.remove(assignment.get());
    }


}
