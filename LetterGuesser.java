package hangman_solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class LetterGuesser extends StackPane{

	private Text text = new Text();
	private List<String> possibleWords = new LinkedList<String>();
	private char currentGuess;
	private int wordLength = 0; // length of the word the user guesses
	private ArrayList<Integer> alreadyGuessedChars = new ArrayList<Integer>();
	private boolean guessedLetterRight = true;
	private int guessCounter = 0;
	
	/**
	 * reads the germanWords file and saves all words to a list
	 * @param filepath path to the file with the words the program needs to know
	 */
	public LetterGuesser(String filepath, int wordlength)
	{
		this.wordLength = wordlength;
		
		// functionality
		BufferedReader reader;
		
		try
		{
			reader = new BufferedReader(new FileReader(filepath));
			String in;
			
			while((in = reader.readLine())!= null)
			{
				String[] words = in.split(" ");
				String cleanWord;
				for(int i=0; i<words.length; i++)
				{
					cleanWord = words[i].replaceAll("�|�", "ae");
					cleanWord = cleanWord.replaceAll("�|�", "ue");
					cleanWord = cleanWord.replaceAll("�|�", "oe");
					cleanWord = cleanWord.replaceAll("�", "ss");
					cleanWord = cleanWord.replaceAll("[^a-zA-Z]", "");
					possibleWords.add(cleanWord.toLowerCase());
				}
			}
			
			reader.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		this.getWordSize(wordLength);
		
		this.newGuess();
		
		// visualization
		Rectangle rec = new Rectangle();
			
		this.setWidth(100);
		this.setHeight(100);
		
		rec.setFill(Color.LIGHTGRAY);
		rec.setStroke(Color.BLACK);
		rec.setWidth(100);
		rec.setHeight(100);
		
		this.text.setText(Character.toString(this.currentGuess));
		
		this.getChildren().addAll(rec, text);
	}
	
	/**
	 * Here is what counts for the game to work
	 * @return Letter, which the game wants to know whether and where its in the word of the user
	 */
	public void newGuess()
	{
		// check whether the letter guessed before did any progress
		// if not delete all words, where the current guess appears
		if(this.guessedLetterRight == false)
			this.deleteAllWordsWithCurrentLetter();
		this.guessedLetterRight = false;
		
		this.guessCounter++;
		
		Iterator<String> iter = this.possibleWords.iterator();
		char[] currentWord;
		
		// initialization with zero
		int[] letterAppearance = new int[255];
		for(int i=0; i<letterAppearance.length; i++)
		{
			letterAppearance[i] = 0;
		}
		
		// check for each word which chars appear in it
		while(iter.hasNext())
		{
			String debugFirstWord = iter.next();
			currentWord = debugFirstWord.toCharArray();//iter.next().toCharArray();
			System.out.println(debugFirstWord);
			
			if(currentWord.length>0)
			{
				// delete duplicates
				Arrays.sort(currentWord);
				char previousChar = currentWord[0];
				letterAppearance[currentWord[0]] += 1;
				
				// increase in array for each char the letterApperance-Array at the place of the char (a = array[65])
				for(int i=1; i<currentWord.length; i++)
				{
					if(previousChar != currentWord[i])
					{
						letterAppearance[currentWord[i]] += 1;
						previousChar = currentWord[i];
					}
				}
			}
		}
		
		// search for highest number and return it
		char popularestChar = 'z';
		int popularity = 0;
		for(int i=0; i<letterAppearance.length; i++)
		{
			if((popularity < letterAppearance[i]) 
					&& !Utilities.isValInList(alreadyGuessedChars, i))
			{
				popularestChar = (char)i;
				popularity = letterAppearance[i];
			}
		}
		
		// return
		if(Utilities.areDifferentValuesInList(possibleWords))
		{
			this.currentGuess = popularestChar;
			this.text.setText(Character.toString(popularestChar) + " " 
					+ Integer.toString(this.guessCounter));
			this.alreadyGuessedChars.add((int) popularestChar);
		}
		else
		{
			if(!possibleWords.isEmpty()) 
			{
				this.wordGuessed();
			}
			else
				this.text.setText("word not existant");
		}
	}
	
	public void wordGuessed()
	{
		this.text.setText(possibleWords.get(0));
	}
	
	public char getCurrentGuess()
	{
		return this.currentGuess;
	}
	
	public int getWordLength()
	{
		return this.wordLength;
	}
	
	public void letterClicked(char letter, int pos)
	{
		this.guessedLetterRight = true;
		
		LinkedList<String> list = new LinkedList<String>();
		Iterator<String> iter = possibleWords.iterator();
		
		while(iter.hasNext())
		{
			String currentWord = iter.next();
			if(currentWord.length() > pos)
			{
				if(letter == currentWord.charAt(pos))
					list.add(currentWord);
			}
		}
		this.possibleWords = list;
		
		// debug
		//Utilities.printList(list, "list after pressing a letter");
	}
	
	public void getWordSize(int size)
	{
		LinkedList<String> list = new LinkedList<String>();
		Iterator<String> iter = possibleWords.iterator();
		
		while(iter.hasNext())
		{
			String currentWord = iter.next();
			if(currentWord.length() == size)
			{
				list.add(currentWord);
			}
		}
		this.possibleWords = list;
		
		// debug
		//Utilities.printList(list, "list after proccessing size");
	}
	
	public void deleteAllWordsWithCurrentLetter()
	{
		LinkedList<String> list = new LinkedList<String>();
		Iterator<String> iter = possibleWords.iterator();
		boolean wordOkay = true;
		
		while(iter.hasNext())
		{
			wordOkay = true;
			String sCurrentWord = iter.next();
			char[] cCurrentWord = sCurrentWord.toCharArray();
			// check whether word is okay because he has not the current guess in
			// it or not
			for(int i = 0; i<cCurrentWord.length; i++)
			{
				if(cCurrentWord[i] == this.currentGuess) wordOkay = false;
			}
			if(wordOkay) list.add(sCurrentWord);
		}
		this.possibleWords = list;
		
		// debug
		//Utilities.printList(list, "list after processing a doubled click on ok");
	}
}
