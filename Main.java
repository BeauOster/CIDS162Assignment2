/*
----------------------------------------------------
Author: Beau Oster
Contact: beau.oster@my.uwrf.edu

Created: 3/27/2023
Modified:4/6/2023

Description: 

This program is a program that simulates a pokedex from the popular series
Pokemon. The main function of a pokedex is the availability for the user
to search and look at a pokemon's information. The pokemon's information can
include: ID number, name, Classification(description), type 1, type2.
All pokemon data is imported from a .csv file and stored into arrays.
The user can then simply enter a name or number to see that pokemon's
information. A total of 801 pokemon's data has been imported.

--------------------------------------------------------
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
import java.util.Arrays;
import java.io.IOException;

class Main {
  public static void main(String[] args) {
    
    int[] pokeDexID = new int[801];
    String[] pokeNames = new String[801];
    String[] pokeClassification = new String[801];
    
    // pokemonTypes - The typing of each pokemon. The first [] represents the pokemon,
    // the second [] represents it's typing. If there is not secondary typing,
    // it is set to none (i.e water/none).
    String[][] pokemonTypes = new String [801][2];

    int currentPokemonIndex;
    String userPokemonChoice;

    // To check rather or not the user typed correctly.
    boolean isValidInput = false;

    // userContinues - Used to continue the pokedex loop.
    boolean userContinues = true;
    Scanner userInput = new Scanner(System.in);

    try {

      //Opens the file for reading
      File importedPokemonData = new File("pokemon.csv");
      Scanner filePokemonDataScanner = new Scanner(importedPokemonData);

      // Skips the header row.
      filePokemonDataScanner.nextLine();

      // Reads data from each line until the end of the file/up to 801 lines.
      // You could probablly use a .length here instead of 801, but I'm doing
      // this as a precaution.
      for (int i = 0; filePokemonDataScanner.hasNextLine() && i < 801; i++) {

        // This reads data on each line, and stores it in a string for later.
        // For example, a line has an ID, Name, Classification, and typing
        // That line is then temporarily stored in the string readPokemonLines.
        String readPokemonLines = filePokemonDataScanner.nextLine();

        // Splits the string of data into an array of strings wherever there is a ","
        // For example, {"1", "Bulbasaur", "Seed Pokémon", "grass", "poison"}
        String[] splitReadPokemonData = readPokemonLines.split(",");

        // Below passes the data to the corresponding arrays.
        pokeDexID[i] = Integer.parseInt(splitReadPokemonData[0]);
        // ID is a number, not a string, so we need to parse it into an int.

        pokeNames[i] = splitReadPokemonData[1];
        pokeClassification[i] = splitReadPokemonData[2];
        pokemonTypes[i][0] = splitReadPokemonData[3];
        pokemonTypes[i][1] = splitReadPokemonData[4];
        
      }

      filePokemonDataScanner.close();
      
      // Handle the FileNotFoundException if the file is not found.
    } catch (FileNotFoundException x) {
      
      System.err.println("File not found. Please check the files.");
      return;
  
    }
    // End try-catch, file scanning

    //---------------------------BEGIN POKEDEX LOOP-------------------------
    
    while (userContinues == true) {

     // Prompt the user to enter a search type.
     System.out.println("\nWelcome to Beau's Pokedex!" + 
                      "\nWould you like to serach by name or number?");
     String userChoice = userInput.next();

    // ********Search by name********
    // Note: A switch case might be better instead, but I don't have time to redo.
     if (userChoice.equalsIgnoreCase("name")) {

      // Promt the user to enter a pokemon name.
      System.out.println("Please enter the name of the pokemon.");
      userPokemonChoice = userInput.next();

      // Go through the pokeNames array and find a match for
      // the user's search term.
      for (int i = 0; i < pokeNames.length; i++) {

       //If a match is found. . .
       if (pokeNames[i] != null && pokeNames[i].equalsIgnoreCase(userPokemonChoice)) {

        // . . . Set the currentPokemonIndex variable.
        currentPokemonIndex = i;
        isValidInput = true;
        // And print out that pokemon's info.
        printPokemonInfo(currentPokemonIndex, pokeDexID, pokeNames, pokeClassification, pokemonTypes);

        // Break out of the for loop since a match is found.
        break;
        
       }
 
      }

     // ********Search by number********
     } else if (userChoice.equalsIgnoreCase("number")) {

        // Prompt the user to enter a pokemon number
        System.out.println("Please enter the number of the pokemon.");
        userPokemonChoice = userInput.next();
       
        try {
          
         // Go through the pokeDexID array and find a match for
         // the user's search term.
         for (int i = 0; i < pokeDexID.length; i++) {

          // If a match is found, the same thing above happens below.
          // However, since userPokemonChouce is a string and pokeDexID is an int,
          // the string must be parsed to an integer (hence the try-catch).
          if (pokeDexID != null && pokeDexID[i] == Integer.parseInt(userPokemonChoice)) {
        
          currentPokemonIndex = i;
          isValidInput = true;
          printPokemonInfo(currentPokemonIndex, pokeDexID, pokeNames, pokeClassification, pokemonTypes);
          break;
        
          }
           
        } // end for loop
        
        // Catch any NumberFormatException that might be thrown if
        // the user enters a non-integer value (i.e abc) since letters can't be parsed.
      } catch (NumberFormatException nfe) {
            System.err.println("Error. Invalid pokemon ID. ID must be a number. Please try again.");
      }
       
     } //end user selection.

     // In case none of the inputs are valid, error message is displayed.
     // Like incorrectly typing name/number, or giving 0 for a number etc.
     if (!isValidInput) {
       System.out.println("Invalid input/Pokemon not found. Please try again. Enter a name or pokemon ID/valid input.\n");
     }

     //User continuation begins here.
     if (isValidInput == true) {

      // isValidInput is set to false to allow for a new search.
      isValidInput = false;

      // Prompts the user if they would like to search again
      System.out.println("\nWould you like to continue? (Y/N)");
      String userContinuation = userInput.next();

      // Exits the loop if no.
      if (userContinuation.equalsIgnoreCase("N")) {

       System.out.println("Thank you for using Beau's Pokedex.");
       userContinues = false;
       
      } 
       
     }
      
    }//-------------------END POKEDEX LOOP-------------------
    
  }
  
/* printPokemonInfo - Prints all of the selected pokemon's information.
|@Param currentIndex - The current index of the pokemon the user entered.
|@Param pokeIDS - An array of numbers containing the poke dex ID of the pokemon.
|@Param pokeNames - An array of strings containing all the names of the pokemon.
|@Param pokeClassification - An array of strings containing each pokemon's
|description.
|@Param pokeTypes - A 2D string array containg each pokemon's types. The first
|[] corresponds to the pokemon, the scond [] corresponds to that pokemon's typing.
*/
  public static void printPokemonInfo(int currentIndex, int[] pokeIDS, String[] pokeNames, String[] pokeClassification, String[][] pokeTypes) {

    System.out.println("\nPokedex ID: " + pokeIDS[currentIndex]);
    System.out.println("Name: " + pokeNames[currentIndex]);
    System.out.println("Pokemon Classification: " + pokeClassification[currentIndex]);
    System.out.println("Pokemon Typing: " + pokeTypes[currentIndex][0] + "/" + pokeTypes[currentIndex][1]); 
  
  }
  
}