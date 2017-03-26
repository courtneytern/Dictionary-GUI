import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CourtneyTernPd8DictionaryExt extends CourtneyTernPd8Dictionary //implements ActionListener
{
   static String choice= "Eng to Spn", lookup="", editChoice="";
   static Scanner in;
   static JFrame frame,loginFrame;
   static JPanel center, south, english, spanish;
   static JButton add2English, add2Spanish,deleteWordButtonE,deleteWordButtonS, deleteTransButtonE,deleteTransButtonS;
   static JButton editWordButton, editTransButton, help;
   static JLabel currentMode,output, deleteInstructions, adminLabel;
   static JTextField input, word, translation, enterWord, enterTrans, currentWord, newValue;
   static JTextField wordField, currentTrans, newTrans;
   static JPasswordField password;
   static JRadioButton english2spanish, spanish2english;
   static ButtonGroup group;
   
   public static void main(String[] args) throws FileNotFoundException
   {
      init();
      try
      {
         in= new Scanner(new File("dictionaryOutput.txt"));
      }
      catch(Exception e){};
     
      Scanner keyboard= new Scanner(System.in);
      
      frame= new JFrame("Dictionary"); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      CourtneyTernPd8DictionaryExt window = new CourtneyTernPd8DictionaryExt();
      
      frame.pack();
      frame.setVisible(true); 
   }//main
   
   public CourtneyTernPd8DictionaryExt()
   {
     adminLabel= new JLabel("ADMIN MODE");
     adminLabel.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
     
     center= new JPanel();
         input= new JTextField("Enter word to translate");
         output= new JLabel("Translation will appear here");
         JButton translate= new JButton("Translate");
         translate.addActionListener(new translateListener());
      center.add(input);
      center.add(translate);
      center.add(output); 
           
      JPanel south= new JPanel();
         currentMode= new JLabel("Current Mode: "+ choice); 
         JButton toggle= new JButton("Toggle mode");
         toggle.addActionListener(new toggleListener());
         JButton edit= new JButton("Edit Dictonary");
         edit.addActionListener(new editListener());
      south.setLayout(new GridLayout(1,3));
      south.add(currentMode);
      south.add(toggle);
      south.add(edit); 
      
      JPanel east= new JPanel();
         help= new JButton("Help");
         help.addActionListener(new helpListener());
      east.add(help);
      
      JLabel userLabel= new JLabel("USER MODE");
      userLabel.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
      
      frame.add(userLabel, BorderLayout.NORTH);     
      frame.add(center, BorderLayout.CENTER);
      frame.add(east, BorderLayout.EAST); 
      frame.add(south, BorderLayout.SOUTH);
   }
   
  /* public void refresh()
   {
           english.removeAll(); 
            english.add(new JLabel("ENGLISH TO SPANISH"));
            for(String eng: getEng2Spn().keySet())
               english.add(new JLabel(eng+" "+getEng2Spn().get(eng)));
           spanish.removeAll();  
            spanish.add(new JLabel("SPANISH TO ENGLISH"));
            for(String spn: getSpn2Eng().keySet())
               spanish.add(new JLabel(spn+ " "+getSpn2Eng().get(spn)));
   }//refresh */
   
   public void deleteWord(Map<String, Set<String>> map, Map<String, Set<String>> other, String word)
      {
         try
         {
           Iterator<String> it= map.get(word).iterator(); //strings
           
           while(it.hasNext())
           {
              String key= it.next();
              other.get(key).remove(word);
              if(other.get(key).isEmpty())
                 other.remove(key);
           }//while
            
            map.remove(word);
                   
         }//try
         catch(Exception e)
         {
            JFrame error= new JFrame();
            error.add(new JLabel("Sorry, that word does not exist in selected language")); 
            error.pack();
            error.setVisible(true);
         }//catch

      }//deleteWord
      
      public void deleteTrans(Map<String, Set<String>> map, Map<String, Set<String>> other, String word, String trans)
      {
         if(map.containsKey(word)) 
         {       
            map.get(word).remove(trans);
          if(map.get(word).isEmpty())
            map.remove(word);
            
         }
         else
         {
            JFrame popup= new JFrame();
            popup.add(new JLabel("Cannot find word to delete"));
            popup.pack();
            popup.setVisible(true);
         }
         
         if(other.containsKey(trans))
         {   
            other.get(trans).remove(word);
            if(other.get(trans).isEmpty())
               other.remove(trans);
            
         }
      }//deleteTrans
   
   private class toggleListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         if(choice.equals("Eng to Spn"))
            choice= "Spn to Eng";
         else
            choice= "Eng to Spn";
         
         currentMode.setText("Current Mode: "+ choice);
      }
   }//toggleListener
   
   private class translateListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         Map<String, Set<String>> dict;
      
         if(choice.equals("Eng to Spn"))
            dict= getEng2Spn(); //access eng2spn from part 1
         else
            dict= getSpn2Eng();
            
         lookup= input.getText();
         if(!dict.containsKey(lookup))
            output.setText("Sorry, we could not find "+lookup); 
         else 
            output.setText(dict.get(lookup)+"");
      }
   }//translateListener
   
   private class editListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         loginFrame= new JFrame("Log In");
         
         JPanel login= new JPanel();
         login.setLayout(new GridLayout(4,1));
            JLabel prompt= new JLabel("Please Enter Admin Password");
            password= new JPasswordField(); //password is "admin"
            JButton ok= new JButton("OK");
            ok.addActionListener(new loginListener());
         login.add(prompt);
         login.add(password);
         login.add(ok);
         
         loginFrame.add(login);
         loginFrame.pack();
         loginFrame.setVisible(true);
      }//actionperformed
   }//edit Listener
   
   private class loginListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         if(password.getText().equals("admin"))
         {
            loginFrame.dispose();
            editWindow();
         }
         else
            password.setText("");
      }//actionPerformed
      
      public void editWindow()
      {
         
         JFrame editFrame= new JFrame("Edit Dictionary");
         editFrame.setLayout(new BorderLayout());
            english= new JPanel();
            english.setLayout(new BoxLayout(english, BoxLayout.Y_AXIS));
            english.add(new JLabel("ENGLISH TO SPANISH"));
            for(String eng: getEng2Spn().keySet())
               english.add(new JLabel(eng+" "+getEng2Spn().get(eng)));
               
            spanish= new JPanel();
            spanish.setLayout(new BoxLayout(spanish, BoxLayout.Y_AXIS));
            spanish.add(new JLabel("SPANISH TO ENGLISH"));
            for(String spn: getSpn2Eng().keySet())
               spanish.add(new JLabel(spn+ " "+getSpn2Eng().get(spn)));
            
            JPanel editor= new JPanel();
               JButton add= new JButton("Add Word/Translation");
               add.addActionListener(new addWordListener());
               JButton delete= new JButton("Delete Word/Translation");
               delete.addActionListener(new deleteWordListener());
               JButton edit= new JButton("Edit Word/Translation");
               edit.addActionListener(new editWordListener());
            editor.add(add);
            editor.add(delete);
            editor.add(edit);
         
         editFrame.add(adminLabel, BorderLayout.NORTH);                  
         editFrame.add(english, BorderLayout.WEST);
         editFrame.add(spanish, BorderLayout.EAST);
         editFrame.add(editor, BorderLayout.SOUTH);
         editFrame.pack();
         editFrame.setVisible(true);
      }//editwindow
   }//loginListener
   
   private class addWordListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         JFrame addFrame= new JFrame("Add Word/Translation");
         addFrame.setLayout(new BorderLayout());
            JPanel userIn= new JPanel();
            userIn.setLayout(new GridLayout(2,1));
               word= new JTextField("Enter word");
               translation= new JTextField("Enter translation one at a time");
            userIn.add(word);
            userIn.add(translation);
            
            JPanel addPanel= new JPanel();
            addPanel.setLayout(new FlowLayout());
               add2English= new JButton("Add to English2Spanish");
               add2English.addActionListener(new addButtonListener());
               add2Spanish= new JButton("Add to Spanish2English");
               add2Spanish.addActionListener(new addButtonListener());
            
            JLabel instructions = new JLabel("Please log in again to see your edits");
         
         addFrame.add(adminLabel, BorderLayout.NORTH);
         addFrame.add(userIn, BorderLayout.WEST);
         addFrame.add(add2English, BorderLayout.CENTER);
         addFrame.add(add2Spanish, BorderLayout.EAST);
         addFrame.add(instructions, BorderLayout.SOUTH);
         addFrame.pack();
         addFrame.setVisible(true);
      }
   }//addListener
   
   private class addButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         if(event.getSource()==add2English)
         {
            add(getEng2Spn(), word.getText(),translation.getText());
            add(getSpn2Eng(), translation.getText(), word.getText());
         }
         else
         {
            add(getSpn2Eng(), word.getText(),translation.getText());
            add(getEng2Spn(), translation.getText(), word.getText());
         }
         
      }
   }//addbutton listener
   
   private class deleteWordListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         JFrame deleteFrame= new JFrame("Delete Word/Translation");
         deleteFrame.setLayout(new BorderLayout());
            JPanel deletePanel= new JPanel();
            deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.Y_AXIS));
               JPanel deleteWord= new JPanel();
               enterWord= new JTextField("Enter word");
               deleteWordButtonE= new JButton("Delete word from English");
               deleteWordButtonE.addActionListener(new deleteButtonListener());
               deleteWordButtonS= new JButton("Delete word from Spanish");
               deleteWordButtonS.addActionListener(new deleteButtonListener());
               deleteWord.add(enterWord);
               deleteWord.add(deleteWordButtonE);
               deleteWord.add(deleteWordButtonS);
               
               JPanel deleteTrans= new JPanel();
               enterTrans= new JTextField("Enter translation");
               deleteTransButtonE= new JButton("Delete translation from English");
               deleteTransButtonE.addActionListener(new deleteButtonListener());
               deleteTransButtonS= new JButton("Delete translation from Spanish");
               deleteTransButtonS.addActionListener(new deleteButtonListener());
               deleteTrans.add(enterTrans);
               deleteTrans.add(deleteTransButtonE);
               deleteTrans.add(deleteTransButtonS);
            deletePanel.add(deleteWord);
            deletePanel.add(new JLabel("This will delete word and all its translations"));
            deletePanel.add(deleteTrans);
            deletePanel.add(new JLabel("This will delete this translation only"));
         
         deleteInstructions= new JLabel("Please log in again to see your edits");
         
         deleteFrame.add(adminLabel, BorderLayout.NORTH);
         deleteFrame.add(deletePanel, BorderLayout.CENTER);
         deleteFrame.add(deleteInstructions, BorderLayout.SOUTH);
         deleteFrame.pack();
         deleteFrame.setVisible(true);
      }
   }//deleteListener
   
   private class deleteButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         if(event.getSource()==deleteWordButtonE)
            deleteWord(getEng2Spn(), getSpn2Eng(),enterWord.getText());
         else if(event.getSource()== deleteWordButtonS)
            deleteWord(getSpn2Eng(), getEng2Spn(),enterWord.getText());
         else if(event.getSource()==deleteTransButtonE)
            deleteTrans(getEng2Spn(), getSpn2Eng(),enterWord.getText(), enterTrans.getText());
         else
            deleteTrans(getSpn2Eng(),getEng2Spn(),enterWord.getText(),enterTrans.getText());
          
          
      }
   }//deleteButtonListener
   
   private class editWordListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         JFrame editFrame= new JFrame("Edit Word/Translation");
         editFrame.setLayout(new BorderLayout());
         
         JPanel editPanel= new JPanel();
         editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
            editPanel.add(new JLabel("EDIT WORD"));
            JPanel editWord= new JPanel();
               currentWord= new JTextField("Word to Edit");
               newValue= new JTextField("New Value");
               editWord.add(new JLabel("from"));
               editWord.add(currentWord);
               editWord.add(new JLabel("to"));
               editWord.add(newValue);               
               editWordButton= new JButton("EDIT");
               editWordButton.addActionListener(new editButtonListener());
               editWord.add(editWordButton);
            editPanel.add(editWord);
            
            editPanel.add(new JLabel("EDIT TRANSLATION"));           
            JPanel editTrans= new JPanel();
            editTrans.setLayout(new GridLayout(2,1));
               JPanel wordPanel= new JPanel();
               wordPanel.add(new JLabel("Word"));
               wordField= new JTextField("Enter word");
               wordPanel.add(wordField);
               
               JPanel editingPanel= new JPanel();
               currentTrans= new JTextField("Current translation");
               newTrans= new JTextField("New Translation");
               editingPanel.add(new JLabel("from"));
               editingPanel.add(currentTrans);
               editingPanel.add(new JLabel("to"));
               editingPanel.add(newTrans);
               editTransButton= new JButton("EDIT");
               editTransButton.addActionListener(new editButtonListener());
               editingPanel.add(editTransButton);
            editTrans.add(wordPanel);
            editTrans.add(editingPanel);
            editPanel.add(editTrans);
            
            JPanel radioButtons= new JPanel(); //flow
               english2spanish= new JRadioButton("English to Spanish");
               english2spanish.addActionListener(new radioListener());
               spanish2english= new JRadioButton("Spanish to English");
               spanish2english.addActionListener(new radioListener());
               
               group= new ButtonGroup();
               group.add(english2spanish);
               group.add(spanish2english);
            radioButtons.add(english2spanish);
            radioButtons.add(spanish2english);
            
         editFrame.add(adminLabel, BorderLayout.NORTH);
         editFrame.add(editPanel, BorderLayout.CENTER);
         editFrame.add(radioButtons, BorderLayout.SOUTH);
         editFrame.pack();
         editFrame.setVisible(true);
      }
   }//editListener
      
   private class editButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         if(group.getSelection()==null)
         {
            JFrame popup= new JFrame();
            popup.add(new JLabel("Please make a language selection"));
            popup.pack();
            popup.setVisible(true);
            return;
         }//if no selection
         
         Map<String,Set<String>> map,other;        
         if(editChoice.equals("ES")) //if english to spanish
         {
            map= getEng2Spn();
            other= getSpn2Eng();
         }
         else
         {
            map= getSpn2Eng();
            other= getEng2Spn();
         }
         
         if(event.getSource()==editWordButton)
         {
            Iterator<String> it= map.get(currentWord.getText()).iterator();
            while(it.hasNext())
            {
               String str= it.next();
               add(map,newValue.getText(), str);
               add(other, str, newValue.getText());
            }
            deleteWord(map,other,currentWord.getText());
         }
         else //if edit trans
         {
            deleteTrans(map, other,wordField.getText(), currentTrans.getText());
            add(map, wordField.getText(), newTrans.getText());
            add(other, newTrans.getText(), wordField.getText());
         }
         
      }//action performed
   }//editButtonListener
   
   private class radioListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         if(event.getSource()==english2spanish)
            editChoice= "ES";
         else
            editChoice= "SE";
      }
   }//radioListener 
   
   private class helpListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         try
         {
         JFrame helpFrame= new JFrame("Help");
            JPanel helpPanel= new JPanel();
            JLabel welcome= new JLabel("Welcome to the English-Spanish Dictionary");
         helpFrame.add(welcome);
         
         Scanner inHelp= new Scanner(new File("DictionaryHelp.txt"));
         StringBuilder sb= new StringBuilder();
         while(inHelp.hasNext())
            sb.append(inHelp.nextLine()+"\n");
         helpPanel.add(new JTextArea(sb.toString()));
         
         helpFrame.add(new JScrollPane(helpPanel));
         helpFrame.pack();
         helpFrame.setVisible(true);
         }
         catch(Exception e){}
      }
   }//helplistener
   
}//extension