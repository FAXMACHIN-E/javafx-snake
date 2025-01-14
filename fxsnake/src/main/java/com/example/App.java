package com.example;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    boolean gameOver = false;
    KeyCode direction = KeyCode.RIGHT;
    
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
        
        ArrayList<Rectangle> snake = new ArrayList<>();
        //Creates the first segment
        growSnake(snake, root);

        Rectangle food = new Rectangle(START_POS_X+SECTION_SIZE*2, START_POS_Y, SECTION_SIZE, SECTION_SIZE);
        food.setFill(Color.CRIMSON);
        root.getChildren().add(food);

        moveFood(food, snake);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4), e -> 
            {
                if(!gameOver){
                    scene.setOnKeyPressed((KeyEvent event) -> {
                        switch (event.getCode()) {
                            case UP:
                                direction = KeyCode.UP;
                                break;
                            case DOWN:
                                direction = KeyCode.DOWN;
                                break;
                            case LEFT:
                                direction = KeyCode.LEFT;
                                break;
                            case RIGHT:
                                direction = KeyCode.RIGHT;
                                break;
                        }
                    });

                    moveSnake(snake, direction, food, root);

                }
            }
        ));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    } 


    public void growSnake(ArrayList<Rectangle> snake, Group root){
        System.out.println(snake.size());
        if(snake.size()==0){
            snake.add(new Rectangle(START_POS_X, START_POS_Y, SECTION_SIZE, SECTION_SIZE));
            snake.get(0).setFill(Color.GREEN);
            root.getChildren().add(snake.get(0));
        }
        else{
            snake.add(new Rectangle(snake.get(snake.size()-1).getX(), snake.get(snake.size()-1).getY(), SECTION_SIZE, SECTION_SIZE));
            snake.get(snake.size()-1).setFill(Color.GREEN);
            root.getChildren().add(snake.get(snake.size()-1));
        }
    }

    public void moveSnake(ArrayList<Rectangle> snake, KeyCode direction, Rectangle food, Group root){
        //Gets the current coordinate of the head
        Rectangle head = snake.get(0);
        double[] headCoordinate = {head.getX(), head.getY()};

        //Moves head depending on the current direction
        switch (direction) {
            case UP:
                head.setY(head.getY() - SECTION_SIZE);
                break;
            case DOWN:
                head.setY(head.getY() + SECTION_SIZE);
                break;
            case LEFT:
                head.setX(head.getX() - SECTION_SIZE);
                break;
            case RIGHT:
                head.setX(head.getX() + SECTION_SIZE);
                break;
            default:
                break;
        }

        //Sets the position of each segement to the segment in front of it after the head moves
        if(snake.size()>1){
            for (int i = 1; i < snake.size(); i++) {
                double tempX = snake.get(i).getX();
                double tempY = snake.get(i).getY();

                snake.get(i).setX(headCoordinate[0]);
                snake.get(i).setY(headCoordinate[1]);

                headCoordinate[0] = tempX;
                headCoordinate[1] = tempY;
            }
        }

        if(head.getX()==food.getX() && head.getY()==food.getY()){
            growSnake(snake, root);
            moveFood(food, snake);
        }


    }

    public void moveFood(Rectangle food, ArrayList<Rectangle> snake){
        Random random = new Random();
        boolean collided = false;

        do{
            food.setX(random.nextInt(WINDOW_WIDTH/SECTION_SIZE-1)*SECTION_SIZE);
            food.setY(random.nextInt(WINDOW_HEIGHT/SECTION_SIZE-1)*SECTION_SIZE);

            collided = false;

            for (int i = 0; i < snake.size(); i++) {
                if(snake.get(i).getX()==food.getX() && snake.get(i).getY()==food.getY()){
                    collided = true;
                }
            }
        }while(collided);
    }


 
}