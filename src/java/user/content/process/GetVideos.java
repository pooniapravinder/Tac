package user.content.process;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GetVideos {

    public byte[] getVideo(String filename) throws IOException {
        final int BUFFERSIZE = 4 * 1024;
        byte[] bytes;
        File file = new File("F:\\videos\\post\\" + filename);
        FileInputStream fin = new FileInputStream(file);
        ByteArrayOutputStream fw = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFERSIZE];
        int bytesRead;
        while (fin.available() != 0) {
            bytesRead = fin.read(buffer);
            fw.write(buffer, 0, bytesRead);
        }
        fw.flush();
        fw.close();
        bytes = fw.toByteArray();
        return bytes;
    }
}
