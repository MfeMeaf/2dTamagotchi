package se.iths.felix.tamagotchi2d.Tamagotchi.GameStuff;

public interface GuessGame {
    String makeGuess(String guess);
    boolean finished();
    String getInfo();
}
