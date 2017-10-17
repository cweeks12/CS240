package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
		Spell corrector = new Spell();
		
		corrector.useDictionary(dictionaryFileName);

//        Spell corr = new Spell();
  //      corr.useDictionary(inputWord);

    //    System.out.println(corr.dictionary.equals(corrector.dictionary));
     //   System.out.println(corr.dictionary.hashCode());
      //  System.out.println(corrector.dictionary.hashCode());

        //corrector.toString();
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		
		System.out.println("Suggestion is: " + suggestion);
	}

}
