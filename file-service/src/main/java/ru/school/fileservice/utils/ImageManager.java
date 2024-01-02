package ru.school.fileservice.utils;

import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ImageManager {
    private final FileManager fileManager;

    private BufferedImage rescale(BufferedImage source, int size) {
        return Scalr.resize(source, Scalr.Method.QUALITY,
                size, Scalr.OP_ANTIALIAS);
    }

    public byte[] getImg(String key, Integer size) throws IOException {
        byte[] data = fileManager.byteArray(key);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        BufferedImage image = ImageIO.read(inputStream);
        BufferedImage scaled = rescale(image, size);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        boolean written = ImageIO.write(scaled, "png", outStream);
        if (!written)
            written = ImageIO.write(scaled, "jpg", outStream);
        if (!written)
            throw new IOException("Can't write image");

        return outStream.toByteArray();
    }
}
