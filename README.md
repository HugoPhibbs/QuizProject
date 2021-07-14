# QuizProject

## Welcome to our quiz project!

This is a small java project that Hugo Phibbs and Tom Berry have decided to undertake to build our software engineering skills!

# Brief and Specs

## Project Scope

This is a quiz project to test and build our Software Engineering skills!

The idea of this project is to create a Java application that can test users on pretty much any topic that can be narrowed down to simple flash cards!

## Tasks

### Planning

Need to plan out everything that is needed to get done, and i mean everything! For this we are going to use a WBS (Work breakdown structure) to specify everything that is needed to get done.

Be realistic with timelines for when things are going to get done, don't overextend yourself or you will be struggling to meet deadlines and constantly having to move things back.

Core Logic and Objects Development
This holds all the logic relating to the handling of requests from the GUI. This processes everything.

Basically the brains of the application

### Unit testing

We should write small JUnit tests for all of the core calsses involved. These should be testing the boundaries of programs and not just fiddling witih getters and setters.

If you find that it is difficult to test a method, break it up and test those smaller methods

Aiming for a 90% code coverage of the core logic classes

Only test the methods if they need testing, always check methods with logical statements. Otherwise if they perform an action that is very straight forward, then don't bother, for example toString methods with no other functional dependencies

### Graphical User Interface

We are going to develop the GUI using Swing Window builder.

Functionality should be kept as modular as possible.

### Integration Testing

Due to the resources that we have at hand, this is going to unfortunately be done manually by hand on either the GUI or a Command Line application.

We should aim to test boundary conditions with these, same deal with unit tests

### Documentation

We are going to be creating documentation for the entire project using JavaDoc. This should be written for everything!- this includes classes, methods and attributes.

Along with JavaDoc, code commenting should be sprinkled throughout to explain difficult parts of code that may need explanation. However, writing code is like humor, if you have to explain it, it is probably bad!

### Delivery

Aim to have a functional application by the end of the holidays (18th July). This may seem unrealstic, but it is good to set deadlines and see what we can do with them. However, it is totally probable that this project will extend into the next semester, so it will likely become a side hustle! No problem, Rome wasn't built in a day!

### Report

Create a report at the end of the project which is a debrief of sorts. In this, we should discuss everything that went well and what didn't go to well. Along with this, we should comment on anything that we have learnt from this project and what we should do going on into the future.

# Requirements

Requirements are based a minimum viable product

### Flash Cards

Flash cards should be two sided, eg with a question on the front and an answer on the back

Flash cards should be stored in a deck, which is a collection of cards under a similar topic

Flash cards should be able to be viewed from both sides, eg the one side could be an English translation of a word and the other could be the French translation. User can decide which side is the 'front' and which is the 'back'

Over time, cards are repeated based on an algorithm for how well a user can remember them. Should be based on a spaced learning ethos. For example, once an initial answering of a question, the app asks the question in 2, 5, 10 day intervals. (Increasing size)

Flash cards should be editable after creation, eg changing front and back text

### Decks

Flash cards can be stored together in a deck.

Decks must have a name

Decks must have a description field

Decks can have from 0 to infinite number of flash cards

Decks should be able to be ordered in order of size or alpha-numerical

User should be able to handle multiple different decks at once

Decks should be editable after creation, eg add or remove flash cards, or change the deck name

Flash cards can be added and removed from a deck

Every flash card in the deck must have a unique front and back text

### Quizzing

User should be able to quiz themselves on one deck at a time

Flash cards are presented to the user in order determined by sorting algorithm.

When a Flash card appears, they see the front of the card. And when they are ready to see the back, they click a button to show the back. Same button can be pressed again to show the front. This button should be labelled "FLIP CARD"

Upon seeing the back of the card, the user can then either click "OK" or "AGAIN". If they press "OK" the flash card is sent to the bottom of the pile. If "AGAIN" is pressed, card is put to the top of the pile. Upon clicking "AGAIN" or "OK the next card is shown.

New cards that are being seen should be in random order.

A max of 10 new cards will be shown to a user on a particular day

### Flash Card Prioritizing

From the first time a card is shown to a user, it is then added to the queue to be shown 2 days later. Once the card has been seen again >= 2 days later, the card is then added to a queue to be seen 5 days later. Once it has been seen again >= 5 days later, it is added to a queue to be seen 10 days later. Once it has been seen a 4th time, then it isn't shown again. If a card isn't review on the day that is due, it is added to a backlog, and will need to be reviewed on top of any other cards that need to be reviewed.

In terms of what the "AGAIN" and "OK" buttons do. In terms of flash cards, in order to finish a quiz, there has to be more "OK"s that "AGAIN"s. A good way to visualise is thinking of a particular flash card at the top of a stair case. If "OK" is pressed the first time a user (a new card) sees a flash card, then the card is put to the back of the priority queue and is seen once more before the end of the quiz. However, if "AGAIN" is pressed, then the card is seen twice more before the end of the quiz, each successive "AGAIN" adds the flash card to be seen once more. Until a user finally presses "OK" on it's final appearance. What this guarantees is that new cards that haven't been seen before appear at least once on their first quiz appearance. However, if a card is being reviewed, the same rules apply with "AGAIN" however, if a user selects "OK" the first time they see a card that they are reviewing, it isn't shown again at the end of the deck.

### GUI

User should be able to create flash cards and decks directly from the GUI

GUI should be a 'shell' for the java application underneath. No logic apart from GUI logic should be contained in the GUI

GUI should be functional, not pretty

A main screen is shown with all the decks that are available.

Button to press in order to edit decks and cards.

### Saving and Progress

Users should be able to save the state of their progress in a file, which can then be loaded whenever the application is started again.

This should be done using Java Serialization

## Extra feature ideas for later development

User can have be notified when they are on a streak within the game and have some sort of message of encouragement.

Photos and audio should be addable to the flash cards

All information from a user should be exportable in a file such as word or txt. This includes all the metrics on their progress, along with all the decks that they have created.

Cards could have an importance tag, that can prioritize them over other cards and make them appear more often than other cards.

Should be able to archive decks into a sub folder, and then be extracted later

Progress should be easily viewed in the form of charts in app. This can be quantified with metrics such as questions answered, percentage of correct answers and the total time spent in app.

Users could have a calendar view that gives them a longterm picture of what they are going to get done. This will also allow them to plan things out.

Flash cards could support any character in Unicode, from Ancient Greek to Kanji

Different learning games, eg mix and match.

Can have multiple choice, where the front of the flash card is the question, and the possible answers are a collection of the backs of other flash cards, with one of them being the correct back.

Implement a priority queue with viewing cards (min or max heap), to get O(logn) performance. extra extra as O(n) to find min anyways.

On setup screen, fields for working directory and a users name can be preentered with the last used directories

User can use keys on keyboard to do simple tasks in with the GUI, without using the mouse

Concurrent programming instead of observer pattern

Default text in text boxes, eg enter front text for flashcard. Text then disappears when user selects the textbox

## Conventions and Code Style

Naming of variables, classes, methods etc should follow standard java conventions and be easily to understand.
Try to break methods up as much as possible. This makes things alot easier to test, integrate and refactor.
DRY, Don't Repeat Yourself and KISS Keep It Simple Stupid!
All programming should follow core object orientated principles
All classes should have appropriate getters and setters.
All names must have no special characters with no extra white space
If a function returns a boolean value from another method, please put this method in parenthesises

### Throwing exceptions

Exceptions that indicate a bug in the system, should not be checked, these should end the flow of the program immediately, ie errors
Exceptions should only be thrown if there is exceptional flow that shouldn't be happening under reasonable conditions. Otherwise, returning boolean values for if an operation was done or not will suffice.

## Version Control

Using Git
Please run unit tests before you push any code, it's much easier to solve bugs for code that you have just created, as it is fresh in your mind
Make sure commit messages are helpful.

## Risks and other concerns

### Clashes

We are using Github for continous integration of our work. As a result we need to be careful of not clashing our work too often, as it can be a nightmare.
To counter this... commit and push to the main branch regularly. Small changes should be grouped together into one push. Try to push as much as possible, this avoids a big piling up of clashses that is simply really fucking annoying and time wasting to fix

### Communication

Can't stress this enough, we need to stay in contact with each other.
If there are any plans or other things that we need to discuss and overcome, then talk about it.
If you feel that you are putting way more work than other members, let them know early before it comes a big problem, I'm sure they will all be happy to help

### Work load

Not expecting everyone involved to put in equal amounts of effort, to think that this is going to happen is simply unrealistic.
So a hard and fast rule is: Do what you have said that you are going to a high quality. Nothing worse than waiting on someone else to implement functionality that they said they would have gotten done.
