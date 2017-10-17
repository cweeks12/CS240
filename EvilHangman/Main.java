package hangman;

import java.io.File;
import java.lang.Integer;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

import hangman.EvilHangman.*;

public class Main{

    int guesses;

    public static void main (String[] args){
        EvilHangman game = new EvilHangman();

        game.startGame(new File(args[0]), Integer.parseInt(args[1]));

        int guesses = Integer.parseInt(args[2]);

        char[] word = new char[Integer.parseInt(args[1])];
        for (int i = 0; i < word.length; i++){
            word[i] = '_';
        }

        Set<String> options = new HashSet<String>();

        Scanner input = new Scanner(System.in);

        while (guesses > 0){
            System.out.print("Word: ");
            System.out.println(word);
            System.out.print("Guesses Left: ");
            System.out.println(guesses);
            System.out.print("Guessed Letters: ");
            System.out.println(game.getGuessedLetters());

            System.out.print("Input a guess: ");
            String guess = input.next();

            if(!guess.matches("[a-zA-z]")){
                System.out.println("Invalid Input!");
                System.out.println();
                continue;
            }

            try {
                options = game.makeGuess(guess.toLowerCase().toCharArray()[0]);
            }
            catch (EvilHangman.GuessAlreadyMadeException ex){
                System.out.println("You already used that letter.");
                System.out.println();
                continue;
            }

            if (options.size() == 1){
                boolean done = true;
                for (char c : options.iterator().next().toCharArray()){
                    if (!game.getGuessedLetters().contains(c)){
                        done = false;
                    }
                }
                if (done){
                    System.out.println("You win! The word was: " + options.iterator().next());
                    System.out.println();
                    return;
                }
            }

            int numberContained = 0;
            int code = game.getCode((char)guess.toCharArray()[0],options.iterator().next());
            numberContained = Integer.bitCount(code);
            if (numberContained > 0){
                System.out.println("Yes, there is " + numberContained + " " + guess);
                System.out.println();
                for (int i = word.length-1; i >= 0; i--){
                    if ((code & 1<<i) > 0){
                        word[word.length-1-i] = guess.toCharArray()[0];
                    }
                }
            }
            else{
                System.out.println("Sorry, there are no " + guess + "'s");
                System.out.println();
                guesses--;
            }
        }

        System.out.println("YOU LOSE! The word was: " + options.iterator().next() + ".");
        return;
    }
}
