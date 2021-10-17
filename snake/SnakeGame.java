package com.codegym.games.snake;
import com.codegym.engine.cell.*;

public class SnakeGame extends Game {
    
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Apple apple;
    private int turnDelay;
    private Snake snake;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;
    
    
    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    
    @Override
    public void onTurn(int x){

        snake.move(apple);
        if(apple.isAlive == false){
            score +=5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if(snake.isAlive == false){
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }
    

    
    private void createNewApple(){
        apple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
        boolean check = snake.checkCollision(apple);
        if(check == true){
            createNewApple();
        }
    }
    
    @Override
    public void onKeyPress(Key key){
        if(key == Key.SPACE && isGameStopped == true){
            createGame();
        }
        if(key == key.LEFT){
            snake.setDirection(Direction.LEFT);
        }
        if(key == key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        }
        if(key == key.UP){
            snake.setDirection(Direction.UP);
        }
        if(key == key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
    }
    
    private void createGame(){
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH/2, HEIGHT/2);
        turnDelay = 300;
        setTurnTimer(turnDelay);
        createNewApple();
        isGameStopped = false;
        drawScene();
    }
    
    private void gameOver(){
        isGameStopped = true;
        stopTurnTimer();
        showMessageDialog(Color.RED,"You have lost",Color.BLACK,75);
    }
    
    private void win(){
        isGameStopped = true;
        stopTurnTimer();
        showMessageDialog(Color.GREEN,"You have won",Color.BLACK,75);
    }
    
    private void drawScene(){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j< HEIGHT; j++){
                setCellValueEx(i,j,Color.DARKSEAGREEN,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }
    
}