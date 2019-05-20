package Algorithms;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Al-Hussein
 */
public class RLE {

    String[] imageExtension = {"png", "jpg", "jpeg", "gif", "bmp"};

    File file;

    public RLE(File file) {
        this.file = file;
    }

    public String compress() {
        byte[] byteData = new byte[0];
        try {
            try (FileInputStream fis = new FileInputStream(file)) {
                byteData = IOUtils.toByteArray(fis);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("length byte before: " + byteData.length);

        List<Integer> outRLE = new ArrayList<>();

        int last = byteData[0];
        int count = 1;
        for (int i = 1; i < byteData.length; i++) {
            if (byteData[i] == last) {
                count++;
            } else {
                outRLE.add(count);
                outRLE.add(last);

                last = byteData[i];
                count = 1;
            }

        }
        outRLE.add(count);
        outRLE.add(last);

        return new Gson().toJson(outRLE);

    }

    public byte[] decompress() {
        byte[] byteData = getByteData(false);
        return byteData;

    }

    private boolean isImage() {
        String extension = FilenameUtils.getExtension(file.getPath());
        return Arrays.stream(imageExtension).anyMatch(extension::equals);
        // return true;
    }

    private byte[] getByteData(boolean ok) {
        byte[] data = new byte[10000000];
        if (ok) {
            try (FileInputStream fis = new FileInputStream(file)) {
                data = IOUtils.toByteArray(fis);
                return data;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try {
                int c = 0;
                String compressImageString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                List<Double> list = new ArrayList<>(new Gson().fromJson(compressImageString, ArrayList.class));
                for (int i = 0; i < list.size() - 1; i += 2) {
                    int count = (int) (double) list.get(i);
                    byte last = (byte) (double) list.get(i + 1);
                    for (int j = 0; j < count; j++) {
                        data[c++] = last;
                    }
                }
                data = Arrays.copyOf(data, c);
                return data;

            } catch (IOException ex) {
                Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
