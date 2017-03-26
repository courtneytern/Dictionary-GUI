import java.util.*;
import java.io.*;

public class DictionaryExt extends CourtneyTernPd8Dictionary
{
   static String choice="", lookup="";
   static Scanner in;
   
   public static void main(String[] args) throws FileNotFoundException
   {
      init();
      System.setOut(System.out);
      try
      {
         in= new Scanner(new File("dictionaryOutput.txt"));
      }
      catch(Exception e){};
     
      Scanner keyboard= new Scanner(System.in);
   
      while(!choice.equals("ES") && !choice.equals("SE"))
      {
         System.out.print("English to Spanish (ES) or Spanish to English (SE)?");
         choice= keyboard.next();
      }//while
      
      System.out.print("What would you like to look up? ");
      lookup= keyboard.next();
      
      Map<String, Set<String>> dict;
      
      if(choice.equals("ES"))
         dict= getEng2Spn(); //access eng2spn from part 1
      else
         dict= getSpn2Eng();
      
      if(!dict.containsKey(lookup))
         System.out.println("Sorry, we could not find "+lookup); 
      else 
         System.out.println(lookup+ ": "+dict.get(lookup));
   }//main
}//ext 