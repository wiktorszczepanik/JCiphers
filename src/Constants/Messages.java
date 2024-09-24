package Constants;

import java.util.HashMap;

public class Messages {
    private static Messages instance;
    private final HashMap<String, String> messages;

    private Messages() {
        this.messages = new HashMap<>();
        setMessages();
    }

    public static Messages getInstance() {
        if (instance == null) instance = new Messages();
        return instance;
    }

    public String get(String key) {
        return messages.get(key);
    }

    private void setMessages() {
        // Regular info
        messages.put("inf.flg.typ.hlp", "There will be HELP instruction.");

        // Flag exceptions & errors
        messages.put("err.flg.typ.bas", ": Incorrect flags. Use (-h, --help) to check possible options.");
        messages.put("err.flg.typ.hlp", ": Incorrect flags. Help flag (-h, --help) need to be the only one in set.");
        messages.put("err.flg.typ.fst", ": Incorrect syntax of first flag. E.g. -t -e -g");
        messages.put("err.flg.typ.reg", ": Incorrect flag syntax.\nPossible flags are -t, -e, -d, -g, -k, -o, -h");
        messages.put("err.flg.typ.act", ": Incorrect flags.\nPossible flags are -e, -d, -g");
        messages.put("err.flg.typ.mod", ": Incorrect number of flags.\nAll of the flags need to have pair except -g");
        messages.put("err.flg.typ.nex", ": Incorrect syntax of flag value.\nPossible values are cipher type or path to file.");
        messages.put("err.flg.typ.typ", ": Incorrect cipher. Provided cipher doesn't exist.");
        messages.put("err.flg.typ.tps", ": Cipher type is not provided.");

        // Reading files exceptions
        messages.put("err.red.reg.enc", ": Problem with reading file with encryption text.");
        messages.put("err.red.nul.enc", ": Provided encryption file is empty.");
        messages.put("err.red.reg.dec", ": Problem with reading file with decryption text.");
        messages.put("err.red.nul.dec", ": Provided decryption file is empty.");
        messages.put("err.red.reg.key", ": Problem with reading file with key.");
        messages.put("err.red.nul.key", ": Provided key file is empty.");
        messages.put("err.red.reg.all", ": Problem with reading file.");
        messages.put("err.red.nul.all", ": Provided file is empty.");

        // Writing files exeptions
        messages.put("err.wrt.reg.enc", ": Problem with writing encrypted data to provided file.");
        messages.put("err.wrt.reg.dec", ": Problem with writing decrypted data to provided file.");
        messages.put("err.wrt.reg.key", ": Problem with writing key to provided file.");
        messages.put("err.wrt.reg.all", ": Provided file is empty.");

    }
}
