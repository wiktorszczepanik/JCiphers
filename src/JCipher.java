import Ciphers.BaseCrypt;
import Components.CipherSelector;
import Components.CipherValidation;
import Components.FlagCollector;
import Constants.Flags.ActionTypes;
import Constants.Messages;
import Exceptions.FlagExcpetion;
import Structures.FlagTuple;

import java.io.IOException;
import java.util.List;

public class JCipher {

    static Messages messages = Messages.getInstance();

    public static void main(String[] args) {
        String[] tempArgs = new String[] {
            "-t", "ROT13",
            "-e", "./rot13.txt",
            "-k", ".key.txt",
            "-o", "./encRot13.txt"
        };
        if (tempArgs[0].equals("-h") || tempArgs[0].equals("--help"))
            System.out.println(messages.get("inf.flg.typ.hlp"));
        else {
            try { // Flags collection
                var flags = new FlagCollector(tempArgs);
                flags.collectFlags();
                flags.sortFlags();
                flags.sequenceBits();
                flags.cleanCipherName();

                // Get base values
                List<FlagTuple<ActionTypes, String>> sortedFlags;
                sortedFlags = flags.getFlags();
                byte options = flags.getActionBits();

                // Cipher validation
                CipherValidation qualityCheck;
                qualityCheck = new CipherValidation(
                    sortedFlags.getFirst(), sortedFlags.get(1), options
                );
                qualityCheck.validate();

                // Cipher selection
                CipherSelector lookForCipher;
                lookForCipher = new CipherSelector(
                    sortedFlags.getFirst(), options
                );
                BaseCrypt cipher = lookForCipher.select(sortedFlags);

            } catch (FlagExcpetion exception) {
                exception.getMessage();
            }
        }
    }
}