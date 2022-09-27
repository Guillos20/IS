package main;
import jakarta.xml.bind.annotation.*;

import java.util.Date;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    @XmlAttribute
    private int id;
    private String name;
    private String telephone;
    private String gender;
    private Date birthDate;
    private long age;
    private Date registrationDate;
    private String address;
    private String professor;

    public void setter(int id, String name, String telephone, String gender, Date birthDate, long age, Date registrationDate, String address) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.age = age;
        this.registrationDate = registrationDate;
        this.address = address;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
