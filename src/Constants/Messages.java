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
        // Regular info messages
        messages.put("inf.flg.typ.hlp", "There will be HELP instruction.");

        // Exceptions & Errors messages
        messages.put("err.flg.typ.hlp", ": Incorrect flags. Help flag (-h, --help) need to be the only one in set.");
        messages.put("err.flg.typ.fst", ": Incorrect syntax of first flag. E.g. -t -e -g");
        messages.put("err.flg.typ.reg", ": Incorrect flag syntax.\nPossible flags are -t, -e, -d, -g, -k, -o, -h");
        messages.put("err.flg.typ.mod", ": Incorrect number of flags.\nAll of the flags need to have pair.");
        messages.put("err.flg.typ.nex", ": Incorrect syntax of flag value.\nPossible values are cipher type or path to file.");
        messages.put("err.flg.typ.typ", ": Incorrect cipher. Provided cipher doesn't exist.");

    }
}
