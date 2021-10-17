package com.codegym.games.game2048;

import com.codegym.engine.cell.*;

import java.util.HashMap;

public class Game2048 extends Game {

    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score = 0;


    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }

    private void createGame() {
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }

    private void drawScene() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                setCellColoredNumber(y, x, gameField[x][y]);
            }
        }

    }

    private boolean compressRow(int[] row){
        boolean shifted = false;
        for (int i = 0; i < SIDE - 1; i++) {
            if (row[i] == 0) {
                for (int j = i + 1; j < SIDE; j++) {
                    if (row[j] != 0) {
                        shifted = true;
                        row[i] = row[j];
                        row[j] = 0;
                        break;
                    }
                }
            }

        }
        return shifted;
    }

    private boolean mergeRow(int[] row){
        boolean shifted = false;
        for (int i = 0; i < 4 - 1; i++) {
            int j = i+1;
            if (row[j] == row[i] && row[i] != 0) {
                row[i] = row[j] * 2;
                score += row[i];
                setScore(score);
                row[j] = 0;
                shifted = true;
            }
        }
        return shifted;
    }

    @Override
    public void onKeyPress(Key key){
        if(key == Key.SPACE) {
            isGameStopped = false;
            score = 0;
            setScore(score);
            createGame();
            drawScene();
        }
        if(!canUserMove()){
            gameOver();
        }
        else if (isGameStopped){
            // do nothing
        }
        else{
            if(key == Key.LEFT){
                moveLeft();
                drawScene();
            }
            if(key == Key.RIGHT){
                moveRight();
                drawScene();
            }
            if(key == Key.UP){
                moveUp();
                drawScene();
            }
            if(key == Key.DOWN){
                moveDown();
                drawScene();
            }
        }
    }

    private void moveLeft(){
        boolean flag = false;
        boolean move, merger;
        for(int[] row : gameField){
            move = compressRow(row);
            merger = mergeRow(row);
            compressRow(row);
            if((move || merger) && !flag ){
                createNewNumber();
                flag = true;
            }
        }
    }

    private void moveRight(){
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }

    private void moveDown(){
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }

    private void moveUp(){
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }

    private void rotateClockwise(){
        int[][] ret = new int[SIDE][SIDE];
        for(int i = 0; i < SIDE; i++){
            for(int j = 0; j < SIDE; j++){
                ret[i][j] = gameField[SIDE-j-1][i];
            }
        }

        gameField =  ret;
    }

    private int getMaxTileValue(){
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < SIDE; i++){
            for(int j = 0; j < SIDE; j++){
                if(gameField[i][j] > max){
                    max = gameField[i][j];
                }
            }
        }
        return max;
    }

    private boolean canUserMove(){
        for(int i = 0; i < SIDE; i++){
            for(int j = 0; j < SIDE; j++){
                if (gameField[i][j] == 0)
                {
                    return true;
                }
                //this if statement checks if there is an above cell has it got the same value in it.
                if((i-1) > 0 && (gameField[i][j] == gameField[i-1][j]))
                {
                    return true;
                }
                //this checks for DOWN
                if ((i+1) < SIDE && (gameField[i][j] == gameField[i+1][j]))
                {
                    return true;
                }
                //this checks for RIGHT
                if ((j+1) < SIDE && (gameField[i][j] == gameField[i][j+1]))
                {
                    return true;
                }
                //this checks for LEFT
                if ((j-1)>0 && (gameField[i][j] == gameField[i][j-1]))
                {
                    return true;
                }
            }

        }
        return false;
    }

    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.RED, "You have Lost", Color.WHITE, 75);
    }
    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "You have Won", Color.WHITE, 75);
    }

    private Color getColorByValue(int value) {
        if (value == 0) {
            return Color.WHITE;
        } else if (value == 2) {
            return Color.PINK;
        } else if (value == 4) {
            return Color.RED;
        } else if (value == 8) {
            return Color.ORANGE;
        } else if (value == 16) {
            return Color.YELLOW;
        } else if (value == 32) {
            return Color.GREEN;
        } else if (value == 64) {
            return Color.BLUE;
        } else if (value == 128) {
            return Color.TEAL;
        } else if (value == 256) {
            return Color.BEIGE;
        } else if (value == 512) {
            return Color.LAVENDER;
        } else if (value == 1024) {
            return Color.DARKGRAY;
        } else {
            return Color.PURPLE;
        }
    }

    private void setCellColoredNumber(int x, int y, int value) {
        if (value > 0) {
            setCellValueEx(x, y, getColorByValue(value), Integer.toString(value));
        }
        else{
            setCellValueEx(x, y, getColorByValue(value), "");
        }
    }

    private void createNewNumber() {
        int x = getRandomNumber(SIDE);
        int y = getRandomNumber(SIDE);
        if (getMaxTileValue() == 2048) {
            win();
        }
        else if (gameField[x][y] > 0) {
            createNewNumber();
        } else {
            int odds = getRandomNumber(10);
            if (odds == 9) {
                gameField[x][y] = 4;
            } else {
                gameField[x][y] = 2;
            }
        }

    }
}
