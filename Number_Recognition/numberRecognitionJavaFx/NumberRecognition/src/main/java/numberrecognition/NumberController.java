package numberrecognition;
//Importing required libraries
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

public class NumberController {

    @FXML //importing Canvas from fxml file
    private Canvas canvas; //Keeping private for encapsulation

    @FXML //importing CheckBox from fxml file
    private CheckBox eraser; //Keeping private for encapsulation

    @FXML //importing label from fxml file
    private Label welcomeText; //Keeping private for encapsulation

    //Method to enable writing on the canvas
    public void initialize(){
        GraphicsContext g = canvas.getGraphicsContext2D(); //Using private variable in public method - concept of encapsulation
        g.setFill(Color.WHITE);
        g.fillRect(0,0,canvas.getWidth(), canvas.getHeight());

        canvas.setOnMouseDragged(e -> {
            double size = 25.0;
            double x = e.getX() - size/2;
            double y = e.getY() - size/2;

            //if-else statement to toggle between eraser and pencil/brush
            if(eraser.isSelected()){ //Using private variable in public method - concept of encapsulation
                g.clearRect(x, y, size, size);
            }
            else{
                g.setFill(Color.BLACK);
                g.fillRect(x, y, size, size);
            }
        });
    }

    //Method for menuItem `Save`
    public void onSave(){
        //Implementing try-catch function
        try{

            Image tmp =  SwingFXUtils.fromFXImage(canvas.snapshot(null, null), null).getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            BufferedImage scaledImg = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
            Graphics graphics = scaledImg.getGraphics();
            graphics.drawImage(tmp, 0, 0, null);
            graphics.dispose();
            //Saving the snapshot in png format with filename `number.png`
            ImageIO.write(scaledImg, "png", new File("number.png"));

        }
        //Printing error if snapshot cannot be captured using catch block
        catch(Exception e){
            System.out.println("Failed to save image: " +e);
        }
    }

    //Method for menuItem `Clear`
    public void onClear(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0,0,canvas.getHeight(), canvas.getWidth()); //Using clearRect() method for clearing canvas
    }

     public void onPredict()
    {
        Process process=null;

        try{
            // Start the Python script as a new process
       ProcessBuilder builder= new ProcessBuilder("python", "D:\\Python_problems\\Number_recognition_tensorflow\\Number_recognition_tensorflow.py" );
       process=builder.start();

        }catch(Exception e) {
            System.out.println("Unable to load python command" + e.toString());
        }
        InputStream stdout = process.getInputStream();
        // Read the output of the Python script
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
        String line;

        try{
            while((line = reader.readLine()) != null){
                //System.out.println(line);
                welcomeText.setText(line);
                welcomeText.setMaxWidth(Double.MAX_VALUE);
                welcomeText.setFont(new Font("Arial",24));
                welcomeText.setAlignment(Pos.CENTER);
            }

        }catch(IOException e){
            System.out.println("Exception in reading output"+ e.toString());
        }
    }
}
