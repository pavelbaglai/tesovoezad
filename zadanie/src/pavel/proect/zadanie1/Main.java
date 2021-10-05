package pavel.proect.zadanie1;

import java.io.IOException;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		ConvertNumbersSlovo converter = new ConvertNumbersSlovo();
		Scanner in = new Scanner(System.in);
		try {
			while(true) {
				System.out.println("¬ведите число :");
				
				BigInteger number = in.nextBigInteger();
				System.out.println(converter.converter(number));
			}
		}catch(InputMismatchException | NullPointerException e) {
			e.printStackTrace();
		}
	}
}
