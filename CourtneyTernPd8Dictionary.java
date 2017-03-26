//*********************************************************************************************************************************
//Name:   Courtney Tern
// Period:   8                Date: 2/28/17
// What I learned: I was having a lot of issues with the setOut to txt. Then, when I
// wanted the extension to print out to the Run I/O, it was still printing to the file
// and not saving the value of my maps. I learned that I needed to initialize all of the
// maps in an init() and then put everything else in the main. 
// How I feel about this lab: Static methods make everything a little bit more confusing
// but I think the lab was a good challenge. The GUI took me a solid 10 hours to work out, 
// but it was a great refresher for my GUI skills. The most frustrating method was the edit
// translation method. I kept trying to modify the different components in different ways until
// I realized that I could delete then add. That didn't take up copious memory because it was only
// one word at a time. 
// I am quite proud of this lab! 
// What I wonder: How can we create a more secure administrative password functionality?
//********************************************************************************************************************************** 

   import java.io.*;
   import java.util.*;
    public class CourtneyTernPd8Dictionary
   {
        static Map<String, Set<String>> eng2spn,spn2eng;
       
       public static void main(String[] args) throws Exception
      {
         init();
         
         try
         {
            System.setOut(new PrintStream(new FileOutputStream("dictionaryOutput.txt")));
         }
             catch(Exception e)
            {
               System.out.println("EXCEPTION");
            }
         System.out.println("ENGLISH TO SPANISH");
         display(eng2spn);
         
         System.out.println("SPANISH TO ENGLISH");
         display(spn2eng);
      }
      
      public static void init() throws FileNotFoundException
      {
      
         eng2spn = new TreeMap<String, Set<String>>();
         Scanner infile = new Scanner(new File("spanglish.txt"));
         while(infile.hasNext())
         {
            add(eng2spn, infile.next(), infile.next());
         }
         infile.close();
      
         spn2eng = reverse(eng2spn);
      }
      
       public static void display(Map<String, Set<String>> m)
      {
         Iterator<String> it= m.keySet().iterator();
         while(it.hasNext())
         {
            String key= it.next();
            System.out.print(key+" ");
            System.out.print(m.get(key));
            System.out.println();
         }
      }
       public static void add(Map<String, Set<String>> dictionary, String word, String translation)
      {
         if(dictionary.containsKey(word))
            dictionary.get(word).add(translation);
        
         else
         {
            Set<String> set= new TreeSet<String>();
            set.add(translation);
            dictionary.put(word,set);
         }
      }
       public static Map<String, Set<String>> reverse(Map<String, Set<String>> dictionary)
      {
         Map<String, Set<String>> reversed= new TreeMap<String, Set<String>>();
         
         Iterator<String> it= dictionary.keySet().iterator();
         while(it.hasNext())
         {
            String key= it.next();
            for(String str: dictionary.get(key))
            {
               add(reversed, str, key);
            }//for each str
         }//while
         
         return reversed;
      }
      
      public static Map<String, Set<String>> getEng2Spn()
      {
         System.setOut(System.out);
         return CourtneyTernPd8Dictionary.eng2spn;
      }
      
      public static Map<String, Set<String>> getSpn2Eng()
      {
         System.setOut(System.out);
         return CourtneyTernPd8Dictionary.spn2eng;
      }
   }
      /********************
	INPUT:
   	holiday
		fiesta
		holiday
		vacaciones
		party
		fiesta
		celebration
		fiesta
     <etc.>
  *********************************** 
	OUTPUT:
		ENGLISH TO SPANISH
			banana [banana]
			celebration [fiesta]
			computer [computadora, ordenador]
			double [doblar, doble, duplicar]
			father [padre]
			feast [fiesta]
			good [bueno]
			hand [mano]
			hello [hola]
			holiday [fiesta, vacaciones]
			party [fiesta]
			plaza [plaza]
			priest [padre]
			program [programa, programar]
			sleep [dormir]
			son [hijo]
			sun [sol]
			vacation [vacaciones]

		SPANISH TO ENGLISH
			banana [banana]
			bueno [good]
			computadora [computer]
			doblar [double]
			doble [double]
			dormir [sleep]
			duplicar [double]
			fiesta [celebration, feast, holiday, party]
			hijo [son]
			hola [hello]
			mano [hand]
			ordenador [computer]
			padre [father, priest]
			plaza [plaza]
			programa [program]
			programar [program]
			sol [sun]
			vacaciones [holiday, vacation]

**********************/