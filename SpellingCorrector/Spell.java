package spell;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import spell.Words.Node;


public class Spell implements spell.ISpellCorrector{

    public spell.Words dictionary;

    public Spell(){
        dictionary = new Words();
    }

    public void useDictionary(String dictionaryFileName) throws IOException{
        Scanner in = new Scanner(new File(dictionaryFileName));

        while (in.hasNext()){
            dictionary.add(in.next());
        }

        in.close();
    }

    public String suggestSimilarWord(String inputWord){

        if (dictionary.find(inputWord) != null){
            return inputWord;
        }
        ArrayList<String> distance1 = new ArrayList<String>();

        // Deletion distance
        for (int i = 0; i < inputWord.length(); i++){
            distance1.add(inputWord.substring(0, i) + inputWord.substring(i+1, inputWord.length()));
        }

        // Transposition distance
        for (int i = 1; i < inputWord.length(); i++){
            char[] characters = inputWord.toCharArray();
            char temp = characters[i];
            characters[i] = characters[i-1];
            characters[i-1] = temp;
            distance1.add(new String(characters));
        }

        // Alteration distance
        for (int i = 0; i < inputWord.length(); i++){
            for (int j = 0; j < 26; j++){
                distance1.add(inputWord.substring(0, i) + (char)(j+97) + inputWord.substring(i+1, inputWord.length()));
            }
        }

        // Insertion distance
        for (int i = 0; i < inputWord.length()+1; i++){
            for (int j = 0; j < 26; j++){
                distance1.add(inputWord.substring(0,i) + (char)(j+97) + inputWord.substring(i, inputWord.length()));
            }
        }

        int maxCount = 0;
        String selection = null;
        java.util.Collections.sort(distance1);

        for (String s : distance1){
            Node found = dictionary.find(s);
            if (found != null){
                if (found.getValue() > maxCount){
                   maxCount = found.getValue();
                   selection = s;
                }
            }
        }

        if(selection != null){
            return selection;
        }

        ArrayList<String> distance2 = new ArrayList<String>();

        for (String s : distance1){
            // Deletion distance
            for (int i = 0; i < s.length(); i++){
                distance2.add(s.substring(0, i) + s.substring(i+1, s.length()));
            }

            // Transposition distance
            for (int i = 1; i < s.length(); i++){
                char[] characters = s.toCharArray();
                char temp = characters[i];
                characters[i] = characters[i-1];
                characters[i-1] = temp;
                distance2.add(new String(characters));
            }

            // Alteration distance
            for (int i = 0; i < s.length(); i++){
                for (int j = 0; j < 26; j++){
                    distance2.add(s.substring(0, i) + (char)(j+97) + s.substring(i+1, s.length()));
                }
            }

            // Insertion distance
            for (int i = 0; i < s.length()+1; i++){
                for (int j = 0; j < 26; j++){
                    distance2.add(s.substring(0,i) + (char)(j+97) + s.substring(i, s.length()));
                }
            }
        }

        maxCount = 0;
        java.util.Collections.sort(distance2);

        for (String s : distance2){
            Node found = dictionary.find(s);
            if (found != null){
                if (found.getValue() > maxCount){
                   maxCount = found.getValue();
                   selection = s;
                }
            }
        }

        return selection;
    }
    
}

