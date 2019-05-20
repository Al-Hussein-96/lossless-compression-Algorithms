/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Al-Hussein
 */
public class Huffman {

    Map<Byte, String> encoder = new HashMap<>();
    Map<String, Byte> decoder = new HashMap<>();
    int[] frequency;
    File file;

    public Huffman(File file) {
        this.file = file;
        countFrequency();
        encodeFile();
        generateKey();
        generateEncodedFile();
    }

    /*
    Count the frequency of various ASCII characters
     */
    private void countFrequency() {
        frequency = new int[256];
        byte[] byteData = new byte[0];
        try (FileInputStream fis = new FileInputStream(file)) {
            byteData = IOUtils.toByteArray(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < byteData.length; i++) {
            if (byteData[i] > 256 || byteData[i] < -256) {
                System.out.println("byte: " + byteData[i]);
            }
        }
        for (int i = 0; i < byteData.length; i++) {
            frequency[byteData[i] + 128]++;
        }

//        for (int i = 0; i < 256 * 2; i++) {
//            System.out.println("frequency: " + i + " : " + frequency[i]);
//        }
    }

    private HuffmanNode encodeFile() {
        int len = 256;

        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(len, (HuffmanNode o1, HuffmanNode o2) -> {
            return (int) (o1.getFrequency() - o2.getFrequency());
        });

        for (int i = -127; i < 128; i++) {
            if (frequency[i + 128] != 0) {
                priorityQueue.add(new HuffmanNode((byte) i, frequency[i + 128]));
            }
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode node1 = priorityQueue.peek();
            priorityQueue.poll();
            HuffmanNode node2 = priorityQueue.peek();
            priorityQueue.poll();
            node1.setCode("0");
            node2.setCode("1");

            HuffmanNode node3 = new HuffmanNode();
            node3.setOk(true);
            node3.setFrequency(node1.getFrequency() + node2.getFrequency());
            node3.setlChild(node1);
            node3.setrChild(node2);

            priorityQueue.add(node3);
        }
        HuffmanNode root = priorityQueue.peek();
        priorityQueue.poll();

        traverse(root, "");

        System.out.println(root.getByteValue());

//        System.out.println(encoder.values());
        for (int i = -127; i < 128; i++) {
            if (encoder.containsKey((byte) i)) {
                System.out.println(i + " : " + encoder.get((byte) i));
            }
        }
        return root;
    }

    /*
     Generates the key file
     */
    private void generateKey() {
        System.out.println("Inside generateKey()...");
        Set<Map.Entry<Byte, String>> set = encoder.entrySet();
        StringBuilder contents = new StringBuilder();
        for (Map.Entry<Byte, String> me : set) {
            contents.append(me.getKey()).append(",").append(me.getValue()).append("\n");
        }
        try {
            FileUtils.writeStringToFile(new File("encoder.csv"), contents.toString(), StandardCharsets.UTF_8);

//        try (FileWriter fw = new FileWriter(destinationPath + "\\key.csv")) {
//            fw.write(contents.toString());
//        } catch (IOException ex) {
//            Logger.getLogger(DataCompression.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (IOException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Generates the encoded file
     */
    private void generateEncodedFile() {
        StringBuilder contents = new StringBuilder();

        byte[] byteData = new byte[0];
        try (FileInputStream fis = new FileInputStream(file)) {
            byteData = IOUtils.toByteArray(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RLE.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < byteData.length; i++) {
            contents.append(encoder.get(byteData[i]));
        }

        try {
            FileUtils.writeStringToFile(new File("encoded.txt"), contents.toString(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void traverse(HuffmanNode node, String s) {
        if (node.getlChild() == null && node.getrChild() == null && !node.isOk()) {
            //     System.out.println("node byte: " + node.getByteValue());
            encoder.put(node.getByteValue(), s);
            return;
        }

        if (node.getCode() != null) {
            s += node.getCode();
        }

        traverse(node.getlChild(), s);
        traverse(node.getrChild(), s);

    }

}
