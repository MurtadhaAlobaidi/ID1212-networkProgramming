import java.util.Random;

public class Game {
    int randomNumber;
    int attempts;

    public int getRandomNumber() {
        return randomNumber;
    }

    public int getAttempts() {
        return attempts;
    }

    public Game() {
        this.randomNumber = new Random().nextInt(100) + 1;
        attempts = 0;
    }

    public String guess(int guess) {
        this.attempts = this.attempts + 1;
        if (guess == this.randomNumber) {
            return "Correct";
        } else if (guess < this.randomNumber) {
            return "Higher";
        } else {
            return "Lower";
        }
    }
}