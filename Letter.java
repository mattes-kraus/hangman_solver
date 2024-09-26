package hangman_solver;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Letter extends StackPane{
	private Text text = new Text();
	private Rectangle rec = new Rectangle();
	private int posInWord;
	private boolean active = true;

	public Letter(int posInWord, LetterGuesser guesser)
	{
		this.setWidth(50);
		this.setHeight(50);
		
		this.rec.setFill(Color.WHITE);
		this.rec.setStroke(Color.BLACK);
		this.rec.setWidth(50);
		this.rec.setHeight(50);
		
		this.text.setText("?");
		
		this.posInWord = posInWord;
		
		this.setOnMouseClicked(event -> 
		{
			if(active)
			{
				guesser.letterClicked(guesser.getCurrentGuess(), this.posInWord);
				this.changeCaption(guesser.getCurrentGuess());
				this.active = false;
			}
		});
		
		this.getChildren().addAll(rec, text);
	}
	
	public void changeCaption(char letter)
	{
		this.text.setText(String.valueOf(letter));
		this.rec.setFill(Color.LIGHTGREEN);
		
		this.getChildren().clear();
		this.getChildren().addAll(rec, text);
	}
	
	public void commitCaption()
	{
		this.rec.setFill(Color.DARKGREEN);
	}
}
