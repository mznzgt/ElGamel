import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ElGamal {
    static BigInteger p, a, x, randomNumber, originalMessage, publickey;
    static BigInteger C1, C2;

    public static BigInteger decryptWithPrivateKey(BigInteger C1, BigInteger C2, BigInteger p, BigInteger privateKey) {

        BigInteger result = C2.multiply(C1.modPow(privateKey.negate(), p)).mod(p);
        return result;
    }

    public static void encryptWithRandomNumber(BigInteger message, BigInteger y, BigInteger a,
                                               BigInteger p, BigInteger randomNumber) {

        C1 = a.modPow(randomNumber, p);
        C2 = message.multiply(y.modPow(randomNumber, p)).mod(p);

    }

    public static BigInteger decryptWithoutPrivateKey(BigInteger C1, BigInteger C2, BigInteger p, BigInteger a, BigInteger publickey) {
        BigInteger root = a;
        BigInteger k = new BigInteger( "-1" );
        BigInteger tempK;
        BigInteger result;
        // looking for random key
        for ( BigInteger i = BigInteger.valueOf(1); i.compareTo(p) < 0; i=i.add(BigInteger.ONE)){
            tempK = a.modPow(i,p);
            if(tempK.equals(C1)){
                k=i;
                System.out.println("Random key is ：" + k);
            }
        }

        result = C2.multiply(publickey.modPow(k, p).modInverse(p)).mod(p);
        return result;
    }



    public static void main(String[] args) {

        System.out.println("e for Encryption , d1 for Private key Decryption, d2 for non-Private key Decryption");

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


        }
    }
}
