import java.util.Scanner;

public class PrimeFactors{
    public static void main(String[] args){
        boolean exit = false;
        while (!exit){
            long number = getInput();
            if (number == 0){
                exit = true;
            }
            String output = "";
            while (number > 1){
                long smallestPrime = findSmallestPrime(number);
                number = number / smallestPrime;
                output += smallestPrime;
                if (number != 1){
                    output += ", ";
                }
            }
            if (!exit){
                System.out.println("List of prime factors: " + output);
            } 
        }    
    }

    public static long findSmallestPrime(long number){
        long i = 2;
        boolean firstRun = true;

        while(i <= Math.sqrt(number)){
            if(number % i == 0 && isPrime(i)){
                return i;
            }
            i += 2;
            if (firstRun){
                i--;
                firstRun = false;
            }
        }
        return number;
    }

    public static boolean isPrime(long number){
        long i = 2;
        boolean firstRun = true;
        while (i <= number / 2){
            if (number % i == 0){
                return false;
            }
            i += 2;
            if (firstRun){
                i--;
                firstRun = false;
            }
        }
        return true;
    }

    public static long getInput() {
    	Scanner input = new Scanner(System.in);
		long num = 0; 
		System.out.print("Enter integer greater than 1 (0 to terminate): ");
		while(true){
			while (!input.hasNextLong()){
				input.next();
				System.out.println("Wrong input, try again");
			}
			num = input.nextLong();
			if (num > 1 || num == 0) {
				return num;
			} else {
				System.out.println("Wrong input, try again");
			}
		}
    }
}
