package coreLogic;

import coreObjects.*;

public class FlashCardQuiz {
	
	private Deck deck;
	private FlashCard currentFlashCard;
	
	FlashCardQuiz(Deck deck){
		this.deck = deck;
	}

	public void startQuiz() {
		// TODO implement
		
		// Starts a quiz 
	}
	
	public void endQuiz() {
		// TODO implement
		
		// Ends a quiz
	}
	
	private void nextFLashCard() {
		// TODO implement
		
		// Handles request of next card, after either "AGAIN" or "OK" was pressed
	}
	
	private void flipCurrentFlashCard() {
		// TODO implement
		
		// Handles request of wanting to flip a flash card. If the current flash card has it's front showing,
		// then show the back, otherwise show the front.
	}
	
	private void flashCardAgain() {
		// TODO implement
		
		// Handles request of card "AGAIN" button from GUI
	}
	
	private void flashCardOk() {
		// TODO implement
		
		// Handles request of card "OK" button from GUI
	}
}
