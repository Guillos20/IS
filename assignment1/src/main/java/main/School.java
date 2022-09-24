package main;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"professors"})
@XmlAccessorType(XmlAccessType.FIELD)
public class School {

    @XmlElement(name = "professor")
    List<Professor> professors;

    public void setList(List <Professor> professors)
    {
        this.professors = professors;
    }
}
