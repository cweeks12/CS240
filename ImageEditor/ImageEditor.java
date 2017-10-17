/* Lab 1. Picture Transformer
 * byuowns
 * 9/6/2016 */

package lab1;

import java.io.*;
import java.lang.*;
import java.util.*;

// Main class
public class ImageEditor {

    private Image image;
    
    public static void main(String[] args){

        if (!ParseArguments(args)){
            return;
        }

        ImageEditor editor = new ImageEditor();

        // Open the file
        try{
            editor.OpenFile(args[0]);
        }
        catch (FileNotFoundException e){
            System.out.println("Infile not found!");
            return;
        }

        // Do the transformation
        if (args[2].equals("grayscale")){
            editor.image.Grayscale();
        }
        if (args[2].equals("invert")){
            editor.image.Invert();
        }
        if (args[2].equals("emboss")){
            editor.image.Emboss();
        }
        if (args[2].equals("motionblur")){
            editor.image.Blur(Integer.parseInt(args[3]));
        }
        // Write the file back
        editor.SaveFile(args[1]);

    }

    // Method that parses the arguments and decides if they're correct, prints usage otherwise
    private static boolean ParseArguments(String[] args){

        final String[] options = {"grayscale", "invert", "emboss", "motionblur"};

        // Checks for the correct number of arguments
        if (args.length < 3 || args.length > 4){
            System.out.println("Incorrect number of arguments.");
            PrintUsage();
            return false;
        }

        // Checks to see if it's a valid option
        boolean correctOption = false;
        for (String s : options){
            if (s.equals(args[2])){
                correctOption = true;
            }
        }

        if (!correctOption){
            System.out.println("Incorrect option chosen: " + args[2]);
            PrintUsage();
            return false;
        }

        // Checks to see if they chose motionblur and didn't provide a correct number
        if (args[2].equals("motionblur") && args.length < 4 ){
            System.out.println("A valid width must be chosen for motionblur.");
            PrintUsage();
            return false;
        }

        else if (args[2].equals("motionblur")){
            int newGuy = 0;
            try {
                newGuy = Integer.parseInt(args[3]);
            }
            catch (NumberFormatException e) {
                System.out.println("Argument 4 isn't a valid integer");
                PrintUsage();
                return false;
            }
            if (newGuy <= 0){
                System.out.println("Width argument must be positive.");
                PrintUsage();
                return false;

            }
        }

        // Checks to see if they didn't choose  motionblur and provided a number
        if (!args[2].equals("motionblur") && args.length != 3){
            System.out.println("Invalid number of arguments provided");
            PrintUsage();
            return false;
        }

        return true;

    }

    // Method that prints the usage of the program
    public static void PrintUsage(){
        System.out.println("Incorrect Usage!");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  java ImageEditor in-file out-file operation [number]");
        System.out.println("  Where operation is one of: 'grayscale', 'invert', 'emboss', 'motionblur'");   
        System.out.println("  And if motionblur is selected, number is a positive integer");


    }

    public void OpenFile(String filename) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(filename));

        SkipComment(sc);
        sc.next("P3");
        SkipComment(sc);
        int width = sc.nextInt();
        SkipComment(sc);
        int height  = sc.nextInt();
        SkipComment(sc);
        sc.nextInt();

        image = new Image(width, height);

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){

                SkipComment(sc);
                int red = sc.nextInt();
                SkipComment(sc);
                int green = sc.nextInt();
                SkipComment(sc);
                int blue = sc.nextInt();
                image.updatePixel(i,j, new Pixel(red, green, blue));

            }

        }

    }

    private void SkipComment(Scanner sc){
        while (sc.hasNext("#")){
            sc.next("#");
            sc.nextLine();
        }
    }

    public void SaveFile(String filename){

        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("P3");
            writer.print(image.getWidth());
            writer.print(" ");
            writer.println(image.getHeight());
            writer.println(255);
            for (int i = 0; i < image.getHeight(); i++){
                for (int j = 0; j < image.getWidth(); j++){
                    for (int k: image.getValuesOfPixel(i,j)){
                        writer.println(k);
                    }

                }
            }
            writer.close();

        } catch (IOException e){
            System.out.println("Look ma, I caught an exception!");
        }

        
    }

}


