package user.content.process;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class SaveAudios {

    public void SaveAudio(MultipartFile multipartFile, String filename) throws IOException {
        final int BUFFERSIZE = 4 * 1024;
        try (
                ByteArrayInputStream fin = new ByteArrayInputStream(multipartFile.getBytes());
                FileOutputStream fout = new FileOutputStream(new File("/mnt/94C42DFDC42DE1EA/user/data/audios/" + filename + "." + "mp3"));) {
            byte[] buffer = new byte[BUFFERSIZE];
            int bytesRead;
            while (fin.available() != 0) {
                bytesRead = fin.read(buffer);
                fout.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong! Reason: " + e.getMessage());
        }
    }

}
