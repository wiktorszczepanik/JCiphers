package Constants.Flags;

public enum ActionTypes {

    TYPE((byte) 0b1_000_00, "-t", "--type"),
    ENCRYPT((byte) 0b0_100_00, "-e", "--encrypt"),
    DECRYPT((byte) 0b0_010_00, "-d", "--decrypt"),
    GENERATE((byte) 0b0_001_00, "-g", "--generate"),
    KEY((byte) 0b0_000_10, "-k", "--key"),
    OUTPUT((byte) 0b0_000_01, "-o", "--output"),
    HELP((byte) 0b0, "-h", "--help");

    private final byte bitStructure;
    private final String shortFlag;
    private final String longFlag;

    ActionTypes(byte bitSequence, String shortFlag, String longFlag) {
        this.bitStructure = bitSequence;
        this.shortFlag = shortFlag;
        this.longFlag = longFlag;
    }

    public byte getBitStructure() {
        return bitStructure;
    }

    public String getLongFlag() {
        return longFlag;
    }

    public String getShortFlag() {
        return shortFlag;
    }

}
