import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ElGamal {
   static BigInteger p, a, x, randomNumber, originalMessage, publickey, privateKey;
   static BigInteger C1, C2;

   public static BigInteger decryptWithPrivateKey(BigInteger C1, BigInteger C2, BigInteger p, BigInteger privateKey) {

       /*
            To decrypt with private key, get C1 (which equal to a^k) to the power of private key mod p, the inverse mod p
           result times C2 (which equal to km) mod p will gives the original message
         */

       BigInteger result = C2.multiply(C1.modPow(privateKey.negate(), p)).mod(p);
       return result;
   }

   public static void encryptWithRandomNumber(BigInteger message, BigInteger y, BigInteger a,
                                              BigInteger p, BigInteger randomNumber) {



       C1 = a.modPow(randomNumber, p);
       C2 = message.multiply(y.modPow(randomNumber, p)).mod(p);

   }

   public static void encryBigPrime(int number){
       Random random = new Random();
       BigInteger randomPrime = new BigInteger( "-1" );
       while (true) {
           randomPrime = BigInteger.probablePrime(number, random);

           if (randomPrime.bitLength()!= number)
               continue;

           if (randomPrime.isProbablePrime(10))
           {
               p=randomPrime;

               if (p.isProbablePrime(10))
                   break;
           }
       }


       while (true) {
           a = BigInteger.probablePrime(p.bitLength() - 1, random);

           if(a.modPow(p.subtract(BigInteger.ONE),p).equals(BigInteger.ONE) && a.modPow(p.subtract(BigInteger.ONE).divide(new BigInteger("2")),p).equals(p.subtract(BigInteger.ONE))){
               break;
           }
       }

       // randomly get choose a private key within p's range
       int pRange = p.intValue();
       privateKey = new BigInteger(p.bitLength() - 1,random);

       // calculate public key
       publickey = a.modPow(privateKey,p);

       BigInteger randomNumber;
       BigInteger message = BigInteger.valueOf(number);
       randomNumber = new BigInteger (number - 1,random);
       C1 = a.modPow(randomNumber, p);
       C2 = message.multiply(publickey.modPow(randomNumber, p)).mod(p);

       System.out.println("Random p is: " + p);
       System.out.println("Random Primitive Root is: " + a);
       System.out.println("Random number is: " + randomNumber);
       System.out.println("Public key is: " + publickey);
       System.out.println("Private key is: " + privateKey);
       System.out.println("C1 is: " + C1);
       System.out.println("C2 is: " + C2);

   }

   public static BigInteger decryptWithoutPrivateKey(BigInteger C1, BigInteger C2, BigInteger p, BigInteger a, BigInteger publickey) {
       BigInteger root = a;
       BigInteger k = new BigInteger( "-1" );
       BigInteger tempK;
       BigInteger result;
        // looking for random key within range of p by lopping a to the power of p to find the matches reminder
       for ( BigInteger i = BigInteger.valueOf(1); i.compareTo(p) < 0; i=i.add(BigInteger.ONE)){
           tempK = a.modPow(i,p);
           if(tempK.equals(C1)){
               k=i;
               System.out.println("Random key is ：" + k);
           }
       }

        // calculate private key by keep looping a to the power of 1 until matches power which the reminder is equal to the public key
       BigInteger privateKey = new BigInteger( "1" );
       while(true){

           if(a.modPow(privateKey,p).equals(publickey)){
               break;
           }

           privateKey = privateKey.add(BigInteger.ONE);
       }

       System.out.println("Private key is ：" + privateKey);
       // Public key multiple random number k mod 17 and inverse modulo the result, then multiple with C2 mod p to get the message
       result = C2.multiply(publickey.modPow(k, p).modInverse(p)).mod(p);
       return result;
   }







   public static void main(String[] args) {

       System.out.println("e for Encryption , bige for Encryption with huge prime, d1 for Private key Decryption, d2 for non-Private key Decryption");

       while (true) {
           Scanner input = new Scanner(System.in);
           String str = input.nextLine();
           if(str.equals("d1")){
               System.out.println("Enter C1");
               str = input.nextLine();
               C1=new BigInteger(str);
               System.out.println("Enter C2");
               str = input.nextLine();
               C2=new BigInteger(str);
               System.out.println("Enter Private Key");
               str = input.nextLine();
               x=new BigInteger(str);
               System.out.println("Enter p");
               str = input.nextLine();
               p=new BigInteger(str);
               BigInteger result = ElGamal.decryptWithPrivateKey(C1, C2, p, x);
               System.out.println("Message is: " + result);
           }

           else if(str.equals("e")){
               System.out.println("Enter original message");
               str = input.nextLine();
               originalMessage=new BigInteger(str);
               System.out.println("Enter public key");
               str = input.nextLine();
               publickey=new BigInteger(str);
               System.out.println("Enter primitive root a");
               str = input.nextLine();
               a=new BigInteger(str);
               System.out.println("Enter mod p");
               str = input.nextLine();
               p=new BigInteger(str);
               System.out.println("Enter random number");
               str = input.nextLine();
               randomNumber=new BigInteger(str);
               ElGamal.encryptWithRandomNumber(originalMessage,publickey,a,p,randomNumber);
               System.out.println("Encrypted message" + "(" + C1 + "," +C2+ ")");
           }

           else if(str.equals("d2")){
               System.out.println("Enter C1");
               str = input.nextLine();
               C1=new BigInteger(str);
               System.out.println("Enter C2");
               str = input.nextLine();
               C2=new BigInteger(str);
               System.out.println("Enter mod p");
               str = input.nextLine();
               p=new BigInteger(str);
               System.out.println("Enter primitive root a");
               str = input.nextLine();
               a=new BigInteger(str);
               System.out.println("Enter public key");
               str = input.nextLine();
               publickey=new BigInteger(str);
               BigInteger result2 = ElGamal.decryptWithoutPrivateKey(C1,C2,p,a,publickey);
               System.out.println("Message is: " + result2);
           }

           else if(str.equals("bige")){
               System.out.println("Enter any message for Encryption");
               str = input.nextLine();
               int number = Integer.parseInt(str);
               encryBigPrime(number);
           }
       }
   }
}

