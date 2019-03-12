package dataProviders;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Stream;

public class FileProvider {

    private static final Logger log = LogManager.getLogger(FileProvider.class);
    public static Stream<File> generateFile() {
        int length = 10;
        String fileName = RandomStringUtils.random(length, true, false);
        byte[] fileByteArray = new byte[50]; // length is bounded by 50
        new Random().nextBytes(fileByteArray);
        File file = new File("src/main/resources/" + fileName + ".txt");
        log.info("Filename: " + fileName);
        try {
            log.info("Creating new file is done: " + file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(fileByteArray);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return Stream.of(file);
    }
}
