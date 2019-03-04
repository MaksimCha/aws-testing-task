package common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.stream.Stream;

public class FileProvider {

    public static Stream<File> uploadFile(int fileNameLong, int fileLong) {
        byte[] array = new byte[fileNameLong]; // length is bounded by 7
        new Random().nextBytes(array);
        byte[] fileByteArray = new byte[fileLong]; // length is bounded by 7
        new Random().nextBytes(fileByteArray);
        String generatedName = new String(array, Charset.forName("UTF-8"));
        File file = new File("*/src/main/java/resources/files/" + generatedName + ".txt");
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(fileByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.of(file);
    }

    public static Stream<File> uploadFile(String pathName){
        return Stream.of(new File(pathName));
    }
}
