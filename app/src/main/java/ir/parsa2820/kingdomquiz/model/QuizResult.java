package ir.parsa2820.kingdomquiz.model;

public class QuizResult {
    private int score;

    public QuizResult() {
        score = 0;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }
}
