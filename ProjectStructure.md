# Structure - Classes and Packages

- A brief overview of how requirements have been implemented in Java code. This document isn't exhaustive and has been more used as a reference manual for creating new classes and making sure that other team members are caught up to speed with how things are being implemented

# Packages

## setup

- Contains classes for starting an quiz application.

#### Setup

- Class to load previous sessions of a user or start a new one
- Loads a serializable file if one exists for a user, otherwise handle creating a new one

#### Main

- Class containing main method to start the application
- Creates a new setup screen

## coreObjects

- Holds core objects

#### FlashCard

- Flash cards should be expressed as a Java Class.
- Attributes for front text, back text and nextReviewDate
- Have a one way relationship between deck, as in FlashCard is independent from the deck that it is contained in. In code this means that FlashCard doesn't have a attribute for the deck that it belongs to.
- To keep track of reviews, it has an LocalDate attribute nextReviewDate. If a card hasn't been seen before this attribute is set to null and if it has been reviewed enough it is set to 100 years in the future- Realistically, the program will never reach this date. Date is in a dd-mm-yyyy format. If this card is switched to another deck, the review history is preserved.

#### Deck

- Expressed as a Java Class
- Attributes for name, ArrayList of flash cards, date of creation.
- Method cardsForQuiz(), which returns 10 new cards (max), along with any other cards that are due for review.
- Supports CRUD actions to do with it's collection of FlashCards

#### User

- Expressed as a java class
- Represents a user of this quiz application
- Has a name, along with a attribute for UserStats,

## coreLogic

- Logic to do with the quizzing application itself

#### FlashCardQuiz

- Class to hold methods and logic relating to the actual playing of a card game.
- Firstly gets all the cards to be quizzed on from Deck.cardsToQuiz()
- Has logic relating to iterating through cards when a user is quizzing themselves. Hold functions relating to "AGAIN" and "OK", i.e. with prioritising cards. For example, these methods could be nextCard(), cardOk(), cardAgain(), showCardBack(), showCardFront(), startQuiz(), endQuiz()
- May hold functionality relating to sorting cards, will need to resolve this.
- Has attributes for final and again stacks of FlashCards, these are continually updated throughout the course of a quiz
- At the end of a quiz, a QuizStats object is returned to AppEnvironment

#### AppEnvironment

- The 'Puppet Player' for the entire App
- Has an instance of DeckCollection for holding all the decks.
- Holds a User for this Game.
- Manages queries from the GUI for the main screen, eg a method newQuiz(), which creates a new flashCardQuiz object and a newFlashCardScreen
- Takes a QuizStats object and delivers it to user stats, which then parses this information.

#### DeckManager

- Contains all the decks for a user
- Has methods to support CRUD actions with a user's decks,
- Acts as a medium between decks and AppEnvironment, making sure that actions performed on decks are correct in the wider scope of things.

## stats

- Contains classes relating to handling of statistics for the application

#### QuizStats

- Holds statistical information for the time spent in a quiz
- I.e. how long the quiz took, how many times a user pressed "AGAIN" or "OK",
- A method to return stats for a quiz, eg a summary that can be shown at the end of the quiz. Average time per flashcard. How many more new cards are in the deck?
- Has a method statsSummary() which is used by Quizzing screen when displaying the summary of a quiz.

#### UserStats

- Holds statistical information about a user
- Holds multiple instances of QuizStats (ie for every quiz that a user has ever done), this data can then be parsed.
- E.g. How many quizes they have done
- Can be used to create graphs

## guiLogic

- Any logic to do with the graphical user interface

### GuiManager

- Managing class for GUI,
- Ensures that there is no more than one instance of a type of Screen at once
- Shouldn't be able to edit a deck while you are being quizzed on the same deck, may complicate things, so just don't allow it.
- When MainScreen is closed, all other GUI screens are closed too. Can have a method shutdown() to do something like this
- ArrayList of all the current screens that are open, closes all of these screens when shutdown() is called.
- If a screen closes by itself or by the user quitting it, then it should remove this Scren from the current screens that are open.

### ScreenLogic

- Super abstract class that is implemented by logic classes in guiLogic
- has methods to go to a parent screen
- Has an attribute to the screen that this logic class is manipulating
- ScreenLogic children handle switching from different screens, Screen children just act like a shell, with no real state
- attribute for the parent screen of a screen

### Updateable

- Interface for ScreenLogic classes that can have their states become out of date by actions on other screens.
- A sort of observr in observer pattern
- method recievedUpdate() to act on any updates from an Updater implementation

### Updater

- Interface for ScreenLogic classes can change the state of other ScreenLogic classes
- A sort of subject in an observer pattern
- method update() to notify any Updateable dependents

### EditFlashCardScreenLogic

- Any logic to do with manipulating EditFlashCardScreen
- Has a parameter updateable of type Updateable that is updated on finishing editing a deck
- Implements Updater

### MainScreenLogic

- Any logic to do with manipulating MainScreen
- Implements Updateable

### EditDeckScreenLogic

- Any logic to do with manipulating EditDeckScreen
- Has a parameter updateable of type Updateable that is updated on finishing editing a deck
- Implements both Updateable and Updater. As editFlashCardScreen can change it too

### QuizzingScreenLogic

- Any logic to do with manipulating QuizzingScreen
- Impements updater to update mainScreen at the end of quiz

### CreateDeckScreenLogic

- Any logic to do with manipulating CreateDeckScreen
- Has a parameter updateable of type Updateable that is updated on finishing editing a deck
- Implements Updater

### SetupScreenLogic

- Any logic to do with manipulating SetupScreen

### guiShell

- contains any screens that are used in the graphical user interface, acts as a 'shell' which is manipulated by classes in guiLogic

#### Screen

- Abstract class for screen objects, extended by all screen objects bellow
- Has attributes for a Frame
- Concrete methods to show and hide the frame, along with abstract methods to create and destroy.
- Method to clear a panel, taken from IslandTrader.
- Attribute for the parent of this screen, i.e. what screen has used to access this current screen

#### SetupScreen

- User can load previous sessions or create a new session
- User selects the directory that they want to load a previous sessions from.
- If a user tries to override a current file with a new save, pop up should show up asking if they would like to delete the previous save or choose a different, name. If DELETE is chosen then deleteSession() should be called in the setup class.

#### MainScreen

- Entry point into the program
- Holds an instance of AppEnvironment.
- Object for the main screen of the quiz.
- See all of the decks that you have created and choose one of them.
- Choose a deck. You have options to press QUIZ, or EDIT deck.
- Create a FlashCard, this is an empty Flash Card object, and then pass this over to an instance of EditFlashCardScreen. Can only create a new FlashCard if a deck has been chosen, disable this button until a deck is chosen
- Button to create a Deck

#### EditDeckScreen

- Screen for editing a deck. Can only view edit deck at a time.
- Has an Deck attribute for the current Deck that is being edited, along with an attribute for DeckManager for the AppEnvironment of this quiz.
- Supports CRUD actions to do with a single Deck.
- Buttons to RENAME or DELETE a deck
- Shows all the flash cards in a deck in a JTable. One Column for front and another column for back.
- Select a flash card, you have the option to EDIT it by pressing another button. This creates a new EditFlashCardScreen.
- Button to ADD a flash card to the deck. Creates a new instance of EditFlashCardScreen, with it's flashCard attribute being an empty FlashCard object. Once the FlashCardScreen is open, editDeckScreen is hidden. Another button to DELETE a FlashCard from a deck
- Press close to collapse screen and go back to the main screen.

#### NewDeckScreen

- Screen to create a new Deck
- Textfield to enter the name of a new deck and the description of a deck
- Another button to create the deck, upon pressing this, displays error text if this isn't permissible

#### EditFlashCardScreen

- Screen for editing and creating a flash card.
- Has attribute for the current FlashCard object that it is editing, along with the Deck that the FlashCard is contained in and an attribute for DeckManager, in-case the user wants to change the deck that this FlashCard is stored in. If the FlashCard is being created, the deck is set to null.
- Text boxes for the front and back texts of a flash card. Textboxes should be pre-entered with the current front and back of the flash card if the card has already been created. If you are creating a flash card from afresh, textboxes should be empty.
- User can choose which deck they would like flash card to belong to. Both when editing and creating a flash card. To switch what deck the flash card belongs to, there should be a drop down menu.
- Press button to save changes. If you are creating a flash card, button should be labelled "CREATE flashcard", otherwise "UPDATE" for editing a flash card. Press button... If there are any errors in doing an operation, then an error text should show up telling user what is wrong and what they can do to fix the problem. Message should should up in green otherwise to indicate that there was a success.
- When you press close, the EditDeckScreen that led to this EditFlashCardScreen is shown.

#### Quiz Screen

- Screen for when a user wants to quiz themselves.
- Has buttons for AGAIN, OK, NEXT and FLIP.
- Has an instance of FlashCardQuiz.
- Has button to start the quiz, this then calls startQuiz() in FlashCardQuiz()
- At the end of the quiz, it shows a summary of the quiz, eg how many questions you got right, percentage of correct answers etc. This info is taken straight from the QuizStats object in FlashCardsQuiz.
- When the quiz is over, along with showing a quiz summary, there is a button to CONTINUE//CLOSE, i.e. just closes the Quizzing window.

## tests

- contains unit testing classes for individual classes.
