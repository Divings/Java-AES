
# AES File Encryption and Decryption in Java

This project provides Java classes for encrypting and decrypting files using AES (Advanced Encryption Standard) with a 256-bit key. It includes two main components:
- `Encrypt.java`: Encrypts files with AES encryption.
- `Decrypt.java`: Decrypts files that were encrypted with `Encrypt.java`.

## Features

- **AES Encryption**: Uses a 256-bit AES key with CBC (Cipher Block Chaining) mode.
- **Key Padding**: Automatically pads keys shorter than 256 bits with random bytes in `Encrypt.java` and with a provided padding in `Decrypt.java`.
- **File-based Encryption**: Encrypts and decrypts files with added IV (Initialization Vector) support for secure encryption.

## Prerequisites

- **Java Development Kit (JDK)** 8 or above.
- **AES-256 Support**: Ensure the JRE/JDK supports AES-256. For some environments, you may need to install the Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files.

## Usage

### 1. Compile the Java files

```bash
javac Encrypt.java
javac Decrypt.java
```

### 2. Run the Encryption

Use `Encrypt` to encrypt a file by specifying the file path and encryption key.

```bash
java Encrypt
```

### 3. Run the Decryption

Use `Decrypt` to decrypt a file by specifying the encrypted file path and the decryption key.

```bash
java Decrypt 
```

## Code Details

### `Encrypt.java`
- **`padKey(byte[] key)`**: Pads the encryption key to a length of 256 bits (32 bytes) with random bytes if the provided key is shorter.
- **`encryptFile(String inputFile, byte[] key)`**: Encrypts the specified file using AES and saves the result to an output file.

### `Decrypt.java`
- **`padKey(byte[] key, byte[] padding)`**: Reconstructs the original padded key used for encryption by combining the provided key with the padding.
- **`decryptFile(String inputFile, byte[] key)`**: Decrypts the specified encrypted file and saves the original content to an output file.

## Notes
- Both encryption and decryption require that the key used in decryption exactly matches the key (including padding) used in encryption.
- The `Encrypt.java` class saves the IV at the beginning of the encrypted file, which `Decrypt.java` reads to ensure secure decryption.

## License

This project is open-source and available under the [MIT License](LICENSE).
