/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.entity;

/**
 *
 * @author samor
 */
public class UserScore {
    private String scoreMessage;
    private int userScore;
    
    
    public UserScore(String scoreMessage, int userScore){
        this.scoreMessage = scoreMessage;
        this.userScore = userScore;
        
    }

    public String getScoreMessage() {
        return scoreMessage;
    }

    public void setScoreMessage(String scoreMessage) {
        this.scoreMessage = scoreMessage;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }
    
    
}
