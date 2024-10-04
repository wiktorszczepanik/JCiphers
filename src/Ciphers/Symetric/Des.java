package Ciphers.Symetric;

import Ciphers.BaseCipher;
import Ciphers.SymmetricCipher;
import Ciphers.UtilCipher;
import Constants.Flags.ActionTypes;
import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.FileException;
import Exceptions.GenerateException;
import Structures.FlagTuple;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class Des extends UtilCipher implements BaseCipher, SymmetricCipher {

    public Des(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    private SecretKey getSecretKey(ActionTypes action) throws FileException {
        String fileName = valueSelector(action);
        SecretKey secretKey;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(fileName));
            String keyAsBase64 = new String(keyBytes);
            byte[] decoded = Base64.getDecoder().decode(keyAsBase64);
            secretKey = new SecretKeySpec(decoded, 0, decoded.length, "DES");
        } catch (IOException exception) {
            throw new FileException(messages.get("err.red.reg.all"));
        }
        return secretKey;
    }

    @Override
    public void encrypt() throws EncryptionException, FileException {
        SecretKey secretKey = getSecretKey(ActionTypes.KEY);
        StringBuilder file = readFileContent(ActionTypes.ENCRYPT);
        String encryptedText;
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(file.toString().getBytes());
            encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException exception) {
            throw new EncryptionException(messages.get("err.red.des.alg"));
        } catch (InvalidKeyException exception) {
            throw new EncryptionException(messages.get("err.flg.enc.key"));
        } catch (IllegalBlockSizeException | BadPaddingException exception) {
            throw new EncryptionException(messages.get("err.red.des.run"));
        }
        print(encryptedText);
    }

    @Override
    public void decrypt() throws DecryptionException, FileException {
        SecretKey secretKey = getSecretKey(ActionTypes.KEY);
        StringBuilder file = readFileContent(ActionTypes.DECRYPT);
        String decryptedText;
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(file.toString()));
            decryptedText = new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException exception) {
            throw new DecryptionException(messages.get("err.red.des.alg"));
        } catch (InvalidKeyException exception) {
            throw new DecryptionException(messages.get("err.flg.dec.key"));
        } catch (IllegalBlockSizeException | BadPaddingException exception) {
            throw new DecryptionException(messages.get("err.red.des.run"));
        }
        print(decryptedText);
    }

    @Override
    public void generate() throws GenerateException, FileException {
        String keyToBase64;
        try {
            SecretKey secretKey;
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            keyToBase64 = Base64.getEncoder().encodeToString(keyBytes);
        } catch (Exception exception) {
            throw new GenerateException(messages.get("err.gen.des.key"));
        }
        if (flagTupleSelector(ActionTypes.OUTPUT) != -1) {
            String keyFileName = valueSelector(ActionTypes.OUTPUT);
            try (FileOutputStream fos = new FileOutputStream(keyFileName)) {
                fos.write(keyToBase64.getBytes());
            } catch (IOException ioe) {
                throw new GenerateException(messages.get("err.gen.des.out"));
            }
        } else System.out.println(keyToBase64);
    }
}
