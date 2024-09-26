package hangman_solver.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 * Class with static methods for things which aren't in direct connection to the game
 * @author Mattes
 *
 */
public class Utilities {

	public static void printArray(char[] array, String message)
	{
		System.out.println(message);
		for(int i = 0; i<array.length; i++)
		{
			System.out.print(Character.toString(array[i]));
		}
		System.out.println();
	}
	
	public static <T> void printList(List<T> list, String message)
	{
		System.out.println(message);
		if (list.isEmpty())
		{
			System.out.println("list is empty");
			return;
		}
		for(int i=0; i<=list.size()-1; i++)
		{
			System.out.println(list.get(i).toString());
		}
	}
	
	public static boolean isValInList(ArrayList<Integer> list, int value)
	{
		Iterator<Integer> iter = list.iterator();
		while(iter.hasNext())
		{
			if(iter.next() == value)
				return true;
		}
		return false;
	}
	
	public static boolean areDifferentValuesInList(List<String> list)
	{
		if(list.isEmpty())
			return false;
		Iterator<String> iter = list.iterator();
		String word = iter.next();
		
		while(iter.hasNext())
		{
			if(!word.equals(iter.next()))
				return true;
		}
		return false;
	}
	
	public static Scene createMainstage(int wordlength, int windowWidth, int windowHeight)
	{
		// main game scene	
				/*** Initialize ***/
				LetterGuesser guesser = new LetterGuesser(
						"./hangman_solver/res/openthesaurus.txt", wordlength);
				
				// configure Pane where letters will be
				FlowPane flow = new FlowPane();
				flow.setMaxWidth(guesser.getWordLength() * 50 + 50);
				flow.setMaxHeight(200);
				
				// configure Letters
				Letter[] letterArray = new Letter[guesser.getWordLength()];
				for(int i = 0; i<guesser.getWordLength(); i++)
				{
					letterArray[i] = new Letter(i, guesser);
					flow.getChildren().addAll(letterArray[i]);
				}
					
				// configure ok Button
				Button commit = new Button("ok");
				commit.setOnMouseClicked(event ->
				{
					guesser.newGuess();
				});
				
				// add commit button, but he needs to be right of all letters
				flow.getChildren().addAll(commit);
				
				// Put things on stage
				BorderPane root = new BorderPane();
				root.setCenter(flow);
				root.setTop(guesser);
				Scene scene = new Scene(root, windowWidth, windowHeight);
				return scene;
	}
}
