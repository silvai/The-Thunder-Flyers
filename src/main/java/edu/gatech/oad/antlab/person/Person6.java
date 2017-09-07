package edu.gatech.oad.antlab.person;
import java.util.Collections;
import java.util.Arrays;

/**
 *  A simple class for person 6
 *  returns their name and a
 *  modified string 
 *
 * @author Leon Zhu
 * @version 1.1
 */
public class Person6 {
    /** Holds the persons real name */
    private String name;
	 	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
	 public Person6(String pname) {
	   name = pname;
	 }
	/**
	 * This method should take the string
	 * input and return its characters in
	 * random order.
	 * given "gtg123b" it should return
	 * something like "g3tb1g2".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
	  //Person 2 put your implementation here
	  if (input.length() = 0) {
	  	throw new IllegalArgumentException();
	  }
	  if (input.length() > 0 && input.length() <= 2) {
	  	return input;
	  }
	  char[] arr = new char[input.length()];
	  for( int i=0; i<input.length(); i++) {
	  	arr[i] = input.charAt(i);
	  }
	  Collections.shuffle(Arrays.asList(arr));
	  String out = new String(arr);
	  return out;
	}
	/**
	 * Return a string rep of this object
	 * that varies with an input string
	 *
	 * @param input the varying string
	 * @return the string representing the 
	 *         object
	 */
	public String toString(String input) {
	  return name + calc(input);
	}
}
