package lv.ctco;

import javax.persistence.*;

@Entity
@Table(name = "STUDENT") //if table has different name from class
public class Student {

    @Id //marks column ad id
    @GeneratedValue //generate id automatically
    @Column(name = "ID") //if column in table has different column name
    private long id;
    private String name;
    private String surname;

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
}
