package main;

import com.github.javafaker.Faker;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public class AppXML {

    public static void main(String[] args) {
        try {
            long timeA = System.currentTimeMillis();
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(School.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File f = new File("assignment.xsl");
            jaxbMarshaller.marshal(createSchoolObject(), f);
            long timeB = System.currentTimeMillis();
            System.out.println(timeB - timeA);
            timeA = System.currentTimeMillis();
            gZipCompression(f.getPath());
            timeB = System.currentTimeMillis();
            System.out.println(timeB - timeA);
            System.out.println(f.length());
            // XML Unmarshalling
            /*File file = new File("C:\\test\\company.xml");
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Company o = (Company) jaxbUnmarshaller.unmarshal(file);
            System.out.println(o);*/

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static School createSchoolObject() {
        School s = new School();
        Faker faker = new Faker();
        ArrayList<Student> allStudents = new ArrayList<>();
        ArrayList<Professor> allProfessors = new ArrayList<>();
        int min = 0;
        int max = 15;
        String[] genders = {"Male", "Female", "Other"};
        for (int i = 0; i < 15000; i++){
            Student student = new Student();
            Date birth = faker.date().birthday();
            student.setter(i, faker.name().fullName(), faker.phoneNumber().cellPhone(), genders[(int) (Math.random() * (3))],birth, new Date().getTime() - birth.getTime(),
                    faker.date().between(birth, new Date()), faker.address().streetAddress());
            allStudents.add(student);
        }
        for (int i = 0; i < 1000; i++){
            Professor professor = new Professor();
            Date birth = faker.date().birthday();
            professor.setter(i, faker.name().fullName(), birth, faker.phoneNumber().cellPhone(),
                    faker.address().streetAddress());
            List<Student> studentsSub = allStudents.subList(min, max);
            for (Student student : studentsSub)
                student.setProfessor(professor.getName());
            professor.setStudents(studentsSub);
            allProfessors.add(professor);
            if (max == allStudents.size())
                break;
            min += 15;
            max += 15;
        }
        s.setProfessors(allProfessors);
        return s;
    }

    private static void gZipCompression(String path) throws IOException {
        Path source = Paths.get(path);
        Path target = Paths.get("assignment1.xsl.gz");
        try (GZIPOutputStream gos = new GZIPOutputStream(
                Files.newOutputStream(target.toFile().toPath()));
             FileInputStream fis = new FileInputStream(source.toFile())) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gos.write(buffer, 0, len);
            }
        }



    }

}

