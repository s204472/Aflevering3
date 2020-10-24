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

    /*
     * This method finds the smallest prime factor of a number.
     * The smallest prime factor is returned
     * */
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

	/*
	 * This method checks whether a number is a primenumber or not.
	 * True is returned if prime.
	 * */
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

    /* 
     * This method prompts the user for an input. 
     * The method handles wrong inputs, by using the try-catch-block and if-statements.
     * */
    public static long getInput() {
        Scanner input = new Scanner(System.in);
        long num = 0;
        try {
            System.out.print("Enter integer greater than 1 (0 to terminate): ");
            num = input.nextLong();
            if (num > 1 || num == 0) {
                return num;
            } else {
                System.out.println("Wrong input, try again");
                return getInput();
            }
        } catch (Exception e) {
            System.out.println("Wrong input, try again");
            return getInput();
        }
    }
}
