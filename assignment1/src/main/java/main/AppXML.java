package main;

import com.github.javafaker.Faker;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public class AppXML {

    public static void main(String[] args) {
        try {
            long timeA = System.currentTimeMillis();
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(School.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File f = new File("assignment.xsl");
            marshaller.marshal(createSchoolObject(), f);
            long timeB = System.currentTimeMillis();
            long xmlTime = timeB - timeA;
            System.out.println("XML Time " + xmlTime);
            timeA = System.currentTimeMillis();
            gZipCompression(f.getPath());
            timeB = System.currentTimeMillis();
            System.out.println("Compression GZIP Time " + (timeB - timeA));
            System.out.println("XML + GZIP Time " + xmlTime + (timeB - timeA));
            System.out.println("File Size " + f.length());
            System.out.println("File GZIP Size " + new File("assignment.xsl.gz").length());
            timeA = System.currentTimeMillis();
            File file = new File("assignment.xsl");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            School s = (School) unmarshaller.unmarshal(file);
            System.out.println(s);
            timeB = System.currentTimeMillis();
            System.out.println("Unmarshall time " + (timeB - timeA));

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
            student.setter(i, faker.name().fullName(), faker.phoneNumber().cellPhone(),
                    genders[(int) (Math.random() * (3))],birth,
                    Period.between(birth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                            new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears(),
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
        Path target = Paths.get("assignment.xsl.gz");
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

