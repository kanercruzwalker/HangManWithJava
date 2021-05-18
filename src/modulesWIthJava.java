import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;


public class modulesWIthJava {

    //Reset Variables & PLay Game Again
    static void resetVariablesAndPlay(){
        //Set secretWord
        String secretWord = getSecretWord();

        //Set encryptedWord
        String encryptedWord = encryptWord(secretWord);

        //Set incorrectGuessCounter
        int incorrectGuessCounter = 0;

        //Declare ArrayList<String>
        ArrayList<String> lettersGuessedNew = new ArrayList<>();

        //Display encryptedWord , incorrectGuessCounter , lettersGuessed
        startGameDisplay(encryptedWord, incorrectGuessCounter, lettersGuessedNew);

        //Needed Variables Have Been Set
        //Game Logic May Commence

        //Playing when incorrectGuessCounter < 6 AND secretWord = encryptedWord
        while (incorrectGuessCounter < 6 && !secretWord.equals(encryptedWord)){

            //Set letterGuessed
            char letterGuessedMainNow = getInput(lettersGuessedNew);

            //Add letterGuessed to ArrayList
            addGuessToLettersGuessed(letterGuessedMainNow, lettersGuessedNew);

            //Set frequencyOfLetterInWord
            int frequencyOfLetterInWord = compareGuessToWord(letterGuessedMainNow , secretWord);

            //Set boolean letterInWord
            boolean guessValidity = validateGuess(frequencyOfLetterInWord);

            //If(letterInWord == true)
            if (guessValidity) {
                //Set encryptedWord = showCorrectLetter(secretWord, letterGuessedArray, encryptedWord)
                encryptedWord = correctGuessMade(secretWord, letterGuessedMainNow, encryptedWord);
                //Display encryptedWord, incorrectGuessCounter, lettersGuessedArray
                startGameDisplay(encryptedWord, incorrectGuessCounter, lettersGuessedNew);
            }
            //Else (letterInWord == false)
            else{
                //Set incorrectGuessCounter with incrementIntMethod()
                incorrectGuessCounter = incorrectGuessMade(incorrectGuessCounter);
            }

            //Display encryptedWord, incorrectGuessCounter , lettersGuessedArray
            startGameDisplay(encryptedWord, incorrectGuessCounter, lettersGuessedNew);
        }

        //If (incorrectGuessCounter == 6),playerLost, call playerLostMethod , call playAgainMethod
        if (incorrectGuessCounter == 6){
            //Display gameOverPromptLost
            System.out.println("================");
            System.out.println("GAME OVER");
            System.out.println("You lost.");
            //Call playAgain Method
            playAgain(encryptedWord , incorrectGuessCounter , lettersGuessedNew);

        }

        //If (encryptedWord == secretWord && incorrectGuessCounter < 6),playerWon, call playerWonMethod, call playAgainMethod
        if (encryptedWord.equals(secretWord.toLowerCase()) && incorrectGuessCounter < 6){
            //Display gameOverPromptWon
            System.out.println("================");
            System.out.println("GAME OVER");
            System.out.println("YOU WON!");
            //Call playAgain method
            playAgain(encryptedWord , incorrectGuessCounter , lettersGuessedNew );
        }

    }

    //Play Again Prompt
    static void playAgain(String encryptedWord , int incorrectGuessCounter , ArrayList<String> lettersGuessedNew ){
        //welcomePrompt
        Scanner input = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Would you like to play again?");
        System.out.println("Input (y) or (n)");

        char toPlayOrNotToPlay = Character.toLowerCase(input.next().charAt(0));

        //inputValidation
        while(Character.toString(toPlayOrNotToPlay).charAt(0) != 'y' && Character.toString(toPlayOrNotToPlay).charAt(0) != 'n'){
            System.out.println("Please enter a valid input (y) or (n)");
            toPlayOrNotToPlay = Character.toLowerCase(input.next().charAt(0));
        }

        //userWantsToPlay
        if (Character.toString(toPlayOrNotToPlay).charAt(0) == 'y'){
            resetVariablesAndPlay();
            startGameDisplay(encryptedWord, incorrectGuessCounter, lettersGuessedNew);
        }

        //userDoesNotWantToPlay
        if (Character.toString(toPlayOrNotToPlay).charAt(0) == 'n'){
            System.out.println("----------------------------------------------------------------");
            System.out.println("Thanks for playing. See you next time.");
            System.exit(0);
        }
    }

    //Display ACII Character basedOnIncorrectGuessCounter
    static void displayASCII(int incorrectGuessCounter){
        switch(incorrectGuessCounter) {
            case 1:
                System.out.println("\\O");
                System.out.println(" | ");
                System.out.println("/ \\");
                break;
            case 2:
                System.out.println(" O");
                System.out.println(" | ");
                System.out.println("/ \\");
                break;
            case 3:
                System.out.println(" O");
                System.out.println(" | ");
                System.out.println("/ ");
                break;
            case 4:
                System.out.println(" O");
                System.out.println(" | ");
                System.out.println("");
                break;
            case 5:
                System.out.println(" O");
                System.out.println(" ");
                System.out.println("");
                break;
            case 6:
                System.out.println("No More Lives");
                break;
            default:
                System.out.println("\\O/");
                System.out.println(" | ");
                System.out.println("/ \\");
        }
    }

    //Numerically track incorrect guesses
    static int incorrectGuessMade(int incorrectGuessCounter){
        return incorrectGuessCounter + 1;
    }

    //Actions needed when a Correct Guess takes place
    static String correctGuessMade(String secretWord, char letterGuessed, String encryptedWord){
        StringBuilder myString = new StringBuilder(encryptedWord);

        for (int i = 0 ; i < secretWord.length() ; i++){
            if (secretWord.charAt(i) == letterGuessed){
                myString.setCharAt(i, letterGuessed);
            }
        }
        return myString.toString();
    }

    //Determine if letterGuessed is Found in word
    static boolean validateGuess(int frequencyOfLetterInWord){
        if (frequencyOfLetterInWord > 0 ){
            return true;
        }
        return false;

    }

    //Numerical count of how many times letterGuessed is in secretWord
    static int compareGuessToWord(char letterGuessed , String secretWord){
        int timesLetterFound = 0;
        for (int i = 0 ; i < secretWord.length() ; i++){
            if (secretWord.charAt(i) == letterGuessed){
                timesLetterFound++;
            }
        }
        return timesLetterFound;
    }

    //Add letterGuessed to letterGuessed ArrayList
    static void addGuessToLettersGuessed(char letterGuessed , ArrayList<String> lettersGuessed){
        lettersGuessed.add(Character.toString(letterGuessed));
    }

    //Get letterGuess from user
    static char getInput (ArrayList<String> lettersGuessedArray) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a letter a-z");
        char userInput = Character.toLowerCase(input.next().charAt(0));

        for (int i = 0 ; i < lettersGuessedArray.size(); i++){
            while(lettersGuessedArray.get(i).equals(Character.toString(userInput))){
                System.out.println("!==!==!==!==!==!==!==!==!==!");
                System.out.println("You already guessed that letter.");
                System.out.println("Please guess a new letter");
                userInput = Character.toLowerCase(input.next().charAt(0));
            }
        }

        while (userInput < 'a' || userInput > 'z'){
            System.out.println("Please enter a valid input a-z");
            userInput = Character.toLowerCase(input.next().charAt(0));
        }
        return userInput;
    }

    //Game Display, encryptedWord, LettersGuessed, guessCount
    //Display String encryptedWord , int incorrectGuessCounter , ArrayList<String> lettersGuessedArray , ascIICharacter method
    static void startGameDisplay(String encryptedWord, int incorrectGuessCounter, ArrayList<String> lettersGuessedNew){
        System.out.println("----------------------------------------------------------------");
        displayASCII(incorrectGuessCounter);
        System.out.println("Guess the following word: " + encryptedWord);
        System.out.println("Letters Guessed : " + lettersGuessedNew);
        System.out.println("Incorrect Guess Count : " + incorrectGuessCounter);
        System.out.println("----------------------------------------------------------------");
    }

    //Greeting Screen
    static void initialDisplay(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to hang man!");
        System.out.println("You are allowed 6 incorrect guesses.");
        System.out.println("Would you like to play? ");
        System.out.println("Enter (y) or (n) below");

        char toPlayOrNotToPlay = Character.toLowerCase(input.next().charAt(0));

        while(Character.toString(toPlayOrNotToPlay).charAt(0) != 'y' && Character.toString(toPlayOrNotToPlay).charAt(0) != 'n'){
            System.out.println("Please enter a valid input (y) or (n)");
            toPlayOrNotToPlay = Character.toLowerCase(input.next().charAt(0));
        }
        //If User Does Not Want To Play / Could validate input
        if (Character.toString(toPlayOrNotToPlay).equals("n")){
            System.out.println("No worries. See you next time.");
            System.exit(0);
        }
        if (Character.toString(toPlayOrNotToPlay).equals("y")){
            return;
        }

    }

    //Encrypt SecretWord
    static String encryptWord(String secretWord){
        String encodedSecretWord = "";
        int lengthOfWord = secretWord.length();
        for (int i = 0 ; i < lengthOfWord ; i++){
            encodedSecretWord = encodedSecretWord.concat("-");
        }
        return encodedSecretWord;
    }

    //Get Word From File
    static String getSecretWord(){

        String word = "";
        File myFile = new File("src/Data.txt");

        try {
            File myObj = new File("src/Data.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Declare int linesInFile = 0
        int count = 0;

        //Read File
        //Count how many lines
        try {
            // create a new file object
            File file = new File("src/Data.txt");

            // create an object of Scanner
            // associated with the file
            Scanner sc = new Scanner(file);

            // read each line and
            // count number of lines
            while(sc.hasNextLine()) {
                sc.nextLine();
                count++;
            }

            // close scanner
            sc.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

        //Declare randomLineNumber
        //Generate random number > 0 && <
        int randomLineNumber = (int)(Math.random() * count);;

        int n = randomLineNumber; // The line number
        try{
            String line = Files.readAllLines(Paths.get("src/Data.txt")).get(n);
            word = line.toLowerCase();
//            System.out.println(line);
        }
        catch(IOException e){
            System.out.println(e);
        }

        return word.toLowerCase();
    }



    //Main Method
    public static void main(String[] args) {
        //Call welcomePrompt method
        initialDisplay();

        //Declare String secretWord
        //Set with getSecretWord() method
        String secretWord = getSecretWord();

        //Declare String encryptedWord
        //Set with encryptedWord(secretWord) method
        String encryptedWord = encryptWord(secretWord);

        //Set incorrectGuessCounter = 0;
        int incorrectGuessCounter = 0;

        //Declare ArrayList<String>
        ArrayList<String> lettersGuessedNew = new ArrayList<>();

        //Display encryptedWord , incorrectGuessCounter , lettersGuessed
        //call gamePrompt(encryptedWord, incorrectGuessCounter, letterGuessedArray) method
        startGameDisplay(encryptedWord, incorrectGuessCounter, lettersGuessedNew);

        //Needed Variables Have Been Set
        //Game Logic May Commence

        //Playing when incorrectGuessCounter < 6 AND secretWord = encryptedWord
        while (incorrectGuessCounter < 6 && !secretWord.equals(encryptedWord)){

            //Set letterGuessed
            char letterGuessedMainNow = getInput(lettersGuessedNew);

            //Add letterGuessed to ArrayList
            addGuessToLettersGuessed(letterGuessedMainNow, lettersGuessedNew);

            //Set frequencyOfLetterInWord
            int frequencyOfLetterInWord = compareGuessToWord(letterGuessedMainNow , secretWord);

            //Set boolean letterInWord
            boolean guessValidity = validateGuess(frequencyOfLetterInWord);

            //If(letterInWord == true)
            if (guessValidity) {
                //Set encryptedWord = showCorrectLetter(secretWord, letterGuessedArray, encryptedWord)
                encryptedWord = correctGuessMade(secretWord, letterGuessedMainNow, encryptedWord);
                //Display encryptedWord, incorrectGuessCounter, lettersGuessedArray
                startGameDisplay(encryptedWord, incorrectGuessCounter, lettersGuessedNew);
            }
            //Else (letterInWord == false)
            else{
                //Set incorrectGuessCounter with incrementIntMethod()
                incorrectGuessCounter = incorrectGuessMade(incorrectGuessCounter);
            }

            //Display encryptedWord, incorrectGuessCounter , lettersGuessedArray
            startGameDisplay(encryptedWord, incorrectGuessCounter, lettersGuessedNew);
        }

        //If (incorrectGuessCounter == 6),playerLost, call playerLostMethod , call playAgainMethod
        if (incorrectGuessCounter == 6){
            //Call gameOverLostMessage
            System.out.println("================");
            System.out.println("GAME OVER");
            System.out.println("You lost.");
            //Call playAgainPrompt(encryptedWord, incorrectGuessCounter, lettersGuessedArray) method
            playAgain(encryptedWord , incorrectGuessCounter , lettersGuessedNew);

        }

        //If (encryptedWord == secretWord && incorrectGuessCounter < 6),playerWon, call playerWonMethod, call playAgainMethod
        if (encryptedWord.equals(secretWord.toLowerCase()) && incorrectGuessCounter < 6){
            //Call gameOverWonMessage() method
            System.out.println("================");
            System.out.println("GAME OVER");
            System.out.println("YOU WON!");
            //Call playAgainPrompt() method
            playAgain(encryptedWord , incorrectGuessCounter , lettersGuessedNew);
        }
    }
}