/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto.main;

import static crypto.main.EncryptoEngine.getNumericKey;
import java.util.Arrays;

/**
 *
 * @author Kavindu
 */
public class DecryptoEngine {
    static int[] inverse_key( int[] key )
    {
        int[] inverse_key = new int[ key.length ];
        for ( int i = 0; i < key.length; i++ )
        {
            inverse_key[ key[ i ] ] = i;
        }
        return inverse_key;
    }

    public static String[] decrypt(String input, String key) {
        String decipherText = "";
        
        input = input.trim();
        
        int[] keyCode = inverse_key(getNumericKey(key));
        
        for(int i: keyCode)
            System.out.print(i + " ");
        
        int keyLength = keyCode.length;
         
        System.out.println("Key Code");
        System.out.println("----------------");
        for(int i: keyCode)
            System.out.print(i + " ");
        System.out.println();
        System.out.println();

        //deciphering caesar shift cipher part 
        String lines[] = input.split("\\r?\\n");

        lines = input.split("\\r?\\n");
       
        System.out.println("Decrypting caesar shift cipher");
        System.out.println("----------------");
        
        for(String s: lines){
            String temp = "";
            for(char i: s.toCharArray()){
                int ascii = (int) i;
                char c;
                if(ascii - keyLength < 97){
                    c = (char)(26 + ascii - keyLength);
                }else{
                    c = (char)(ascii - keyLength); 
                }
                temp += (c);
            }
            temp = temp.replace('ë', 'ą');
            decipherText += temp;
            decipherText += "\n";
        }
        
        System.out.println("");
        System.out.println("Deciphered text after desubstitution");
        System.out.println("----------------");
        System.out.println(decipherText);
        
// ========================================================================================================
        
        //permutation cipher part 
        lines = decipherText.split("\\r?\\n");
        decipherText = "";
        
        System.out.println("Decrypting permutation cipher");
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
                decipherText += temp;
                System.out.println();
            }
            decipherText += "\n";
        }
        
        decipherText = decipherText.replace("ą", "");
        
        System.out.println("");
        System.out.println("Deciphered text after desubstitution and depermutation");
        System.out.println("----------------");
        System.out.println(decipherText);

        return decipherText.split("\\r?\\n");
    }
    
}
