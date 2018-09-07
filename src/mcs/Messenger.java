package mcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Messenger {
	public static void main(String[] args) 
	throws IOException {
		
		MiniCipherSys mcs = new MiniCipherSys();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter sequence file name => ");
		
		Scanner sc = new Scanner(new File(br.readLine()));
		mcs.makeSeq(sc);
		
		System.out.print("Encrypt or decrypt? (e/d), press return to quit => ");
		String inp = br.readLine();
		if (inp.length() == 0) {
			System.exit(0);
		}
		char ed = inp.charAt(0);
		System.out.print("Enter message => ");
		String message = br.readLine();
		if (ed == 'e') {
			System.out.println("Encrypted message: " + mcs.encrypt(message));
		} else {
			System.out.println("Decrypted message: " + mcs.decrypt(message));
		}
		
//		//testing----
//		SeqNode ptr = mcs.seqRear;
//		System.out.println("Original: ");
//		do {
//			System.out.print(ptr.seqValue + " -> ");
//			ptr = ptr.next;
//			
//		} while(ptr != mcs.seqRear);
//		System.out.println();
//		
////		System.out.print("FlagA"); mcs.flagA();
////		System.out.println("SeqRear: "+mcs.seqRear.seqValue);
//		
////		System.out.print("FlagB"); mcs.flagB();				
//		System.out.print("Count Shift"); mcs.countShift();
//		
////		System.out.print("Triple Shift"); mcs.tripleShift();
////		System.out.println("seqRear: " + mcs.seqRear.seqValue);
//		
//		System.out.println();
		
		
		//mcs.encrypt(message);
//		System.out.println("Key: "+ mcs.getKey());
		
//		ptr = mcs.seqRear;
//		do {
//			System.out.print(ptr.seqValue + " -> ");
//			ptr = ptr.next;
//			
//		} while(ptr != mcs.seqRear);
////		System.out.print(ptr.seqValue + " lll ");
////		System.out.print(ptr.next.seqValue + " lll ");
//		//testing----
	}
}
