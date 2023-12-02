import java.util.*;
import java.io.*;

public class Games {

//this is a variable that counts the amount of incorrect guesses the user has guessed
private static int wrongCount = 0;

/*	*	This program run and allows user to play Hangman against either a friend or the computer
	*	The code is designed so that if in the future I want to add more games for the user to play they will be easily integrated into the current code
	*	In this program main is designed to do nothing but pass the numerical value that chooseGame() returns to the runGame() method
	*	In this instance, hangman is recognized as the value of zero
	*	As an example, if chooseGame() is no longer a comment, then it prompts the user to pick hangman or tictactoe
	*	It returns 0 for hangman and 1 for tic tac toe
	*	Then those values can be passed to the runGame() method 
	*	However, tictactoe is only used as an example and isnt playable so the method is commented out
*/
	public static void main(String[] args) throws FileNotFoundException {
//		int game = chooseGame();
		System.out.println("Welcome to Hangman!");
		runGame(0);
	}

/*	*	chooseGame() is not currently active and is only an example for the time being to show how the program is designed for easy implementation of other games in the future
	*	It prompts the user to enter T or H for tictactoe or hangman
	*	All instances where the user must enter input are robust against invlaid user input
	*	Depending on the user's input, the code will return a numerical value
	* 	In main that numerical value is passed to runGame()
*/
	public static int chooseGame() {
		int result;
		Scanner console = new Scanner(System.in);
		printBarrier();
		
		//Prompt user to pick a game
		System.out.println("You have the option to choose from two games to play.\nWould you like to play Hangman or TicTacToe?");
		printBarrier();
		System.out.print("Please type \"H\" for Hangman or \"T\" for TicTacToe: ");

		//Stores the user's pick in a String game
		String game = console.nextLine();

		//test String game for invalid user input, re-prompt user if necessary
		while(!(game.equals("H")) && !(game.equals("T"))) {
			System.out.print("Please type \"H\" for Hangman or \"T\" for TicTacToe: ");
			game = console.nextLine();
		}
		printBarrier();

		//return the numerical value for the game picked
		if (game.equals("H")) {
			System.out.println("The game you picked was Hangman.");
			return 0;
		} else if (game.equals("T")) {
			System.out.println("The game you picked was TicTacToe.");
			return 1;
		}
		return 2;
	}

/*	*	This method is used through the game to print a nice looking break in the text
	*	In some instances the output of the games can make for a hard time reading each line so this makes a nice looking barrier to seperate every few lines of output
*/
	public static void printBarrier() {
//		System.out.println("\n--------------------\n--------------------\n");
		System.out.println("\n||||||||||||||||||||\n||||||||||||||||||||\n");
	}

/*	*	runGame takes a numerical value and uses it to choose which game the user will play
	*	this method tests the numerical value and when a match is found, it calls the method responsible for playing the game picked
*/
	public static void runGame(int game) throws FileNotFoundException {
		if (game == 0) {
			Hangman();
		} else if (game == 1) {
//			TicTacToe();
		}
	}

/*	*	The hangman() method is responsible for calling the necessary methods to run and play hangman
	*	It calls chooseWord() which prompts the user to set parameters for the computer to pick a word
	*	The word is saved in the String word
	*	word is passed to makeHiddenWord() which uses the length of word (the word the user will be guessing) to create a word with the same length of only underscores, spaces and dashes
	*	With word and hiddenWord, both parameters are passed to formatGame() which takes each and calls the appropriate methods for the user to guess letters and play Hangman
*/
	public static void Hangman() throws FileNotFoundException {
		String word = chooseWord();
		String hiddenWord = makeHiddenWord(word);
		int result = formatGame(word, hiddenWord);
	}

/*	*	formatGame() takes the word to be guessed a hidden version of that word in underscores
	*	first it initializes the letter variable, when the user guesses a letter it will be passed to this variable
	*	next the code prints out the hiddenword for the user to view each time they guess a word
	*	then the printGraphic() method is run
	*	printGraphics takes the word, hiddenWord, and wrongCount parameters
	*	depending on these paramters it will print a different picture to show the user how far along they are until they lose
	*	the user is prompted to guess a letter
	*	util the hiddenWord is the same as the actual word the user is prompted to guess letters
	*	All instances where the user is aksed to guess a letter are robusr against invalid user input and will only run if the user types in a letter
	*	The code runs only if the user guesses a lowercase or uppercase letter
	*	when a valid letter is guessed, its value along with word and hiddenWord are passed to the guessLetter() method
	*	guessLetter() takes that letter and if it is a correct guess (it is a letter in word) then it is revealed in the hiddenWord
*/
	public static int formatGame(String word, String hiddenWord) throws FileNotFoundException {
		char letter = '*';

		//makes user's guesses case insensitive
		word = word.toLowerCase();
		printBarrier();
		Scanner console = new Scanner(System.in);

		//display the hiddenWord
		for (int i = 0; i < hiddenWord.length(); ++i) {
			System.out.print(hiddenWord.charAt(i) + " ");
		}
		System.out.println();

		//prints the hangman picture
		printGraphic(wrongCount, hiddenWord, word);
		System.out.println();

		//test that the user entered a valid letter; if invalid re-prompt them
		while (!(hiddenWord.equalsIgnoreCase(word))) {
			printBarrier();
			System.out.print("Please guess a letter: ");
			String letterAsString = console.next();
			System.out.println();

			//letter is first used as a string in case the user enters a sequence of multiple chars
			if (letterAsString.length() == 1 && Character.isLetter(letterAsString.charAt(0))) {
				letter = letterAsString.charAt(0);
			}
			while (letter == '*') {
				if (letterAsString.length() == 1 && Character.isLetter(letterAsString.charAt(0))) {
					letter = letterAsString.charAt(0);
				} else {
					System.out.print("\nPlease enter a valid letter: ");
					letterAsString = console.next();
				}
			}

			//makes the guesses case insensitive
			letter = Character.toLowerCase(letter);

			//guessLetter() returns a string of the new value of hiddenWord; depends on if the user guessed the right letter or not
			hiddenWord = guessLetter(word, hiddenWord, letter);
			printGraphic(wrongCount, hiddenWord, word);
			letter = '*';
		}
		return 0;
	}

/*	*	first, guessLetter() creates a char array equivalent to hiddenWord
	*	a new variable called initial hiddenWord is created and equivalent to hiddenWord
	*	initialHiddenWord is used to test whether or not the value of hiddenWord changed durinng the method
	*	next it iterates through each letter is word and tests if letter matches any letters in word
	*	if it matches, then hiddenWord at the same index is changed to letter
	*	if the value of hiddenWord now still equals the value of InitialHiddenWord, then the user must have guessed a letter that doesn't appear in word
	*	in this instance the value of wrongCount is incremented by 1
	*	a for loop then prints out the new value of hiddenWord and returns hiddenWord
*/
	public static String guessLetter(String word, String hiddenWord, char letter) throws FileNotFoundException {
		
		//turn hiddenWord to a char Array
		char Arr_hiddenWord[] = hiddenWord.toCharArray();
		
		//save value of hiddenWord before the method runs to initialhiddenWord to see if it changes
		String initialhiddenWord = hiddenWord;

		//test if any letters in word are equal to the letter guessed by the user; if so change hiddenWord to display that letter
		for (int i = 0; i < word.length(); ++i) {
			if (word.charAt(i) == letter) {
				Arr_hiddenWord[i] = letter;
			}
		}

		//if hiddenWord was not changed then increment wrongCount by 1
		hiddenWord = String.valueOf(Arr_hiddenWord);
		if (initialhiddenWord.equals(hiddenWord)) {
			++wrongCount;
		}

		//print and format new value of hiddenWord
		for (int j = 0; j < hiddenWord.length(); ++j) {
			System.out.print(hiddenWord.charAt(j) + " ");
		}
		System.out.println();
		return hiddenWord;
	}

/*	*	the makeHiddenWord() method is responsible for returning the value of hiddenWord based on its parameter
	*	the method takes the length of word and tests each index
	*	if word at that index is a letter then hiddenWord at that index is represented by an underscore
	*	some words have spaces and dashes, if that is the case then those indexes remain as they are so it is clear to the user they are guesses a phrase
*/
	public static String makeHiddenWord(String word) {
		int length = word.length();
		
		//fencepost
		String hiddenWord = "_";
		
		//get each index of word and replce it with the necessary character and save that to hiddenWord
		for (int i = 1; i < word.length(); ++i) {
			if (word.charAt(i) == ' ') {
				hiddenWord = hiddenWord + " ";
			} else if (word.charAt(i) == '-') {
				hiddenWord = hiddenWord + "-";
			} else {
				hiddenWord = hiddenWord + "_";
			}
		}
		return hiddenWord;
	}

/*	*	printGraphic takes wrongCount, hiddenWord, word
	*	it creates a new file object with the value of wrongCount in the name
	*	then the code prints the file in the console
	*	if the wrongcount equals 7 then it prints the final grpahic and terminates the program because the user has lost
*/
	public static String printGraphic(int wrongCount, String hiddenWord, String word) throws FileNotFoundException {
		
		//initialize a new Scanner object that takes input from a new file object
		Scanner file = new Scanner(new File("Pictures/" + wrongCount + "~Wrong.txt"));
		
		//print out the .txt file
		while(file.hasNextLine()) {
			System.out.println(file.nextLine());
		}

		//terminates program is user typed too many wrong guesses
		if (wrongCount == 7) {
			System.out.println("The word was \"" + word + ".\"");
			System.exit(0);
		}
		return hiddenWord;
	}

/*	*	the chooseWord() method is responsible for prompting the user to enter certain parameters based on how they want to play the game
	*	with these parameters set, the method will pick a word from a file in the dict/ directory and return its value
	*	the user first picks if they want to play against a friend or the computer
	*	if they pick a friend then the computer prompts someone to enter a word
        *	that new word is returned
	*	if the user chooses to play against the computer then they are prompted to pick a difficulty
	*	if they pick easy the word will be between 0-5 chars
	*	if they pick medium the word will be between 6-9 chars
	*	if they pick hard the word will be between 10-15 chars
	*	if they pick extreme the word will be between 16-100 chars
	*	the length of the word to be guessed is set by the bounds variables
	*	After the length is determined the user is asked if they want to guess a word or phrase
	*	if they want to guess a word then the computer looks at words in dict/web2
	*	if they want to guess a phrase then the computer looks at phrases in dict/web2a
	*	a new random object is initialized for the computer to guess a random line in the dict files
	*	web2 has 235976 lines and web2a has 76205 lines
	*	in web2 each line is a word and each line in web2a is a phrase
        *	as the random object generates a line, if the word or phrase on that line does not meet the difficulty parameters the user set then it guesses a new line
	*	this process repeats until a valiid word or phrase that meets the diffculty parameters is met
	*	the method returns this value as a string	
*/
	public static String chooseWord() throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		Random getLine = new Random();
		int bound1 = 0;
		int bound2 = 0;
		String wordCPU = "";
		printBarrier();
		
		//prompt user to play against either the CPU or have a friend enter a word for them to guess
		System.out.print("Would you like to play against a friend or the Computer?\n");
		System.out.print("Enter \"F\" to play a friend or \"C\" to play the Computer: ");
		String opponent = console.nextLine();

		//make code robust against invalid user input
		while (!(opponent.equals("F")) && !(opponent.equals("C"))) {
			System.out.print("Enter \"F\" to play a friend or \"C\" to play the Computer: ");
			opponent = console.nextLine();
		}

		//if they guess friend prompt the friend to enter a word and retuen that value
		printBarrier();
		if (opponent.equals("F")) {
			System.out.print("You have chosen to play against a friend. Have your friend enter a valid word or phrase for you to guess: ");
			String word = console.nextLine();
			return word;

		//havew the user enter a difficulty setting for the computer to use to guess their word
		} else if (opponent.equals("C")) {
			System.out.print("You have chosen to play against the Computer.\nPlease select a difficulty:\n");
			printBarrier();
			System.out.println("Type \"E\" for easy\nType \"M\" for medium\nType \"H\" for hard\nType \"X\" for extreme");
			printBarrier();
			System.out.print("Type the difficulty you want to play here: ");
			String difficulty = console.nextLine();

			//makes code robust against invalid user input
			while (!(difficulty.equals("E")) && !(difficulty.equals("M")) && !(difficulty.equals("H")) && !(difficulty.equals("X"))) {
				System.out.print("Please enter a valid difficulty: ");
				difficulty = console.nextLine();
			}

			//each of these if statements sets the bounds based of the difficulty the user wants
			printBarrier();
			if (difficulty.equals("E")) {
				System.out.println("You chose the easy difficulty!");
				bound1 = 0;
				bound2 = 5;
			}
			if (difficulty.equals("M")) {
				System.out.println("You chose the medium difficulty!");
				bound1 = 6;
				bound2 = 9;
			}
			if (difficulty.equals("H")) {
				System.out.println("You chose the hard difficulty!");
				bound1 = 10;
				bound2 = 15;
			}
			if (difficulty.equals("X")) {
				System.out.println("You chose the extreme difficulty!");
				bound1 = 16;
				bound2 = 100;
			}
			printBarrier();

			//the user is prompted whether to guess a word or a phrase
			System.out.print("Would you like to guess a word or a phrase?\nEnter \"W\" to guess a word and enter \"P\" tp guess a phrase: ");
			String status = console.nextLine();

			//makes code robust against invalid user input
			while(!(status.equals("W")) && !(status.equals("P"))) {
				System.out.print("Enter \"W\" to guess a word and enter \"P\" to guess a phrase: ");
				status = console.nextLine();
			}
			printBarrier();
			
			//in each of these if statements a ranodm object guesses a line in the chosen file
			//a for loop is needed to iterate to that line in the file and save it as a string
			//if the word/phrase on that line meets the bounds then it returns that word
			if (status.equals("W")) {
				System.out.print("You decided to guess a word.\nYour word has been generated.");
				Scanner web2 = new Scanner(new File("dict/web2"));
				int wordLine = getLine.nextInt(235976);
				for (int i = 1; i <= wordLine; ++i) {
					wordCPU = web2.nextLine();
				}
				while (!(wordCPU.length() >= bound1 && wordCPU.length() <= bound2)) {
					Scanner newWeb2 = new Scanner(new File("dict/web2"));
					wordLine = getLine.nextInt(235976);
					for (int j = 1; j <= wordLine; ++j) {
						wordCPU = newWeb2.nextLine();
					}
				}
				return wordCPU;
			}

			//this is the same as the above if statement except it is only if the user wants to guess a phrase
			if (status.equals("P")) {
				System.out.print("You decided to guess a phrase.\nYour phrase has been generated.");
				Scanner web2a = new Scanner(new File("dict/web2a"));
				int phraseLine = getLine.nextInt(76205);
				for (int z = 1; z <= phraseLine; ++z) {
					wordCPU = web2a.nextLine();
				}
				while (!(wordCPU.length() >= bound1 && wordCPU.length() <= bound2)) {
					Scanner newWeb2a = new Scanner(new File("dict/web2a"));
					phraseLine = getLine.nextInt(76205);
					for (int m = 1; m <= phraseLine; ++m) {
						wordCPU = newWeb2a.nextLine();
					}
				}
				return wordCPU;
			}

			return "no word generated; there must be a problem with the code";
		} return "no word generated; there must be a problem with the code";
	 }
}
