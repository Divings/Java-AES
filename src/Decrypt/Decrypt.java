import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Decrypt {
    private static final int KEY_SIZE = 32; // 256-bit key size
    private static final int BLOCK_SIZE = 16; // AES block size (128-bit)

    public static byte[] padKey(byte[] key, byte[] padding) {
        byte[] fullKey = new byte[KEY_SIZE];
        System.arraycopy(key, 0, fullKey, 0, key.length);
        System.arraycopy(padding, 0, fullKey, key.length, padding.length);
        return fullKey;
    }

    public static void decryptFile(String inputFile, byte[] key) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byte[] iv = new byte[BLOCK_SIZE];
            fis.read(iv); // Read IV from file
            
            int paddingSize = KEY_SIZE - key.length;
            byte[] padding = new byte[paddingSize];
            fis.read(padding); // Read key padding from file

            byte[] fullKey = padKey(key, padding);
            SecretKeySpec secretKey = new SecretKeySpec(fullKey, "AES");
            IvParameterSpec ivParams = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
            
            byte[] encryptedData = new byte[(int) (new File(inputFile).length() - BLOCK_SIZE - paddingSize)];
            fis.read(encryptedData);

            byte[] decryptedData = cipher.doFinal(encryptedData);

            String outputFile = inputFile.replace(".enc", "");
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(decryptedData);
            }

            System.out.println("Decryption completed. Decrypted file saved as: " + outputFile);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the decryption key: ");
        byte[] key = scanner.nextLine().getBytes();
        System.out.print("Enter the file path to decrypt: ");
        String inputFile = scanner.nextLine();
        decryptFile(inputFile, key);
        scanner.close();
    }
}
