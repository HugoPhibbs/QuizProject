package gui.guiLogic;

import core.coreLogic.FlashCardQuiz;
import gui.guiShell.QuizzingScreen;

public class QuizzingScreenLogic extends ScreenLogic implements Updater {

	private FlashCardQuiz flashCardQuiz;
	private QuizzingScreen screen;

	public QuizzingScreenLogic(FlashCardQuiz flashCardQuiz, QuizzingScreen quizzingScreen,
			ScreenLogic parentScreenLogic) {
		super(parentScreenLogic, null);
		this.flashCardQuiz = flashCardQuiz;
		this.screen = quizzingScreen;
	}

	public void flashCardFlip() {
		// TODO implement
	}

	public void flashCardAgain() {
		// TODO implement
	}

	public void flashCardOk() {
		// TODO implement
	}

	public void finishQuiz() {
		// TODO implement
	}

	@Override
	public void createScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeScreen() {
		// TODO Auto-generated method stub

	}
}
