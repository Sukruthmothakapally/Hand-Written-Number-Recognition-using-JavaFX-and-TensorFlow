package numberrecognition;
//Importing required libraries
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Defining derived class of `Application` class - inheritance
public class NumberRecognizer extends Application{

    //Using concepts of Polymorphism and interface - `start()` method is an abstract method of `Application` class
    @Override
    public void start(Stage stage) throws Exception{ //Defining start method with proper exception handling
        //Loading details from the fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(NumberRecognizer.class.getResource("number-recognizer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600); //Creating an object of Scene class
        stage.setScene(scene); //Display scene on stage(window/screen)
        stage.setTitle("Hand Written Number Recognition Tool"); //Title of the window/screen
        stage.show(); //Show screen
    }
    //Main program to run the class
    public static void main(String[] args) {
        launch(args);

    }

}
