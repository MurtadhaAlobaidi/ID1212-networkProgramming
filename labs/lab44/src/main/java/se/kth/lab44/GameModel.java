/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatefulEjbClass.java to edit this template
 */
package se.kth.lab44;

import java.util.Random;
import javax.ejb.Stateful;

/**
 * Lab4 part1
 * @author murtadha and @author Abdullah 
 */
@Stateful
public class GameModel {

    private int sessionsId = 0;
    private int attempts;
    private final int target;
    private int result = -100;

    public GameModel() {
        this.attempts = 0;
        this.target = new Random().nextInt(100) + 1;

    }

    public int getTarget() {
        return target;
    }

    public int getAttempts() {
        return attempts;
    }

    public void click() {
        this.attempts++;
    }

    public void endGame() {
        this.sessionsId++;
        this.attempts = 0;
    }

    public int getSessionsId() {
        return sessionsId;
    }

    /*public static String getGameResponse(String[] gamesResponse){
        return gamesResponse;
    }*/
    public void game(int guess) {
        if (guess == target) {
            this.result = 1;
        } else if (guess > target) {
            click();
            this.result = 0;
        } else {
            click();
            this.result = -1;
        }
    }
    
    public int getResult() {
        return this.result;
    }
    
   
}
