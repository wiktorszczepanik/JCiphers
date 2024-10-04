package Ciphers.Unsimetric;

import Ciphers.AsymmetricCipher;
import Ciphers.BaseCipher;
import Ciphers.UtilCipher;
import Constants.Flags.ActionTypes;
import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.FileException;
import Exceptions.GenerateException;
import Structures.FlagTuple;

import javax.crypto.Cipher;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

public class Rsa extends UtilCipher implements BaseCipher, AsymmetricCipher {

    private final String pemPubBegin = "-----BEGIN PUBLIC KEY-----\n";
    private final String pemPubEnd = "\n-----END PUBLIC KEY-----";
    private final String pemPrvBegin = "-----BEGIN PRIVATE KEY-----\n";
    private final String pemPrvEnd = "\n-----END PRIVATE KEY-----";

    public Rsa(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    private boolean isPublicKey(String filePath) throws FileException {
        String content;
        try { content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception exception) {
            throw new FileException(messages.get("err.red.key.ioe"));
        }
        if (content.contains(pemPubBegin) && content.contains(pemPubEnd))
            return true;
        else if (content.contains(pemPrvBegin) && content.contains(pemPrvEnd))
            return false;
        else throw new FileException("err.red.key.chc");
    }

    private byte[] readKeyBytes(String filePath) throws FileException{
        String key;
        try {
            key = new String(Files.readAllBytes(Paths.get(filePath)));
            key = key.replaceAll("-----BEGIN (.*)-----", "");
            key = key.replaceAll("-----END (.*)-----", "");
            key = key.replaceAll("\\s", "");
        } catch (IOException exception) {
            throw new FileException(messages.get("err.red.key.ioe"));
        } catch (Exception exception) {
            throw new FileException(messages.get("err.red.key.chc"));
        }
        return Base64.getDecoder().decode(key);
    }

    private static PublicKey loadPublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec specification = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(specification);
    }

    private static PrivateKey loadPrivateKey(byte[] keyBytes) throws Exception {
        PKCS8EncodedKeySpec specification = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(specification);
    }

    @Override
    public void encrypt() throws EncryptionException, FileException {
        String keyFile = valueSelector(ActionTypes.KEY);
        byte[] keyBytesMain = readKeyBytes(keyFile);
        Key key;
        try {
            if (isPublicKey(keyFile)) key = loadPublicKey(keyBytesMain);
            else key = loadPrivateKey(keyBytesMain);
        } catch (Exception exception) {
            throw new FileException(messages.get("err.red.key.spc"));
        }
        StringBuilder fileText = readFileContent(ActionTypes.ENCRYPT);
        String text = fileText.toString();
        String encodedText;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            encodedText = Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception exception) {
            throw new EncryptionException(messages.get("err.red.rsa.run"));
        }
        print(encodedText);
    }

    @Override
    public void decrypt() throws DecryptionException, FileException {
        String keyFile = valueSelector(ActionTypes.KEY);
        byte[] keyBytesMain = readKeyBytes(keyFile);
        Key key;
        try {
            if (isPublicKey(keyFile)) key = loadPublicKey(keyBytesMain);
            else key = loadPrivateKey(keyBytesMain);
        } catch (Exception exception) {
            throw new FileException(messages.get("err.red.key.spc"));
        }
        String encodedText = readFileContent(ActionTypes.DECRYPT).toString();
        byte[] decryptedBytes;

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedBytes = Base64.getDecoder().decode(encodedText);
            decryptedBytes = cipher.doFinal(encryptedBytes);
        } catch (Exception exception) {
             throw new DecryptionException(messages.get("err.red.rsa.run"));
        }
        String decryptedText = new String(decryptedBytes);
        print(decryptedText);
    }

    @Override
    public void generate() throws GenerateException, FileException {
        int keySize;
        try {
            String textSize = valueSelector(ActionTypes.GENERATE);
            keySize = Integer.parseInt(textSize);
        } catch (Exception exception) {
            throw new GenerateException(messages.get("err.flg.gen.out"));
        }
        String publicKeyBase64, privateKeyBase64;
        try {
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(keySize);
            KeyPair keyPair = keyGenerator.generateKeyPair();

            // Implementation with PEM standard.
            publicKeyBase64 = this.pemPubBegin +
                    Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()) +
                    this.pemPubEnd;
            privateKeyBase64 = this.pemPrvBegin +
                    Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()) +
                    this.pemPrvEnd;
        } catch (NoSuchAlgorithmException exception) {
            throw new GenerateException(messages.get("err.gen.des.key"));
        }
        if (flagTupleSelector(ActionTypes.OUTPUT) != -1) {
            String fileName = valueSelector(ActionTypes.OUTPUT);
            String publicExtension = ".public", privateExtension = ".private";
            String pubKeyFileName = fileName.concat(publicExtension);
            String prvKeyFileName = fileName.concat(privateExtension);
            try { // Write public key and private key
                FileWriter publicKeyFileWriter = new FileWriter(pubKeyFileName);
                publicKeyFileWriter.write(publicKeyBase64);
                publicKeyFileWriter.close();

                FileWriter privateKeyFileWriter = new FileWriter(prvKeyFileName);
                privateKeyFileWriter.write(privateKeyBase64);
                privateKeyFileWriter.close();
            } catch (IOException exception) {
                throw new GenerateException("err.wrt.reg.key");
            }
        } else {
            System.out.println(publicKeyBase64);
            System.out.println(privateKeyBase64);
        }
    }
}
