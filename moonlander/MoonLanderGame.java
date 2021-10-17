package com.codegym.games.moonlander;

import com.codegym.engine.cell.*;


public class MoonLanderGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape;
    private GameObject platform;
    private boolean isUpPressed, isLeftPressed, isRightPressed;
    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();
        showGrid(false);
    }

    private void createGame(){
        score = 1000;
        setTurnTimer(50);
        isGameStopped = false;
        isRightPressed = false; isUpPressed = false;isLeftPressed = false;
        createGameObjects();
        drawScene();
    }

    private void drawScene(){
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                setCellColor(x,y,Color.BLACK);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }

    private void createGameObjects(){
        rocket = new Rocket(WIDTH/2,0);
        platform = new GameObject(23, HEIGHT - 1, ShapeMatrix.PLATFORM);
        landscape = new GameObject(0,25, ShapeMatrix.LANDSCAPE);
    }

    @Override
    public void onTurn(int i){
        rocket.move(isUpPressed,isLeftPressed,isRightPressed);
        check();
        if(score > 0){
            score--;
        }
        setScore(score);
        drawScene();
    }

    @Override
    public void setCellColor(int x, int y, Color color){
        if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT){
            // do nothing
        }
        else{
            super.setCellColor(x, y, color);
        }
    }

    @Override
    public void onKeyPress(Key key){
        if(key == Key.SPACE && isGameStopped == true){
            createGame();
        }
        else{
            if(key == Key.UP){
                isUpPressed = true;
            }
            if(key == Key.LEFT){
                isLeftPressed = true;
                isRightPressed = false;
            }
            if(key == Key.RIGHT) {
                isRightPressed = true;
                isLeftPressed = false;
            }
        }
    }

    @Override
    public void onKeyReleased(Key key){
        if(key == Key.UP){
            isUpPressed = false;
        }
        if(key == Key.LEFT){
            isLeftPressed = false;
        }
        if(key == Key.RIGHT) {
            isRightPressed = false;
        }
    }

    private void check(){
        if(rocket.isCollision(platform) && rocket.isStopped()){
            win();
        }
        if(rocket.isCollision(landscape) && !(rocket.isCollision(platform) && rocket.isStopped())){
            gameOver();
        }
    }

    private void win(){
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "You Have Won!",Color.GREEN,75);
        stopTurnTimer();
    }

    private void gameOver(){
        score = 0;
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "You Have Lost..",Color.RED,75);
        stopTurnTimer();
    }




    
}
