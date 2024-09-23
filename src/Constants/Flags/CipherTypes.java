package Constants.Flags;

public enum CipherTypes {

    //-----------|ENCRYPT|----------|DECRYPT|----------|GENERATE|
    ROT13((byte) 0b1_100_10, (byte) 0b1_010_10, (byte) 0b1_001_00);

    private final byte encyptionOptions;
    private final byte decryptionOptions;
    private final byte getEncyptionOptions;

    CipherTypes(byte encryptOptions, byte decryptOptions, byte generateOptions) {
        this.encyptionOptions = encryptOptions;
        this.decryptionOptions = decryptOptions;
        this.getEncyptionOptions = generateOptions;
    }

    public byte getEncyptionOptions() {
        return encyptionOptions;
    }

    public byte getDecryptionOptions() {
        return decryptionOptions;
    }

    public byte getGetEncyptionOptions() {
        return getEncyptionOptions;
    }


}
