package ru.school.fileservice.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.coyote.BadRequestException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {
    @Value("${file.root-path}")
    private String ROOT;

    private Path fileDir(String key) {
        return Paths.get(ROOT, key);
    }

    private File filePath(String key, Integer size) {
        return size == null ? filePath(key) : Paths.get(ROOT, key, size.toString()).toFile();
    }

    private File filePath(String key) {
        return Paths.get(ROOT, key, "src").toFile();
    }

    public boolean fileNotExists(String key) {
        return !fileDir(key).toFile().exists();
    }

    private void createFolder(String key) throws IOException {
        if (fileNotExists(key))
            Files.createDirectory(fileDir(key));
    }

    public void save(byte[] data, String key) throws IOException {
        save(data, key, null);
    }

    private void save(byte[] data, String key, Integer size) throws IOException {
        createFolder(key);

        try (FileOutputStream stream = new FileOutputStream(filePath(key, size))) {
            stream.write(data);
        }
    }

    public byte[] getFile(String key) throws FileNotFoundException {
        return getFile(key, null);
    }

    private byte[] getFile(String key, Integer size) throws FileNotFoundException {
        try (FileInputStream stream = new FileInputStream(filePath(key, size))) {
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new FileNotFoundException("No such file");
        }
    }

    public byte[] getImage(String key, Integer size) throws IOException {
        if (fileNotExists(key))
            throw new FileNotFoundException("No such file");

        try {
            return getFile(key, size);
        } catch (FileNotFoundException ignored) {
        }

        BufferedImage image = ImageIO.read(filePath(key));
        if (image == null)
            throw new BadRequestException("File is not an image");

        BufferedImage scaled = rescale(image, size);
        byte[] result = getImageBytes(scaled);
        save(result, key, size);

        return result;
    }

    private byte[] getImageBytes(BufferedImage img) throws IOException {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            boolean written = ImageIO.write(img, "jpg", outStream);
            if (!written)
                written = ImageIO.write(img, "png", outStream);
            if (!written)
                throw new IOException("Can't write image");

            return outStream.toByteArray();
        }
    }

    private BufferedImage rescale(BufferedImage source, int size) {
        return Scalr.resize(source, Scalr.Method.QUALITY,
                size, Scalr.OP_ANTIALIAS);
    }

    public String generateKey(byte[] data, Long owner) {
        return owner == null ? DigestUtils.md5Hex(data) :
                DigestUtils.md5Hex(data) + owner;
    }

    public void remove(String key) {
        try {
            FileUtils.deleteDirectory(fileDir(key).toFile());
        } catch (IOException ignored) {
        }
    }
}
