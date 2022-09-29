package main;

import com.github.javafaker.Faker;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class AppXML {
    public static long timeBuf = 0;
    public static ArrayList<String> studBuf = new ArrayList<>();
    public static ArrayList<String> profjam = new ArrayList<>();

    public static void main(String[] args) {

        try {
            FileWriter fileWriter = new FileWriter("data.txt");
            long timeA = System.currentTimeMillis();
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(School.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File f = new File("assignment.xsl");
            School school = createSchoolObject();
            marshaller.marshal(school, f);
            long timeB = System.currentTimeMillis();
            long xmlTime = (timeB - timeA) - timeBuf;
            long timeBefore = System.currentTimeMillis();
            FileWriter fos = new FileWriter("assignment");
            for (String student : studBuf) {
                fos.write(student);
            }
            for (String prof : profjam) {
                fos.write(prof);
            }
            fos.close();
            long timeAfter = System.currentTimeMillis();
            timeBuf += (timeAfter - timeBefore);
            fileWriter.write("XML Time " + xmlTime + "\n");
            System.out.println("XML Time " + xmlTime);
            timeA = System.currentTimeMillis();
            gZipCompression(f.getPath());
            timeB = System.currentTimeMillis();
            fileWriter.write("Compression GZIP Time " + (timeB - timeA) + "\n"
                    + "xml + gzip time " + (xmlTime + (timeB - timeA)) + "\n"
                    + "File Size " + f.length() + "\n"
                    + "File GZIP Size " + new File("assignment.xsl.gz").length() + "\n"
                    + "Protocol Buffers Time " + timeBuf + "\n" + "Protocol Buffers size " + new File("assignment").length());
            System.out.println("Compression GZIP Time " + (timeB - timeA));
            System.out.println("xml + gzip time " + (xmlTime + (timeB - timeA)));
            System.out.println("Total time for Protocol Buffers " + timeBuf);
            System.out.println("Protocol Buffers size " + new File("assignment").length());
            System.out.println("File Size " + f.length());
            System.out.println("File GZIP Size " + new File("assignment.xsl.gz").length());

            timeA = System.currentTimeMillis();
            File file = new File("assignment.xsl");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            School s = (School) unmarshaller.unmarshal(file);
            System.out.println(s);
            timeB = System.currentTimeMillis();
            fileWriter.write("Unmarshall time " + (timeB - timeA) + "\n");
            decompressGzipFile("assignment.xsl.gz", "assignmentDecompressed.xsl");
            fileWriter.close();
            System.out.println("Unmarshall time " + (timeB - timeA));

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static School createSchoolObject() throws FileNotFoundException {
        School s = new School();
        Faker faker = new Faker();
        ArrayList<Student> allStudents = new ArrayList<>();
        ArrayList<Professor> allProfessors = new ArrayList<>();
        int min = 0;
        int max = 15;
        String[] genders = { "Male", "Female", "Other" };
        for (int i = 0; i < 15000; i++) {
            Student student = new Student();
            Date birth = faker.date().birthday();
            String birthdate = (String) birth.toString();
            String name = faker.name().fullName();
            String cell = faker.phoneNumber().cellPhone();
            String gender = genders[(int) (Math.random() * (3))];
            long age = Period.between(birth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears();
            int ageBuf = (int) age;
            Date regDate = faker.date().between(birth, new Date());
            String regiDate = (String) regDate.toString();
            String addr = faker.address().streetAddress();
            student.setter(i, name, cell, gender, birth, age, regDate, addr);
            allStudents.add(student);

            // this is for the protocols
            long timeBefore = System.currentTimeMillis();
            StudentBuffer stud = StudentBuffering(i, birthdate, name, cell, gender, ageBuf, regiDate, addr);
            studBuf.add(stud.toString());
            long timeAfter = System.currentTimeMillis();
            timeBuf += (timeAfter - timeBefore);

        }
        for (int i = 0; i < 1000; i++) {
            Professor professor = new Professor();
            Date birth = faker.date().birthday();
            String birthdate = (String) birth.toString();
            String name = faker.name().fullName();
            String cell = faker.phoneNumber().cellPhone();
            String addr = faker.address().streetAddress();
            professor.setter(i, name, birth, cell, addr);
            long timeBefore = System.currentTimeMillis();
            ProfessorBuffer prof = ProfessorBuffering(i, birthdate, name, cell, addr);
            profjam.add(prof.toString());
            long timeAfter = System.currentTimeMillis();
            timeBuf += (timeAfter - timeBefore);
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

    private static void decompressGzipFile(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static StudentBuffer StudentBuffering(int id, String birth, String name, String cell, String gender,
            int ageBuf,
            String regiDate, String addr) throws FileNotFoundException {
        StudentBuffer stud = StudentBuffer.newBuilder().setId(id).setBirthdate(birth).setName(name).setCell(cell)
                .setGender(gender).setAge(ageBuf).setRegDate(regiDate).setAddress(addr).build();
        FileOutputStream fos = new FileOutputStream("Student.txt");
        try {
            stud.writeTo(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stud;
    }

    public static ProfessorBuffer ProfessorBuffering(int id, String name, String birthdate, String cell,
            String address) {
        ProfessorBuffer prof = ProfessorBuffer.newBuilder().setId(id).setBirthdate(birthdate).setName(name)
                .setPhoneNumber(cell)
                .setAddress(address).build();
        return prof;
    }
}
