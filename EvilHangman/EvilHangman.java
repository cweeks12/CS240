package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map; import java.util.HashSet; import java.util.HashMap;
import java.util.TreeSet;

public class EvilHangman implements IEvilHangmanGame {

    Set<String> dictionary;
    Set<String> remainingWords;
    Set<Character> guessedLetters;

    Map<Integer,Set<String>> options;


    // Not sure what to do here yet
    public EvilHangman(){
    }

    public static void main(String[] args){
        for (int i = 0; i < 33; i++)
            for (int j = 0; j < 33; j++){
                if (i == j){
                    continue;
                }
                System.out.println("i=" + i + " j=" + j + " answer=" + compareValues(i,j));
            }
    }

	
	/**
	 * Starts a new game of evil hangman using words from <code>dictionary</code>
	 * with length <code>wordLength</code>.
	 *	<p>
	 *	This method should set up everything required to play the game,
	 *	but should not actually play the game. (ie. There should not be
	 *	a loop to prompt for input from the user.)
	 * 
	 * @param dictionary Dictionary of words to use for the game
	 * @param wordLength Number of characters in the word to guess
	 */
    @Override
	public void startGame(File dictionary, int wordLength){

        this.dictionary = new HashSet<String>();
        this.remainingWords = new HashSet<String>();
        this.guessedLetters = new TreeSet<Character>();
        this.options = new HashMap<Integer,Set<String>>();

        try (Scanner in = new Scanner(dictionary)){
            in.useDelimiter("[^a-zA-Z]+");

            while(in.hasNext()){
                String next = in.next().toLowerCase();
                if (next.length() == wordLength){
                    this.dictionary.add(next);
                }
            }

        }
        catch (IOException e){
            System.out.println("There was an error reading the file. Simulation terminating.");
            return;
        }

        for (String s : this.dictionary){
//            System.out.println(s);
            this.remainingWords.add(s);
        }

    }
	

	/**
	 * Make a guess in the current game.
	 * 
	 * @param guess The character being guessed
	 *
	 * @return The set of strings that satisfy all the guesses made so far
	 * in the game, including the guess made in this call. The game could claim
	 * that any of these words had been the secret word for the whole game. 
	 * 
	 * @throws GuessAlreadyMadeException If the character <code>guess</code> 
	 * has already been guessed in this game.
	 */
    @Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException{
        if (guessedLetters.contains(guess)){
            throw new GuessAlreadyMadeException();
        }

        guessedLetters.add(guess);

        options.clear();

        for (String s: remainingWords){
            int key = getCode(guess, s);
            if (!options.containsKey(key)){
                options.put(key, new HashSet<String>());
                options.get(key).add(s);
            }
            else {
                options.get(key).add(s);
            }
        }

        int maxSize = 0;
        int bestSet = -1;
        for (int k: options.keySet()){
            if (options.get(k).size() > maxSize){
                bestSet = k;
                maxSize = options.get(bestSet).size();
            }
            else if (options.get(k).size() == maxSize){
                bestSet = compareValues(k, bestSet);
                maxSize = options.get(bestSet).size();
            }
        }

        remainingWords = options.get(bestSet);
        return options.get(bestSet);
    }

    public Set<Character> getGuessedLetters(){
        return guessedLetters;
    }

    public int getCode(char guess, String word){

        int multiple = 1;
        int value = 0;

        char[] newWord = word.toCharArray();

        for (int i = newWord.length-1; i >= 0; i--){
            if (newWord[i] == guess){
                value += multiple;
            }
            multiple *= 2;
        }

        return value;
    }

    // The size is already known to be the same, so compare just the keys
    // The values are guaranteed to be different
    public static int compareValues(int k, int bestSet){
        if (k == 0){
            return k;
        }
        else if (bestSet == 0){
            return bestSet;
        }
        else if (Integer.bitCount(k) == 1 && Integer.bitCount(bestSet) > 1){
            return k;
        }
        else if (Integer.bitCount(bestSet) == 1 && Integer.bitCount(k) > 1){
            return bestSet;
        }

        for (int i = 0; i < 32; i++){
            if (((k & 1<<i) > 0) && !((bestSet & 1<<i) > 0)){
                return k;
            }
            else if (((bestSet & 1<<i) > 0) && !((k & 1<<i) > 0)){
                return bestSet;
            }
        }
        return 0;
    }
}

