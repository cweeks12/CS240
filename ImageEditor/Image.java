/* Lab 1. Picture Transformer
 * byuowns
 * 9/6/2016 */

package lab1;

import lab1.Pixel;
import java.lang.*;
import java.util.*;

// Class that contains the raster and does the heavy lifting
public class Image {

    private Pixel[][] Raster;
    private int height;
    private int width;

    public Image(int width, int height){
        Raster = new Pixel[height][width];
        this.height = height;
        this.width = width;
        
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                Raster[i][j] = new Pixel(0,0,0);
            }
        }
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public void updatePixel(int i, int j, Pixel newPixel){
        Raster[i][j] = newPixel;
    }

    public int[] getValuesOfPixel(int i, int j){
        return Raster[i][j].Values();
    }

    // Inverts the colors of the raster
    public void Invert(){
        for (int i = 0; i < Raster.length; i++){
            for (int j = 0; j < Raster[i].length; j++){
                Raster[i][j].Invert();
            }
        }
    }

    // Converts the picture to greyscale
    public void Grayscale(){
        for (int i = 0; i < Raster.length; i++){
            for (int j = 0; j < Raster[i].length; j++){
                Raster[i][j].Average();
            }
        }
    }

    // Makes the picture look embossed
    public void Emboss(){

        Pixel[][] transformed = new Pixel[this.height][this.width];
        int maxDifference = 0;
        int kittyCornerRed;
        int kittyCornerGreen;
        int kittyCornerBlue;

        for (int i = 0; i < Raster.length; i++){
            for (int j = 0; j < Raster[i].length; j++){
                if ( i == 0 || j == 0){
                    transformed[i][j] = new Pixel(128, 128, 128);
                    continue;
                }

                int redDiff = Raster[i][j].Red() - Raster[i-1][j-1].Red();
                int greenDiff = Raster[i][j].Green() - Raster[i-1][j-1].Green();
                int blueDiff = Raster[i][j].Blue() - Raster[i-1][j-1].Blue();

                if ((Math.abs(redDiff) > Math.abs(greenDiff) 
                        && Math.abs(redDiff) > Math.abs(blueDiff))
                    ||(Math.abs(redDiff) == Math.abs(greenDiff)
                        && Math.abs(redDiff) > Math.abs(blueDiff))
                    ||(Math.abs(redDiff) == Math.abs(blueDiff)
                        && Math.abs(redDiff) > Math.abs(greenDiff))
                    ||(Math.abs(redDiff) == Math.abs(greenDiff)
                        && Math.abs(redDiff) == Math.abs(blueDiff))
                        ){

                    maxDifference = redDiff;
                }

                else if (Math.abs(greenDiff) > Math.abs(redDiff) 
                            && Math.abs(greenDiff) > Math.abs(blueDiff)
                        || (Math.abs(greenDiff) == Math.abs(blueDiff)
                            && Math.abs(greenDiff) > Math.abs(redDiff))){

                    maxDifference = greenDiff;
                }

                else if (Math.abs(blueDiff) > Math.abs(greenDiff) 
                    && Math.abs(blueDiff) > Math.abs(redDiff)){

                    maxDifference = blueDiff;
                }


                int value = maxDifference + 128;
                value = (value < 0) ? 0 : (value > 255) ? 255 : value;
                transformed[i][j] = new Pixel(value, value, value);
            }
        }

        Raster = transformed; // Very declaratory

    }

    // Motion blurs the picture
    public void Blur(int blurLength){
        int redSum;
        int greenSum;
        int blueSum;
        int divisor;

        for (int i = 0; i < Raster.length; i++){
            for (int j = 0; j < Raster[i].length; j++){
                redSum = greenSum = blueSum = divisor = 0;

                for (int k = 0; k < blurLength; k++){
                    redSum += Raster[i][j+k].Red();
                    greenSum += Raster[i][j+k].Green();
                    blueSum += Raster[i][j+k].Blue();
                    divisor++;
                    if (j+k == Raster[i].length-1){
                        break;
                    }
                }                

                Raster[i][j] = new Pixel(redSum / divisor, greenSum / divisor, blueSum / divisor);

            }
        }
    }
}

