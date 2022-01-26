package user.content.process;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GetAudios {

    public byte[] getAudio(String path) throws IOException {
        final int BUFFERSIZE = 4 * 1024;
        byte[] bytes;
        File file = new File("/mnt/94C42DFDC42DE1EA/user/data/audios/" + path + ".mp3");
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
