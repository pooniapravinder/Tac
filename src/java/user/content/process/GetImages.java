package user.content.process;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GetImages {

    public byte[] getFullSize(String filename) throws IOException {
        BufferedImage image;
        byte[] imageInByte;
        try {
            image = ImageIO.read(new File("F:\\images\\thumbnails\\" + filename));
        } catch (IOException ioe) {
            System.out.println("Cannot read the image");
            throw ioe;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        return imageInByte;
    }

    public byte[] getProfileImage(String filename) throws IOException {
        BufferedImage image;
        byte[] imageInByte;
        try {
            image = ImageIO.read(new File("F:\\images\\profile\\" + filename));
        } catch (IOException ioe) {
            System.out.println("Cannot read the image");
            throw ioe;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        return imageInByte;
    }

    public byte[] getThumbnail(String path, int thumbnail) throws IOException {
        int widthToScale, heightToScale, x, y;
        BufferedImage image, resizedImage;
        Graphics2D g;
        try {
            image = ImageIO.read(new File("/mnt/94C42DFDC42DE1EA/user/data/images/" + path + ".jpg"));
        } catch (IOException ioe) {
            System.out.println("Cannot read the image");
            throw ioe;
        }
        byte[] imageInByte;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (image.getWidth() > image.getHeight()) {
                heightToScale = (int) (1.1 * thumbnail);
                widthToScale = (int) ((heightToScale * 1.0) / image.getHeight() * image.getWidth());
            } else {
                widthToScale = (int) (1.1 * thumbnail);
                heightToScale = (int) ((widthToScale * 1.0) / image.getWidth() * image.getHeight());
            }
            int imageType = image.getType();
            if(imageType == 0) imageType = 5;
            resizedImage = new BufferedImage(widthToScale, heightToScale, BufferedImage.TYPE_INT_RGB);
            g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(image, 0, 0, widthToScale, heightToScale, null);
            g.dispose();
            x = (resizedImage.getWidth() - thumbnail) / 2;
            y = (resizedImage.getHeight() - thumbnail) / 2;
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Width of new thumbnail is bigger than original image");
            }
            ImageIO.write(resizedImage.getSubimage(x, y, thumbnail, thumbnail), "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        } catch (IOException ioe) {
            System.out.println("Cannot create thumbnail image");
            throw ioe;
        }
        return imageInByte;
    }
}
