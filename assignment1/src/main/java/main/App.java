package main;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

public class App {

    public static void main(String[] args) {
        try {

            long timeA = System.currentTimeMillis();
            xml("xml");
            long timeB = System.currentTimeMillis();
            System.out.println(timeB - timeA);

            long timeC = System.currentTimeMillis();
            File f = xml("gzip");
            gZipCompression(f.getPath());
            long timeD = System.currentTimeMillis();
            System.out.println(timeD - timeC);


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

    private static File xml(String name) throws JAXBException {
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(School.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File f = new File("test" + name + ".xsl");
        jaxbMarshaller.marshal(createSchoolObject(), f);
        return f;
    }

    private static School createSchoolObject() {

        School sch = new School();
        Student s = new Student();
        s.setter(1, "Alberto", new Date(), 20, new Date(), "a tua prima");
        Professor p = new Professor();
        p.setter(1, "Albertina", "3243242", "42342423", "a tua prima de 4");
        p.setStudents(Arrays.asList(s));
        sch.setList(Arrays.asList(p));


        return sch;
    }

    private static void gZipCompression(String path) throws IOException {
        Path source = Paths.get(path);
        System.out.println(path);
        Path target = Paths.get("test.xsl.gz");
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

