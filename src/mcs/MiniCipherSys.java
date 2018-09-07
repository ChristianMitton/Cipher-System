package mcs;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a mini cipher system.
 * 
 * @author RU NB CS112
 */
public class MiniCipherSys {
	
	/**
	 * Circular linked list that is the sequence of numbers for encryption
	 */
	SeqNode seqRear;
	
	/**
	 * Makes a randomized sequence of numbers for encryption. The sequence is 
	 * stored in a circular linked list, whose last node is pointed to by the field seqRear
	 */
	public void makeSeq() {
		// start with an array of 1..28 for easy randomizing
		int[] seqValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < seqValues.length; i++) {
			seqValues[i] = i+1;
		}
		
		// randomize the numbers
		Random randgen = new Random();
 	        for (int i = 0; i < seqValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = seqValues[i];
	            seqValues[i] = seqValues[other];
	            seqValues[other] = temp;
	        }
	     
	    // create a circular linked list from this sequence and make seqRear point to its last node
	    SeqNode sn = new SeqNode();
	    sn.seqValue = seqValues[0];
	    sn.next = sn;
	    seqRear = sn;
	    for (int i=1; i < seqValues.length; i++) {
	    	sn = new SeqNode();
	    	sn.seqValue = seqValues[i];
	    	sn.next = seqRear.next;
	    	seqRear.next = sn;
	    	seqRear = sn;
	    }
	}
	
	/**
	 * Makes a circular linked list out of values read from scanner.
	 */
	public void makeSeq(Scanner scanner) 
	throws IOException {
		SeqNode sn = null;
		if (scanner.hasNextInt()) {
			sn = new SeqNode();
		    sn.seqValue = scanner.nextInt();
		    sn.next = sn;
		    seqRear = sn;
		}
		while (scanner.hasNextInt()) {
			sn = new SeqNode();
	    	sn.seqValue = scanner.nextInt();
	    	sn.next = seqRear.next;
	    	seqRear.next = sn;
	    	seqRear = sn;
		}
	}
	
	/**
	 * Implements Step 1 - Flag A - on the sequence.
	 */
	void flagA() {
	    // COMPLETE THIS METHOD
		SeqNode tail = seqRear;
		SeqNode ptr = seqRear;
		SeqNode prev = null;
		
		//case where tail contains flag A
		if(ptr.seqValue == 27) {
			int temp = ptr.next.seqValue;
			ptr.next.seqValue = 27;
			ptr.seqValue = temp;
			return;
			
		}
		
		//case where flag A is in any node besides tail
		while (ptr.next != tail) {
			prev = ptr;
			ptr = ptr.next;
			
			if (ptr.seqValue == 27) {
				//If 27 is found, all pointers are shifted to the right once
				//prev points to 27
				prev = ptr;
				//ptr points to the number to be swapped
				ptr = ptr.next;
				
				//swap
				prev.seqValue = ptr.seqValue;
				ptr.seqValue = 27;
				return;
				
			}
		}

	}
	
	/**
	 * Implements Step 2 - Flag B - on the sequence.
	 */
	void flagB() {
	    // COMPLETE THIS METHOD
		SeqNode tail = seqRear;
		SeqNode ptr = seqRear;
		SeqNode prev = null;
		
		//case where tail contains flag B
		if(ptr.seqValue == 28) {
			int temp = ptr.next.seqValue;
			ptr.next.seqValue = 28;
			ptr.seqValue = temp;
			temp = ptr.next.next.seqValue;
			ptr.next.seqValue = temp;
			ptr.next.next.seqValue = 28;
			return;
			
		}
		
		//case where flag A is in any node besides tail
		while (ptr.next != tail) {
			prev = ptr;
			ptr = ptr.next;
			
			if (ptr.seqValue == 28) {
				//If 28 is found, all pointers are shifted to the right twice			
				//1st shift:
				prev = ptr;				
				ptr = ptr.next;
				//Swap values:
				prev.seqValue = ptr.seqValue;
				ptr.seqValue = 28;								
				//2nd shift:
				prev = ptr;
				ptr = ptr.next;				
				//swap
				prev.seqValue = ptr.seqValue;
				ptr.seqValue = 28;
				return;
				
			}
		}
	}

	private int getIntPositionOfFlagA() {
		SeqNode ptr = seqRear;
		int nodePositionOf27 = 0;
		int nodesPassed = 0;
		
		//Find node position of 27
		while(ptr.seqValue != 27) {
			ptr = ptr.next;
			nodesPassed++;
		}
		//ptr points to node with 27, nodesPassed contains number of nodes passed before reaching 27. Add 1 to get 27's position
		nodePositionOf27 = nodesPassed + 1;
		
		return nodePositionOf27;
	}
	
	private int getIntPositionOfFlagB() {
		SeqNode ptr = seqRear;
		int nodePositionOf28 = 0;
		int nodesPassed = 0;

		//Find node position of 28
		nodesPassed = 0;
		while(ptr.seqValue != 28) {
			ptr = ptr.next;
			nodesPassed++;
		}
		//ptr points to node with 28, nodesPassed contains number of nodes passed before reaching 28. Add 1 to get 28's position
		nodePositionOf28 = nodesPassed + 1; 
		return nodePositionOf28; 
	}
	
	/**
	 * Implements Step 3 - Triple Shift - on the sequence.
	 */
	void tripleShift() {
	    // COMPLETE THIS METHOD
		SeqNode ptr = seqRear;
		SeqNode head = seqRear.next;
		SeqNode nodeBeforeFlagA = null;
		SeqNode nodeAfterFlagA = null;
		SeqNode nodeAfterFlagB = null;
		SeqNode nodeBeforeFlagB = null;
		SeqNode lastNodeBetweenFarthestFlagAndHead = null;
		SeqNode lastNodeBetweenFarthestFlagBndHead = null;
		SeqNode flagA = null;
		SeqNode flagB = null;
		
		int nodePositionOf27 = getIntPositionOfFlagA(); // 27
		int nodePositionOf28 = getIntPositionOfFlagB(); // 28
		
		boolean flagAIsHead = (nodePositionOf27 == 2); //true if node position of 27 = 2
		boolean flagAIsTail = (nodePositionOf27 == 1); //true if node position of 27 = 1
		
		boolean flagBIsHead = (nodePositionOf28 == 2); //true if node position of 28 = 2
		boolean flagBIsTail = (nodePositionOf28 == 1); //true if node position of 28 = 2
		
		//case where front and tail are flags
		if(flagAIsHead && flagBIsTail) {
			return;
		}
		if(flagBIsHead && flagAIsTail) {
			return;
		}
		
		//case where there are numbers after farthest flag and no numbers before closest flag (flagAIsHead or flagBIsHead) 27....28.....
		if(flagAIsHead || flagBIsHead) {
			if(flagAIsHead) { //case where flagAIsHead --- WORKS***
				//start from the head, loop until you get to node after 28, keep track of important positions
				ptr = head;
				while (ptr.next != head) {
					if(ptr.seqValue == 28) {
						flagB = ptr;
						nodeAfterFlagB = ptr.next;
					}
					if(ptr.seqValue == 27) {
						flagA = ptr;
						nodeAfterFlagA = ptr.next;
					}
					ptr = ptr.next;
				}
				lastNodeBetweenFarthestFlagAndHead = ptr;
				
				//arrange proper order				
				head = nodeAfterFlagB;
				lastNodeBetweenFarthestFlagAndHead.next = flagA;
				flagB.next = nodeAfterFlagB;
				seqRear = flagB;
					
				return;
			} else if (flagBIsHead) { // --- WORKS***
				
				ptr = head;
				while (ptr.next != head) {
					if(ptr.seqValue == 28) {
						flagB = ptr;
						nodeAfterFlagB = ptr.next;
					}
					if(ptr.seqValue == 27) {
						flagA = ptr;
						nodeAfterFlagA = ptr.next;
					}
					ptr = ptr.next;
				}
				lastNodeBetweenFarthestFlagBndHead = ptr;
				
				//arrange proper order				
				head = nodeAfterFlagA;
				lastNodeBetweenFarthestFlagBndHead.next = flagB;
				flagA.next = nodeAfterFlagA;
				seqRear = flagA;
				
				return;
			}
		}
		//case where there are numbers before closest flag and no numbers after farthest flag (flagAIsTail or flagBIsTail) ....27.....28
		else if(flagAIsTail || flagBIsTail) {
			if(flagBIsTail) { //....27....28 // --- WORKS*** 
				//starting from head, loop until ptr points to 28, keep track of important positions
				ptr = head;
				while(ptr != seqRear) {
					if(ptr.next.seqValue == 28) {
						flagB = ptr.next;
						nodeBeforeFlagB = ptr;
					}
					if(ptr.next.seqValue == 27) {
						flagA = ptr.next;
						nodeBeforeFlagA = ptr;
						nodeAfterFlagA = flagA.next;
					}
					ptr = ptr.next;
				}
				//arrange proper order
				
				seqRear = nodeBeforeFlagA;
				seqRear.next = flagA;
				
				
				return;
				
			} else if(flagAIsTail) { // ....28....27 --- WORKS***
				
				ptr = head;
				while(ptr != seqRear) {
					if(ptr.next.seqValue == 28) {
						flagB = ptr.next;
						nodeBeforeFlagB = ptr;
					}
					if(ptr.next.seqValue == 27) {
						flagA = ptr.next;
						nodeBeforeFlagA = ptr;
						nodeAfterFlagA = flagA.next;
					}
					ptr = ptr.next;
				}
				//arrange proper order
				
				seqRear = nodeBeforeFlagB;
				seqRear.next = flagB;
				
				return;
			}
			
		}
		
		//case where no flags are at end points
		//create reference to flagA node and flagB node, aswell as the 2 nodes next to it
		ptr = seqRear;
		do {
			if(ptr.seqValue == 27) {
				flagA = ptr;
				nodeAfterFlagA = ptr.next;
			}
			if(ptr.seqValue == 28) {
				flagB = ptr;
				nodeAfterFlagB = ptr.next;
			}
			if(ptr.next.seqValue == 27) {
				nodeBeforeFlagA = ptr;
			}
			if(ptr.next.seqValue == 28) {
				nodeBeforeFlagB = ptr;
			}
			if(ptr.next == seqRear) {
				lastNodeBetweenFarthestFlagBndHead = ptr;
				lastNodeBetweenFarthestFlagAndHead = ptr;
			}
			ptr = ptr.next;
			
		} while(ptr != seqRear);
		
		if(ptr.seqValue == 28) {
			nodeAfterFlagB = ptr.next;
		}
		if(ptr.seqValue == 27) {
			nodeAfterFlagA = ptr.next;
		}
		
//		System.out.println();
//		System.out.println("seqRear before swap: "+seqRear.seqValue);
//		System.out.println("head: "+head.seqValue);
//		System.out.println("nodeBeforeFlagA: "+nodeBeforeFlagA.seqValue);
//		System.out.println("flagA: "+flagA.seqValue);
//		System.out.println("nodeAfterFlagA: "+nodeAfterFlagA.seqValue);
//		System.out.println("nodeBeforeFlagB: "+nodeBeforeFlagB.seqValue);
//		System.out.println("flagB: "+flagB.seqValue);
//		System.out.println("nodeAfterFlagB: "+nodeAfterFlagB.seqValue);
//		System.out.println("Printing all nodes: ");
		
		if(nodePositionOf28 - nodePositionOf27 > 0) { //if 28 is farther than 27  --- WORKS ***
			
			//nodeAfterFlagB points to flagA
			seqRear.next = flagA;
	
			//flagB points to nodeBeforeFlagA
			flagB.next = head;
			
			head = nodeAfterFlagB;
			
			//head = nodeAfterFlagB
			nodeBeforeFlagA.next = nodeAfterFlagB;
			
			//Reset seqRear
			seqRear = nodeBeforeFlagA;
//			System.out.println("This is the new seqRear: "+seqRear.seqValue);
			
		} else if(nodePositionOf27 - nodePositionOf28 > 0) { //if 27 is farther than 28			
			//same as before
			seqRear.next = flagB;
	
			flagA.next = head;
			
			head = nodeAfterFlagA;
			
			nodeBeforeFlagB.next = nodeAfterFlagA;			

			seqRear = nodeBeforeFlagB;
			
		}

	}
	
	/**
	 * Implements Step 4 - Count Shift - on the sequence.
	 */
	void countShift() {		
	    // COMPLETE THIS METHOD
		SeqNode head = seqRear.next;
		SeqNode nodeAfterChainToBeShifted = null;
		SeqNode nodeAtEndOfChainToBeShifted = null;
		SeqNode nodeBeforeFinalNode = null;
		SeqNode finalNode = null;
		
		//Find the last number in the sequence, aka number of nodes that will be moved
		int numbersToBeMoved = 0;
		SeqNode ptr = seqRear;

		numbersToBeMoved = ptr.seqValue;
		
		//Traverse the specific number of nodes to be moved
		ptr = head;
		int counter = 0;
		
		//case where numbersToBeMoved is 28
		if(numbersToBeMoved == 28 || numbersToBeMoved == 27) {
			return;
		}
		
		//case where numbersToBeMoved is 1
		if (numbersToBeMoved == 1) {
			nodeAtEndOfChainToBeShifted = ptr;	
			
		} else {
			
			do{
				counter++;
				ptr = ptr.next;
			
			} while (counter != numbersToBeMoved-1); 
			
			nodeAtEndOfChainToBeShifted = ptr;
		}
		
		//Ptr is at the final node in chain to be shifted
		//Store value of ptr.next
		nodeAfterChainToBeShifted = ptr.next;
		
		//get to final node, point ptr.next to final node, keep reference to final node
		while (ptr.next != head) {
			ptr = ptr.next;
		}
		//ptr points to final node in list
		finalNode = ptr;
		
		//get to node before final node
		ptr = head;
		while (ptr.next.seqValue != finalNode.seqValue) {
			ptr = ptr.next;
		}
		//ptr points to node before final, set nodeBeforeFinalNode to ptr
		nodeBeforeFinalNode = ptr;
		
		//set nodeAtEndOfChainToBeShifted.next to final node
		nodeAtEndOfChainToBeShifted.next = finalNode;
		//set nodeBeforeFinalNode.next to head
		nodeBeforeFinalNode.next = head;
		//set head to nodeAfterChainToBeShifted
		head = nodeAfterChainToBeShifted;
		//set final node.next to head to make a cll
		finalNode.next = head;
		seqRear = finalNode;
		
	}
	
	/**
	 * Gets a key. Calls the four steps - Flag A, Flag B, Triple Shift, Count Shift, then
	 * counts down based on the value of the first number and extracts the next number 
	 * as key. But if that value is 27 or 28, repeats the whole process (Flag A through Count Shift)
	 * on the latest (current) sequence, until a value less than or equal to 26 is found, 
	 * which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
	    // COMPLETE THIS METHOD
		int firstNumber = 0;
		int key = 0;
		SeqNode ptr = null;
		SeqNode head = null;
		
//		printList(seqRear);
//		flagA();
//		printList(seqRear);
//		flagB();
//		printList(seqRear);
//		tripleShift();
//		printList(seqRear);
//		countShift();
//		printList(seqRear);
		
//		System.out.println("Before Method calls:");
//		printList(seqRear);
		
		do {
			flagA();
			flagB();
			tripleShift();
			countShift();				
			
//			System.out.println("After Method calls:");
//			printList(seqRear);
			
			ptr = seqRear;
			head = seqRear.next;
		
			//get value of firstNumber
			firstNumber = head.seqValue;
		
			if(firstNumber == 28) {
				firstNumber = 27;
			}
		
			//traverse the list 'firstNumber' times
			int counter = 0;
			while(counter < firstNumber) {
				ptr = ptr.next;
				counter++;
			}
			//ptr points to the 'firstNumber'th node, ptr.next points to key
			key = ptr.next.seqValue;
//			System.out.println("Key at end of loop: " + key);
		} while (key >= 27);
		
//		System.out.println("After code:");
//		printList(seqRear);

	    return key;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(SeqNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.seqValue);
		SeqNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("->" + ptr.seqValue);
		} while (ptr != rear);
		System.out.println("\n");
	}
	
	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
	    // COMPLETE THIS METHOD
		
//		Message:     D   U    D   E   W   H    E    R   E    S    M    Y    C    A   R
//
//		Alphabet:    4   21   4   5   23  8    5    18  5    19   13   25   3    1   18
//		Position
//																					
//		Keystream:   7   16   5   8   8   15   26   9   14   23   12   15   25   3   1        each key is gained from txt file (seqRear) when method is called (keys in flagATest1.txt)
//		--------------------------------------------------------------------------------
//		Encryption:  11   11   9   13  5   23   5    1   19   16   25   14   2    4   19
		
//		encryptedStringToBeReturned:   KKIMEWEASPYNBDS
		
		//Node: call generate key for each char in message
		
//		int key1 = getKey();
//		int key2 = getKey();
//		int key3 = getKey();
//		System.out.println(key1 + " " + key2 + " " + key3);
		
		String alphabet = "$ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //To get alpabet position, use alphabet.indexOf('char')
		String encryptedStringToBeReturned = "";
		
		int alphabetPosition = 0;
		int keyStream = 0;
		int encryptedNumber = 0;
		
		SeqNode ptr = null;
		SeqNode encryptionRear = null; //remember to set .next to itself
		SeqNode encryptionHead = new SeqNode();
		encryptionHead.seqValue = 100;
		encryptionHead.next = null;
		
		
		//traverse each char in message, get it's alphabet position, add this position to corresponding key, increment char and key, store result in encryption cll
		for (char letter: message.toCharArray()) {
			if(Character.isLetter(letter)) {

				alphabetPosition = alphabet.indexOf(letter);
//				keyStream = ptr.seqValue;
				keyStream = getKey();
				encryptedNumber = alphabetPosition + keyStream;
				if(encryptedNumber > 26) {
					encryptedNumber = encryptedNumber - 26;
				}
					
				if(encryptionHead.seqValue == 100) {
					encryptionHead.seqValue = encryptedNumber;
					encryptionHead.next = encryptionHead;
					continue;
				}
			
				SeqNode node = new SeqNode();
				node.seqValue = encryptedNumber;
				if(encryptionHead.next == encryptionHead) {
					encryptionHead.next = node;
					node.next = encryptionHead;
					encryptionRear = node;
					continue;
				}
				encryptionRear.next = node;
				node.next = encryptionHead;
				encryptionRear = node;
								
//				System.out.println("Alphabet: "+alphabetPosition);
//				System.out.println("KeyStream: "+keyStream);
//				System.out.println("Encrypted number: "+encryptedNumber);
//				System.out.println("Node contains: "+encryptionRear.seqValue);
//				System.out.println();

			}
			
		}
		//encryption cll should be properly filled, with a rear pointing to 19
		ptr = encryptionRear;
		do {
			ptr = ptr.next;
			encryptedStringToBeReturned += alphabet.charAt(ptr.seqValue);
		} while(ptr != encryptionRear);
		
		//loop through encryption cll and add corresponding letters to encryptedStringToBeReturned
		
		
		
		
		//Testing-----
//		System.out.println("Encryption: ");
//		printList(encryptionRear);
//		System.out.println("encryptionRear value: ");
//		System.out.println(encryptionRear.seqValue);
//		System.out.println("encryptionHead value: ");
//		System.out.println(encryptionHead.seqValue);
		
		//Testing-----
		
		
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return encryptedStringToBeReturned;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
	    // COMPLETE THIS METHOD
//		Message:    K	 K	 I	  M	  E	  W	   E   A    S	P	 Y	  N	   B   D 	S 
//		
//		Code:       11   11   9   13   5   23   5   1   19   16   25   14    2   4   19   alphabetPosition of each character in message
//
//		Keystream:   7   16   5    8   8   15  26   9   14   23   12   15   25   3    1	 keys generated by getKey()
//		           --------------------------------------------------------------------
//		Message:     4   21   4    5   23   8   5  18    5   19   13   25    3   1   18	 code - keystream (if message is a negative number, add 26 to message)
//
//		             D   U    D    E   W    H   E   R    E   S    M    Y     C   A    R   alphabet.charAt(Message)
		
			
		String alphabet = "$ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //To get letter, use alphabet.charAt('integer')
		String decryptedMessage = "";
		
		int code = 0;
		int keyStream = 0;
		int messageCode = 0;
		
		
		for (char letter: message.toCharArray()) {
			code = alphabet.indexOf(letter);
			keyStream = getKey();
			messageCode = code - keyStream;
			if (messageCode <= 0) {
				messageCode = messageCode + 26;
			}
			decryptedMessage += alphabet.charAt(messageCode);
		}
		
		
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return decryptedMessage;
	}
}
