/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package losslesscompressionalgorithms;

import Algorithms.RLE;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Al-Hussein
 */
public class FXMLDocumentController implements Initializable {
    
    File file;
    
    @FXML
    private Label label;
    
    @FXML
    private Text path;
    
    @FXML
    private ImageView imageView;
    
    Image image;
    
    @FXML
    void btnNew(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        
        fileChooser.setInitialDirectory(new File("D:\\ITE 2017\\السنة الرابعة\\ملتيميديا\\project1\\LosslessCompressionAlgorithms"));
        file = fileChooser.showOpenDialog(null);
        
        if (file != null) {
            imageView.setImage(new Image(file.toURI().toString()));
            
        }
        
    }
    
    @FXML
    void btnCompress(ActionEvent event) throws FileNotFoundException, IOException {
        if (file != null) {
            RLE rle = new RLE(file);
            String s = rle.compress();
            
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            savefile.setInitialDirectory(new File("D:\\ITE 2017\\السنة الرابعة\\ملتيميديا\\project1\\LosslessCompressionAlgorithms"));
            
            File file2 = savefile.showSaveDialog(null);
            if (file2 != null) {
                FileUtils.writeStringToFile(file2, s, StandardCharsets.UTF_8);
            }
            
        }
    }
    
    @FXML
    void btndecompress(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        
        fileChooser.setInitialDirectory(new File("D:\\ITE 2017\\السنة الرابعة\\ملتيميديا\\project1\\LosslessCompressionAlgorithms"));
        file = fileChooser.showOpenDialog(null);
        
        if (file != null) {
            RLE rle = new RLE(file);
            byte[] bs = rle.decompress();            
            ByteArrayInputStream bais = new ByteArrayInputStream(bs);
            BufferedImage bi;
            
            try {
                FileUtils.writeByteArrayToFile(new File("now"), bs);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
    }
    
    private List<Integer> getAllpixel(List<Pair<Integer, Integer>> list) {
        List<Integer> l = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            
            Pair<Integer, Integer> pair = list.get(i);
            for (int j = 0; j < (int) (long) pair.getKey(); j++) {
                l.add(pair.getValue());
                
            }
            
        }
        return l;
    }
    
    private List<Integer> getListPixel(List<Double> list) {
        List<Integer> temp = new ArrayList<>();
        for (int i = 2; i < list.size() - 1; i += 2) {
            int count = list.get(i).intValue();
            int pix = list.get(i + 1).intValue();
            
            while (count-- > 0) {
                temp.add(pix);
            }
            
        }
        return temp;
    }
    
}
