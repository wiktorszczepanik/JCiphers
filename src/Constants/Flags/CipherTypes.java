package Constants.Flags;

public enum CipherTypes {

    //-----------|ENCRYPT|----------|DECRYPT|----------|GENERATE|
    ROT13((byte) 0b1_100_00, (byte) 0b1_010_00, (byte) 0b1_001_00),
    CEZAR((byte) 0b1_100_10, (byte) 0b1_010_10, (byte) 0b1_001_00),
    BEALE((byte) 0b1_100_10, (byte) 0b1_010_10, (byte) 0b1_001_00),
    VERNAM((byte) 0b1_100_10, (byte) 0b1_010_10, (byte) 0b1_001_00);


    private final byte encyptionOptions;
    private final byte decryptionOptions;
    private final byte getEncyptionOptions;

    CipherTypes(byte encryptOptions, byte decryptOptions, byte generateOptions) {
        this.encyptionOptions = encryptOptions;
        this.decryptionOptions = decryptOptions;
        this.getEncyptionOptions = generateOptions;
    }

    public byte getEncryptionOptions() {
        return encyptionOptions;
    }

    public byte getDecryptionOptions() {
        return decryptionOptions;
    }

    public byte getGenerateOptions() {
        return getEncyptionOptions;
    }


}
