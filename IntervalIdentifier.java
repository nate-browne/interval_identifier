/*
 * Name: Nate Browne
 * Date: 28 July 2017
 * File: IntervalIdentifier.java
 * Version: 2.0
 * This program identifies various types of intervals entered in by the user,
 * calculates them, prints it back, and plays the tones corresponding to it. It
 * plays them starting from middle C and going up.
 */


import java.util.*;
import org.jfugue.*;

/**
 * Main class for the program.
 */
public class IntervalIdentifier {

  /* Declaration of constants and instance variables */

  // Define the distances between notes as constants
  private static final int WHOLE_STEP = 2;
  private static final int HALF_STEP = 1;
  private static final int FIFTH = 7;
  private static final int BASE_NOTE = 5;
  private static final int OCTAVE = 8;
  private static final int OCTAVE_INTERVAL = 12;

  // User input used by scanner
  private static String qualityChoice;
  private static int noteChoice, intervalChoice;

  // Used to make the note go up the octave
  private static boolean goUp;

  // Used when reporting results back to the user
  private static int originalInterval, octaveCounter, newVal;

  // Create the scanner
  private static Scanner scan = new Scanner(System.in);

  // Create the music player that will play the notes
  private static Player notePlayer = new Player();

  // Array of note names
  private static final String[] NOTES = {"C", "Db", "D", "Eb", "E", "F", "F#",
    "G", "Ab", "A", "Bb", "B"};


  /**
   * Main method. Allows user to select interval type, distance, and starting
   * note.
   */
  public static void main(String[] args) {

    System.out.println("\nWelcome to the IntervalIdentifier!!\n");

    // Start the loop
    while(true) {

      // Reset the boolean to false
      goUp = false;

      try {

        // First, grab the desired starting note
        for(int i = 0; i < NOTES.length; i++) {

          System.out.println("Note option #" + i + ": " + NOTES[i]);
        }

        System.out.print("Enter the starting note (as a number): ");
        noteChoice = scan.nextInt() % NOTES.length;


        // Next, grab the interval
        System.out.println("Enter in the desired interval to calculate");
        System.out.print("Intervals are entered with the number corresponding "
          + "to the amount of notes away from the base (5th = 4, unison = 0, " +
          "etc): ");

        // Mod by the number of unique notes in a scale to avoid an
        // ArrayIndexOutOfBoundsException
        originalInterval = scan.nextInt();
        intervalChoice = originalInterval % (OCTAVE - 1);
        octaveCounter = originalInterval / (OCTAVE - 1);


        // Last, grab the quality of the interval
        System.out.print("Enter an interval quality: ((a)ugmented, (d)iminished"
          + ", (M)ajor, (m)inor): ");

        qualityChoice = scan.next();


        // Parse which quality user has selected
        switch(qualityChoice) {

          case "a":

            makeAugmentedInterval(noteChoice, intervalChoice);
            break;
          case "d":

            makeDiminishedInterval(noteChoice, intervalChoice);
            break;
          case "m":

            makeMinorInterval(noteChoice, intervalChoice);
            break;
          case "M":

            makeMajorInterval(noteChoice, intervalChoice);
            break;
          default:

            System.err.println("You entered an invalid quality. Try again.");
            break;
        }

      // Handle user typing a string for a number
      } catch(InputMismatchException e) {

        // Close input going to the scanner
        scan.close();

        // Report error
        System.err.print("Caught InputMismatchException!!");
        System.err.println("Make sure to use numbers to select note/interval!");

        // Exit the program with error
        System.err.println("Exiting...\n");
        System.exit(1);

      // Handle user typing EOF
      } catch(NoSuchElementException e) {

        // Close input going to the scanner
        scan.close();

        // Exit the program normally
        System.err.println("\nExiting...\n");
        System.exit(0);
      }
    }
  }

  /**
   * This method calculates and prints out the augmented interval for the
   * base note entered.
   * @param note base note to use
   * @param interval desired interval to create
   */
  public static void makeAugmentedInterval(int note, int interval) {

    // Used to correctly pitch the interval
    int newOctave = 0;

    // Figure out which interval to create
    switch(interval) {

      case 0:

        newVal = createUnison(note);
        break;
      case 1:

        newVal = createSecond(note);
        break;
      case 2:

        newVal = createThird(note);
        break;
      case 3:

        newVal = createFourth(note);
        break;
      case 4:

        newVal = createFifth(note);
        break;
      case 5:

        newVal = createSixth(note);
        break;
      case 6:

        newVal = createSeventh(note);
        break;
    }

    // Make the interval augmented
    newVal = (newVal + HALF_STEP) % NOTES.length;

    // Report result to user
    System.out.println("\nFor the note " + NOTES[note] + ", the interval of an "
      + "augmented " + ++originalInterval + " results in the note "
      + NOTES[newVal] + " " + octaveCounter + " octave(s) above the original " +
      "note.");

    // Play the interval back to the user
    System.out.println("This interval sounds like this: \n");
    notePlayer.play(NOTES[note] + BASE_NOTE);

    if(goUp) {

      newOctave = BASE_NOTE + 1;
    } else {

      newOctave = BASE_NOTE + octaveCounter;
    }

    notePlayer.play(NOTES[newVal] + newOctave);
  }

  /**
   * This method calculates and prints out the diminished interval for the
   * base note entered.
   * @param note base note to use
   * @param interval desired interval to create
   */
  public static void makeDiminishedInterval(int note, int interval) {

    // Used to correctly pitch the interval
    int newOctave = 0;

    // Figure out which interval to create
    switch(interval) {

      case '0':

        newVal = createUnison(note);
        break;
      case 1:

        newVal = createSecond(note);
        break;
      case 2:

        newVal = createThird(note);
        break;
      case 3:

        newVal = createFourth(note);
        break;
      case 4:

        newVal = createFifth(note);
        break;
      case 5:

        newVal = createSixth(note);
        break;
      case 6:

        newVal = createSeventh(note);
        break;
    }

    // Make the interval diminished
    newVal = (newVal - HALF_STEP) % NOTES.length;

    // Report result to user
    System.out.println("\nFor the note " + NOTES[note] + ", the interval of a "
      + "diminished " + ++originalInterval + " results in the note "
      + NOTES[newVal] + " " + octaveCounter + " octave(s) above the original " +
      "note.");

    // Play the interval back to the user
    System.out.println("This interval sounds like this: \n");
    notePlayer.play(NOTES[note] + BASE_NOTE);

    if(goUp) {

      newOctave = BASE_NOTE + 1;
    } else {

      newOctave = BASE_NOTE + octaveCounter;
    }

    notePlayer.play(NOTES[newVal] + newOctave);
  }

  /**
   * This method calculates and prints out the major interval for the base note
   * entered.
   * @param note base note to use
   * @param interval desired interval to create
   */
  public static void makeMajorInterval(int note, int interval) {

    // Used to correctly pitch the interval
    int newOctave = 0;

    // Figure out which interval to create
    switch(interval) {

      case 0:

        newVal = createUnison(note);
        break;
      case 1:

        newVal = createSecond(note);
        break;
      case 2:

        newVal = createThird(note);
        break;
      case 3:

        newVal = createFourth(note);
        break;
      case 4:

        newVal = createFifth(note);
        break;
      case 5:

        newVal = createSixth(note);
        break;
      case 6:

        newVal = createSeventh(note);
        break;
    }

    // Report result to user
    System.out.println("\nFor the note " + NOTES[note] + ", the interval of a "
      + "major " + ++originalInterval + " results in the note " + NOTES[newVal]
      + " " + octaveCounter + " octave(s) above the original note.");

    // Play the interval back to the user
    System.out.println("This interval sounds like this: \n");
    notePlayer.play(NOTES[note] + BASE_NOTE);

    if(goUp) {

      newOctave = BASE_NOTE + 1;
    } else {

      newOctave = BASE_NOTE + octaveCounter;
    }

    notePlayer.play(NOTES[newVal] + newOctave);
  }

  /**
   * This method calculates and prints out the minor interval for the base note
   * entered.
   * @param note base note to use
   * @param interval desired interval to create
   */
  public static void makeMinorInterval(int note, int interval) {

    // Used to correctly pitch the interval
    int newOctave = 0;

    // Figure out which interval to create
    switch(interval) {

      case '0':

        newVal = createUnison(note);
        break;
      case 1:

        newVal = createSecond(note);
        break;
      case 2:

        newVal = createThird(note);
        break;
      case 3:

        newVal = createFourth(note);
        break;
      case 4:

        newVal = createFifth(note);
        break;
      case 5:

        newVal = createSixth(note);
        break;
      case 6:

        newVal = createSeventh(note);
        break;
    }

    // Eliminate perfect intervals (intervals without minor variants)
    if(interval != 0 && interval != 3 && interval != 4) {

      newVal = (newVal - HALF_STEP) % NOTES.length;
    }

    // Report result to user
    System.out.println("\nFor the note " + NOTES[note] + ", the interval of a "
      + "minor " + ++originalInterval + " results in the note " + NOTES[newVal]
      + " " + octaveCounter + " octave(s) above the original note.");

    // Play the interval back to the user
    System.out.println("This interval sounds like this: \n");
    notePlayer.play(NOTES[note] + BASE_NOTE);

    if(goUp) {

      newOctave = BASE_NOTE + 1;
    } else {

      newOctave = BASE_NOTE + octaveCounter;
    }

    notePlayer.play(NOTES[newVal] + newOctave);
  }

  /**
   * This method creates an interval of a unison above the given starting note.
   * @param starting note to use as the base
   * @return the base
   */
  public static int createUnison(int starting) {

    return starting;
  }
  /**
   * This method creates an interval of a 2nd above the given starting note. The
   * return value is modded by the size of the array to wrap back around it.
   * @param starting note to use as the base
   * @return the note a second above the base
   */
  public static int createSecond(int starting) {

    if(starting + WHOLE_STEP >= NOTES.length) {

      goUp = true;
    }

    return (starting + WHOLE_STEP) % NOTES.length;
  }

  /**
   * This method creates an interval of a 3rd above the given starting note. The
   * return value is modded by the size of the array to wrap back around it.
   * @param starting note to use as the base
   * @return the note a third above the base
   */
  public static int createThird(int starting) {

    if(starting + (WHOLE_STEP * WHOLE_STEP) >= NOTES.length) {

      goUp = true;
    }

    return (starting + (WHOLE_STEP * WHOLE_STEP)) % NOTES.length;
  }

  /**
   * This method creates an interval of a 4th above the given starting note. The
   * return value is modded by the size of the array to wrap back around it.
   * @param starting note to use as the base
   * @return the note a fourth above the base
   */
  public static int createFourth(int starting) {

    if(starting + (FIFTH - WHOLE_STEP) >= NOTES.length) {

      goUp = true;
    }

    return (starting + (FIFTH - WHOLE_STEP)) % NOTES.length;
  }

  /**
   * This method creates an interval of a 5th above the given starting note. The
   * return value is modded by the size of the array to wrap back around it.
   * @param starting note to use as the base
   * @return the note a fifth above the base
   */
  public static int createFifth(int starting) {

    if(starting + FIFTH >= NOTES.length) {

      goUp = true;
    }

    return (starting + FIFTH) % NOTES.length;
  }

  /**
   * This method creates an interval of a 6th above the given starting note. The
   * return value is modded by the size of the array to wrap back around it.
   * @param starting note to use as the base
   * @return the note a sixth above the base
   */
  public static int createSixth(int starting) {

    if(starting + (FIFTH + WHOLE_STEP) >= NOTES.length) {

      goUp = true;
    }

    return (starting + (FIFTH + WHOLE_STEP)) % NOTES.length;
  }

  /**
   * This method creates an interval of a 7th above the given starting note. The
   * return value is modded by the size of the array to wrap back around it.
   * @param starting note to use as the base
   * @return the note a seventh above the base
   */
  public static int createSeventh(int starting) {

    if(starting + (OCTAVE_INTERVAL - WHOLE_STEP) >= NOTES.length) {

      goUp = true;
    }

    return (starting + (OCTAVE_INTERVAL - WHOLE_STEP)) % NOTES.length;
  }
}
