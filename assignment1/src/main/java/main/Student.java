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
    private int age;
    private Date registrationDate;
    private String address;

    public void setter(int id, String name, Date birthDate, int age, Date registrationDate, String address) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.registrationDate = registrationDate;
        this.address = address;
    }

}
