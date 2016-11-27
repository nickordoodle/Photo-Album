package nscherer30.gmail.com.androidphotoalbum11.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * @author Patel, Kartik (157004332) 
 * @author Scherer, Nicholas (150004490)
 * 
 */

public class PhotoAlbum extends Application {

	private static Stage primaryStage; // **Declare static Stage**

	public static Stage getPrimaryStage() {
		return PhotoAlbum.primaryStage;
	}

	@Override
	public void start(Stage primaryStage) {

		FXMLLoader gui = new FXMLLoader();
		gui.setLocation(getClass().getResource("/view/login.fxml"));

		Parent root = null;
		try {
			root = gui.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		PhotoAlbum.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		primaryStage.setTitle("Log In");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
