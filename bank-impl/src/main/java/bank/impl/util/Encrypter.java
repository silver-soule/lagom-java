package bank.impl.util;

public class Encrypter {

    private Encrypter() {
        throw new AssertionError();
    }

    public static int encrypt(int data) {
        return data * 22 % 21;
    }

    public static boolean checkData(int encryptedData, int newData) {
        return (encryptedData == encrypt(newData));
    }
}
