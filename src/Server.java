import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.*;

public class Server extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
  
            while (true) {
                // Receive number from the client
                double number = inputFromClient.readDouble();
                // Compute primeNumber
                String primeNumber = primeChecker(number);
                System.out.println(primeNumber);
                // Send primeNumber back to the client
                dataOutputStream.writeUTF(primeNumber);
                Platform.runLater(() -> {
                  ta.appendText("Number to check if prime received from client: " 
                    + number + '\n');
                  ta.appendText(primeNumber + "\n"); 
                });
        	}
        	}
      catch(IOException ex) {
        System.out.println("closing..");
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
  public String primeChecker(double number)
  {
  	String primeString = number +  " is prime number";
      for (int i = 2; i <= number / 2; i++) {
    	  if (number % i == 0) {
    		  primeString = number + " is not a prime number";
    		  break;
    	  }
      }
  	return primeString;
  }}