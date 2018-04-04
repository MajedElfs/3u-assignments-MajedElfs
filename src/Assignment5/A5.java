/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment5;

import java.util.Scanner;

/**
 *
 * @author hadim9637
 */
public class A5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Repeat translation
        while (true) {
            //Getting the word
            System.out.println("Enter a word to UBBIFY!");
            String origWord = input.nextLine();
            //Sanitize the input
            origWord = origWord.toLowerCase();

            //Checking if the word starts with a vowel
            //Walk down the word looking for a vowel
            int length = origWord.length();

            // Store the translated word in a variable
            String transWord = "";

            //Use a for loop to go through all the letters
            for (int i = 0; i < length;) {

                //Look at the character at position 'i' and is it a vowel?
                if (origWord.charAt(i) == 'a' || origWord.charAt(i) == 'i' || origWord.charAt(i) == 'o' || origWord.charAt(i) == 'e' || origWord.charAt(i) == 'u') {
                    //If vowel is first letter, transword is blank so Ub will be first.
                    //If vowel is in middle or end of word, then it puts the current stored letters then Ub
                    transWord = transWord + "ub";

                    if (i + 1 < length) {
                        //if character after is not a vowel.. print
                        while (origWord.charAt(i + 1) == 'a' || origWord.charAt(i + 1) == 'i' || origWord.charAt(i + 1) == 'o' || origWord.charAt(i + 1) == 'e' || origWord.charAt(i + 1) == 'u') {
                            transWord = transWord + origWord.charAt(i);
                            //Moves on to the next letter
                            i++;
                            //To avoid exceeding beyond length of the original word
                            if (i + 1 >= length) {
                                break;
                            }

                        }
                    }

                }
                transWord = transWord + origWord.charAt(i);
                //Moves on to the next letter
                i++;

            }
            //Gives translated word
            System.out.println(transWord);
        }
    }
}
