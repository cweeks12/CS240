/* Lab 1. Picture Transformer
 * byuowns
 * 9/6/2016 */

package lab1;

// Helper class that makes it easier to access and manipulate the pixels' colors
public class Pixel {
    int Red;
    int Green;
    int Blue;

    // Constructor for new Pixel
    Pixel(int red, int green, int blue){
        Red = red;
        Green = green;
        Blue = blue;
    }

    // Averages the value of the pixel, and sets every color to the average
    public void Average(){
        int average = (Red + Green + Blue) / 3;
        Red = average;
        Green = average;
        Blue = average;
    }

    // Inverts the colors of the pixel
    public void Invert(){
        final int max_value = 255;
        Red = max_value - Red;
        Green = max_value - Green;
        Blue = max_value - Blue;
    }

    public int[] Values(){
        int returnArray[] = new int[3] ;

        returnArray[0] = Red;
        returnArray[1] = Green;
        returnArray[2] = Blue;

        return returnArray;

    }

    public int Red(){
        return Red;
    }

    public int Green(){
        return Green;
    }

    public int Blue(){
        return Blue;
    }
}

