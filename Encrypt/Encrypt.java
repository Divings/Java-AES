import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class Encrypt {
    private static final int KEY_SIZE = 32; // 256-bit key size
    private static final int BLOCK_SIZE = 16; // AES block size (128-bit)

    public static byte[] padKey(byte[] key) {
        if (key.length >= KEY_SIZE) {
            return Arrays.copyOf(key, KEY_SIZE);
        }
        byte[] paddedKey = new byte[KEY_SIZE];
        System.arraycopy(key, 0, paddedKey, 0, key.length);
        SecureRandom random = new SecureRandom();
        byte[] padding = new byte[KEY_SIZE - key.length];
        random.nextBytes(padding);
        System.arraycopy(padding, 0, paddedKey, key.length, padding.length);
        return paddedKey;
    }

    public static void encryptFile(String inputFile, byte[] key) throws Exception {
        byte[] paddedKey = padKey(key);
        byte[] iv = new byte[BLOCK_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(paddedKey, "AES");
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
        
        FileInputStream fis = new FileInputStream(inputFile);
        byte[] fileData = new byte[(int) new File(inputFile).length()];
        fis.read(fileData);
        fis.close();
        
        byte[] encryptedData = cipher.doFinal(fileData);

        try (FileOutputStream fos = new FileOutputStream(inputFile + ".enc")) {
            fos.write(iv); // Write IV first
            fos.write(Arrays.copyOfRange(paddedKey, key.length, KEY_SIZE)); // Write key padding
            fos.write(encryptedData); // Write encrypted data
        }

        System.out.println("Encryption completed. Encrypted file saved as: " + inputFile + ".enc");
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the encryption key: ");
        byte[] key = scanner.nextLine().getBytes();
        System.out.print("Enter the file path to encrypt: ");
        String inputFile = scanner.nextLine();
        encryptFile(inputFile, key);
        scanner.close();
    }
}
