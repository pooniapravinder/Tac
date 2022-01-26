package user.content.process;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

public class SaveImages {

    public void SaveImage(MultipartFile multipartFile, String filename) throws IOException {
        byte[] imageInByte;
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        }
        InputStream in = new ByteArrayInputStream(imageInByte);
        BufferedImage bImageFromConvert = ImageIO.read(in);
        ImageIO.write(bImageFromConvert, "jpg", new File("F:\\" + "images\\thumbnails\\" + filename));
    }

    public void SaveProfileImage(MultipartFile multipartFile, String filename) throws IOException {
        byte[] imageInByte;
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        }
        InputStream in = new ByteArrayInputStream(imageInByte);
        BufferedImage bImageFromConvert = ImageIO.read(in);
        ImageIO.write(bImageFromConvert, "jpg", new File("F:\\" + "images\\profile\\" + filename));
    }
}
