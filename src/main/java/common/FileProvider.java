package common;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Stream;

public class FileProvider {

    public static Stream<File> generateFile() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String fileName = RandomStringUtils.random(length, useLetters, useNumbers);
        byte[] fileByteArray = new byte[50]; // length is bounded by 50
        new Random().nextBytes(fileByteArray);
        File file = new File("src/main/resources/" + fileName + ".txt");
        System.out.println(fileName);
        try {
            System.out.println(file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(fileByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.of(file);
    }
}
