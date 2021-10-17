package com.codegym.games.snake;
import com.codegym.engine.cell.*;
import java.util.*;

public class Snake {
    
    public List<GameObject> snakeParts = new ArrayList();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    public Direction direction;
    
    
    public Snake(int x, int y){
        direction = Direction.LEFT;
        GameObject first = new GameObject(x,y);
        GameObject second = new GameObject(x+1,y);
        GameObject third  = new GameObject(x+2,y);
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }
    
    
    public void setDirection(Direction direction){
        if(this.direction == Direction.RIGHT &&snakeParts.get(0).x == snakeParts.get(1).x) {
        }
        else if(this.direction == Direction.LEFT &&snakeParts.get(0).x == snakeParts.get(1).x) {
        }
        else if(this.direction == Direction.UP &&snakeParts.get(0).y == snakeParts.get(1).y) {
        }
        else if(this.direction == Direction.DOWN &&snakeParts.get(0).y == snakeParts.get(1).y) {
        }
        
        else if(this.direction == Direction.LEFT && direction == Direction.RIGHT ||
            this.direction == Direction.RIGHT && direction == Direction.LEFT){
                // do not change
        }
        else if(this.direction == Direction.UP && direction == Direction.DOWN ||
            this.direction == Direction.DOWN && direction == Direction.UP){
                // do not change 
            }
            else{
                this.direction = direction;
        }
    }
    
    public int getLength(){
        return snakeParts.size();
    }
    
    public void move(Apple apple){
        GameObject newHead = createNewHead();
        boolean check = checkCollision(newHead);
        if(check == true){
            isAlive = false;
        }
        if(newHead.x == apple.x && newHead.y == apple.y){
            apple.isAlive = false;
        }
        if (newHead.x<0 || newHead.y<0 || newHead.x>=SnakeGame.WIDTH || newHead.y>=SnakeGame.HEIGHT) {
            isAlive = false;
        }
        else {
            if(isAlive == false){
                
            }
            else{
                snakeParts.add(0, newHead);
                if(newHead.x == apple.x && newHead.y == apple.y){
                    apple.isAlive = false;
                }
                else{
                    removeTail(); 
                }
            }
        }
    }
    
    public GameObject createNewHead(){
        GameObject newHead;
        if(direction == Direction.LEFT){
           newHead = new GameObject(snakeParts.get(0).x-1,
           snakeParts.get(0).y);
        }
        else if (direction == Direction.RIGHT){
           newHead = new GameObject(snakeParts.get(0).x+1,
           snakeParts.get(0).y);
        }
        else if (direction == Direction.UP){
           newHead = new GameObject(snakeParts.get(0).x,
           snakeParts.get(0).y-1);
        }
        else{
           newHead = new GameObject(snakeParts.get(0).x,
           snakeParts.get(0).y+1);
        }
        return newHead;
    }
    
    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }
    
    public boolean checkCollision(GameObject gameObject){
        for(GameObject segment : snakeParts) {
            if(gameObject.x == segment.x && gameObject.y == segment.y){
                return true;
            }
        }
        return false;
    }
        
    public void draw(Game game){
        if(isAlive == true){
        for (GameObject snakePart : snakeParts) {
            if(snakeParts.indexOf(snakePart) == 0){
                game.setCellValueEx(snakeParts.get(0).x, 
                snakeParts.get(0).y,Color.NONE,HEAD_SIGN, Color.BLACK, 75);
            } else {
                game.setCellValueEx(snakePart.x, snakePart.y,Color.NONE, BODY_SIGN, 
                Color.BLACK, 75);
            }
            }
        }
        else{
        for (GameObject snakePart : snakeParts) {
            if(snakeParts.indexOf(snakePart) == 0){
                game.setCellValueEx(snakeParts.get(0).x, 
                snakeParts.get(0).y,Color.NONE,HEAD_SIGN, Color.RED, 75);
            } else {
                game.setCellValueEx(snakePart.x, snakePart.y,Color.NONE, BODY_SIGN, 
                Color.RED, 75);
            }
            }
        }    
    }
    
}