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
        messages.put("err.flg.num.sml", ": Incorrect number of flags. Minimum possible number of flags is 2.");
        messages.put("err.flg.num.big", ": Incorrect number of flags. Maximum possible number of flags is 4.");
        messages.put("err.flg.typ.bas", ": Incorrect flags. Use (-h, --help) to check possible options.");
        messages.put("err.flg.typ.crp", ": Incorrect flags. One action is possible per program run: -e -d -g");
        messages.put("err.flg.typ.hlp", ": Incorrect flags. Help flag (-h, --help) need to be the only one in set.");
        messages.put("err.flg.typ.fst", ": Incorrect syntax of first flag. E.g. -t -e -g");
        messages.put("err.flg.typ.reg", ": Incorrect flag syntax.Possible flags are -t, -e, -d, -g, -k, -o, -h");
        messages.put("err.flg.typ.act", ": Incorrect flags. Possible flags are -e, -d, -g");
        messages.put("err.flg.typ.mod", ": Incorrect number of flags. All of the flags need to have pair except -g");
        messages.put("err.flg.typ.gek", ": Incorrect flag. We cannot use --key with --generate flag.");
        messages.put("err.flg.typ.nex", ": Incorrect syntax of flag value. Possible values are cipher type or path to file.");
        messages.put("err.flg.typ.typ", ": Incorrect cipher. Provided cipher doesn't exist.");
        messages.put("err.flg.typ.tps", ": Cipher type is not provided.");
        messages.put("err.flg.typ.enc", ": Incorrect flags. Should also use --encryption in this flags configuration.");
        messages.put("err.flg.typ.dec", ": Incorrect flags. Should also use --decryption in this flags configuration.");
        messages.put("err.flg.typ.gen", ": Incorrect flags. Should also use --generate in this flags configuration.");
        messages.put("err.flg.typ.key", ": Incorrect flags. Should also use --key in this flags configuration.");
        messages.put("err.flg.typ.out", ": Incorrect flags. Should also use --output in this flags configuration.");

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
