import java.util.*;
import java.io.*;

public class Games {
	public static void main(String[] args) throws FileNotFoundException {
		int game = chooseGame();
		runGame(game);
	}
	public static int chooseGame() {
		int result;
		Scanner console = new Scanner(System.in);
		printBarrier();
		System.out.println("You have the option to choose from two games to play.\nWould you like to play Hangman or TicTacToe?");
		printBarrier();
		System.out.print("Please type \"H\" for Hangman or \"T\" for TicTacToe: ");
		String game = console.nextLine();
		while(!(game.equals("H")) && !(game.equals("T"))) {
			System.out.print("Please type \"H\" for Hangman or \"T\" for TicTacToe: ");
			game = console.nextLine();
		}
		printBarrier();
		if (game.equals("H")) {
			System.out.println("The game you picked was Hangman.");
			return 0;
		} else if (game.equals("T")) {
			System.out.println("The game you picked was TicTacToe.");
			return 1;
		}
		return 2;
	}
	public static void printBarrier() {
		System.out.println("\n--------------------\n--------------------\n");
	}
	public static void runGame(int game) throws FileNotFoundException {
		if (game == 0) {
			Hangman();
		} else if (game == 1) {
//			TicTacToe();
		}
	}
	public static void Hangman() throws FileNotFoundException {
		String word = chooseWord();
		System.out.println("The word will be: " + word);
		String hiddenWord = makeHiddenWord(word);
		int result = formatGame(word, hiddenWord);
	}
	public static int formatGame(String word, String hiddenWord) {
		int count = 0;
		char letter = '*';
		Scanner console = new Scanner(System.in);
		System.out.println(hiddenWord);
		while (!(hiddenWord.equalsIgnoreCase(word)) && count <= 7) {
			System.out.print("Please guess a letter: ");
			String letterAsString = console.next();
			System.out.println();
			while (letterAsString.length() != 1 && !(Character.isLetter(letter))) {
				System.out.print("Please guess a valid character: ");
				letterAsString = console.next();
				if (letterAsString.length() == 1) {
					letter = letterAsString.charAt(0);
				}
			}
			hiddenWord = guessLetter(word, hiddenWord, letter);
		}
		return 0;
	}
	public static String guessLetter(String word, String hiddenWord, char letter) {
		int wrongCount = 0;
		char Arr_hiddenWord[] = hiddenWord.toCharArray();
		String initialhiddenWord = hiddenWord;
		for (int i = 0; i < word.length(); ++i) {
			if (word.charAt(i) == letter) {
				Arr_hiddenWord[i] = letter;
			}
		}
		hiddenWord = String.valueOf(Arr_hiddenWord);
		if (initialhiddenWord.equals(hiddenWord)) {
			++wrongCount;
		}
		//printGraphic(wrongCount);
		//after 7 wrong guesses, return hiddenWord as an empty string
		//so hiddenWord will be passed to this method but until it executes the last file it wont alter hiddenWord
		System.out.println(hiddenWord);
		return hiddenWord;
	}			
	public static String makeHiddenWord(String word) {
		int length = word.length();
		String hiddenWord = "_";
		for (int i = 1; i < word.length(); ++i) {
			if (word.charAt(i) == ' ') {
				hiddenWord = hiddenWord + "  ";
			} else if (word.charAt(i) == '-') {
				hiddenWord = hiddenWord + " -";
			} else {
				hiddenWord = hiddenWord + " _";
			}
		}
		return hiddenWord;
	}
	public static String chooseWord() throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		Random getLine = new Random();
		int bound1 = 0;
		int bound2 = 0;
		String wordCPU = "";
		printBarrier();
		System.out.print("Would you like to play against a friend or the Computer?\n");
		System.out.print("Enter \"F\" to play a friend or \"C\" to play the Computer: ");
		String opponent = console.nextLine();
		while (!(opponent.equals("F")) && !(opponent.equals("C"))) {
			System.out.print("Enter \"F\" to play a friend or \"C\" to play the Computer: ");
			opponent = console.nextLine();
		}
		printBarrier();
		if (opponent.equals("F")) {
			System.out.print("You have chosen to play against a friend. Have your friend enter a valid word or phrase for you to guess: ");
			String word = console.nextLine();
			return word;
		} else if (opponent.equals("C")) {
			System.out.print("You have chosen to play against the Computer.\nPlease select a difficulty:\n");
			printBarrier();
			System.out.println("Type \"E\" for easy\nType \"M\" for medium\nType \"H\" for hard\nType \"X\" for extreme");
			printBarrier();
			System.out.print("Type the difficulty you want to play here: ");
			String difficulty = console.nextLine();
			while (!(difficulty.equals("E")) && !(difficulty.equals("M")) && !(difficulty.equals("H")) && !(difficulty.equals("X"))) {
				System.out.print("Please enter a valid difficulty: ");
				difficulty = console.nextLine();
			}
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
			System.out.print("Would you like to guess a word or a phrase?\nEnter \"W\" to guess a word and enter \"P\" tp guess a phrase: ");
			String status = console.nextLine();
			while(!(status.equals("W")) && !(status.equals("P"))) {
				System.out.print("Enter \"W\" to guess a word and enter \"P\" to guess a phrase: ");
				status = console.nextLine();
			}
			printBarrier();
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


































