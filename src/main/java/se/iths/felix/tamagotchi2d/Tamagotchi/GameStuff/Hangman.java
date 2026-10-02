package se.iths.felix.tamagotchi2d.Tamagotchi.GameStuff;

import se.iths.felix.tamagotchi2d.Tamagotchi.jobStuff.Job;

import java.util.Random;
import java.util.Set;

public class Hangman implements GuessGame{

    private Set<Character> guesses;
    private int nGuess;
    private String[] words = {"Kebab", "Richard", "Kanelbulle", "tamagotchi", "bandkanonvagn", "leksand"};
    public String gWord;
    String secret;

    public Hangman(){
        int pick = new Random().nextInt(words.length);
        secret = words[pick];
    }


    @Override
    public String makeGuess(String guess) {
        char cGuess = guess.charAt(0);
        if(guesses.contains(cGuess)){
            return "You have already guessed that letter";
        }else {
            guesses.add(cGuess);
            nGuess++;

            if(secret.indexOf(cGuess) == -1){
                return "Guess is incorrect";
            }
            else{
                return "Guess is correct";
            }
        }
    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public String getInfo() {
        return "";
    }
}
