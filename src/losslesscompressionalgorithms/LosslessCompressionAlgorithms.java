/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package losslesscompressionalgorithms;

import Algorithms.Huffman;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Al-Hussein
 */
public class LosslessCompressionAlgorithms extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        File file = new File("3square.bmp");
        Huffman huffman = new Huffman(file);
        
        

        //   launch(args);
    }

}
/*
package Algorithms;

import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


public class RLE {

    String[] imageExtension = {"png", "jpg", "jpeg", "gif"};

    File file;

    public RLE(File file) {
        this.file = file;
    }

    public byte[] compress() {
        ByteArrayOutputStream dest = new ByteArrayOutputStream();

        byte[] byteData = getByteData();
        for (int i = 0; i < 25; i++) {
            System.out.println("pixel: " + byteData[i]);
        }

        if (isImage()) {
            String base64String = Base64.getEncoder().encodeToString(byteData);
            System.out.println("Line: " + base64String.length());

            List<Integer> outRLE = new ArrayList<>();

            int c = 0;
            int last = byteData[0];
            int count = 1;
            for (int i = 0; i < byteData.length; i++) {
                if (byteData[i] == last) {
                    count++;
                } else {
                    //   System.out.println("count: " + count + " , last: " + last);
//                    outRLE.add(count);
//                    outRLE.add(last);
                    dest.write((byte) count);
                    dest.write((byte) last);

                    last = byteData[i];
                    count = 1;
                }

            }
//            outRLE.add(count);
//            outRLE.add(last);
            dest.write((byte) count);
            dest.write((byte) last);
            //   System.out.println("length: " + outRLE.length + " , " + byteData.length);
            return dest.toByteArray();

//            try {
//                FileUtils.writeByteArrayToFile(new File("compress.hos"), outRLE);
//            } catch (IOException ex) {
//                Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else {
            return null;
        }
    }

    public byte[] decompress() {
        byte[] byteData = getByteData();
        for (int i = 0; i < 25; i++) {
            System.out.println("byte: " + byteData[i]);
        }

        return null;

    }

    private boolean isImage() {
        String extension = FilenameUtils.getExtension(file.getPath());
        return Arrays.stream(imageExtension).anyMatch(extension::equals);
        // return true;
    }

    private byte[] getByteData() {
        byte[] data;
        if (isImage()) {
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(file);
                data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

                return data;
            } catch (IOException ex) {
                Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                data = FileUtils.readFileToByteArray(file);
                return data;
            } catch (IOException ex) {
                Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}


 */
