package main;
import jakarta.xml.bind.annotation.*;

import java.util.Date;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    @XmlAttribute
    private int id;
    private String name;
    private Date birthDate;
    private long age;
    private Date registrationDate;
    private String address;
    private String professor;

    public void setter(int id, String name, Date birthDate, long age, Date registrationDate, String address) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.registrationDate = registrationDate;
        this.address = address;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
