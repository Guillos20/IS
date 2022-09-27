package main;

import jakarta.xml.bind.annotation.*;

import java.util.Date;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Professor {
    @XmlAttribute
    private int id;
    private String name;
    private Date birthDate;
    private String phoneNumber;
    private String address;
    @XmlElement(name = "student")
    private List<Student> students;

    public void setter(int id, String name, Date birthDate, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    @XmlTransient
    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
