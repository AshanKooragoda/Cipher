/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Kavindu
 */

public class EncryptoEngine {
    public static int[] getNumericKey(String s){
        int[] keyCharArray = new int[26];
        Arrays.fill(keyCharArray, -1);
        
        char[] charArray = s.toCharArray();

        for(int i = 0; i < s.length(); i++){
            char c = charArray[i];
            
            int ascii = (int) Character.toUpperCase(c) - 65;
            keyCharArray[ascii] = i;
        }
        
        int filledElements = 0;
        
        for(int i: keyCharArray){
            if(i!=-1)
                filledElements++;
        }
        
        int[] keyCode = new int[filledElements];
        int position = 0;
        
        for(int i: keyCharArray){
            if(i!=-1){
               keyCode[position] = i;
               position++;
            }
        }
        
        return keyCode;
    }
    
    public static String[] encrypt(String input, String key){
        String cipherText = "";
        
        input = input.trim();
        
        int[] keyCode = getNumericKey(key);
        int keyLength = keyCode.length;
         
        System.out.println("Key Code");
        System.out.println("----------------");
        for(int i: keyCode)
            System.out.print(i + " ");
        System.out.println();
        System.out.println();
        
        //permutation cipher part 
        String lines[] = input.split("\\r?\\n");
        
        System.out.println("Permutation cipher");
        System.out.println("----------------");
        
        for(String s: lines){
            if(s.length() % key.length() != 0){
                char[] array = new char[key.length() - s.length() % key.length()];
                Arrays.fill(array, 'ą'); //filling charactor
                s += new String(array);
            }
            
            for(int i = 0; i<s.length(); i+=keyLength){
                String block = (s.substring(i, i+keyLength));

                String temp = "";
                for(int pos = 0; pos < keyLength; pos++){
                    temp += block.charAt(keyCode[pos]);
                    System.out.print(block.charAt(keyCode[pos]) + " ");
                }
                cipherText += temp;
                System.out.println();
            }
            cipherText += "\n";
        }
        
        System.out.println("");
        System.out.println("Ciphered text after permutation");
        System.out.println("----------------");
        System.out.println(cipherText);
        
// ========================================================================================================
        //caesar shift cipher part 

        lines = cipherText.split("\\r?\\n");
        
        cipherText = ""; 
        
        System.out.println("Caesar shift cipher");
        System.out.println("----------------");
        
        for(String s: lines){
            String temp = "";
            for(char i: s.toCharArray()){
                int ascii = (int) i;
                char c;
                if(ascii + keyLength > 122){
                    c = (char)(ascii + keyLength - 26);
                }else{
                    c = (char)(ascii + keyLength); 
                }
                temp += (c);
            }
            cipherText += temp;
            cipherText += "\n";
        }
        
        System.out.println("");
        System.out.println("Ciphered text after permutation and substitution");
        System.out.println("----------------");
        System.out.println(cipherText);

        return cipherText.split("\\r?\\n");
    }
}
