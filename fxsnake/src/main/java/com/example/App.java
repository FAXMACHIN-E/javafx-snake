package com.example;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {

    private final int WINDOW_HEIGHT = 800;
    private final int WINDOW_WIDTH = 1200;

    private final int SECTION_SIZE = 40;

    private final int START_POS_X = WINDOW_WIDTH/2;
    private final int START_POS_Y = WINDOW_HEIGHT/2;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        Image favicon = new Image(getClass().getResourceAsStream("/images/favicon.png"));
        
        stage.getIcons().add(favicon);
        stage.setTitle("SNAKE");

        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
        
        ArrayList<Rectangle> snakeList = new ArrayList<>();
        snakeList.add(new Rectangle(START_POS_X, START_POS_Y, SECTION_SIZE, SECTION_SIZE));
        snakeList.add(new Rectangle(START_POS_X+40, START_POS_Y, SECTION_SIZE, SECTION_SIZE));


        boolean gameOver = false;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> 
            {
                if(!gameOver){
                    for (int i = 0; i < snakeList.size(); i++) {
                        Rectangle currentSegment = snakeList.get(i);
                        currentSegment.setFill(Color.GREEN);
                        root.getChildren().add(currentSegment);
                    }
                }
            }
        ));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    } 
 
}