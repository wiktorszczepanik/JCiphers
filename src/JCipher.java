import Ciphers.UtilCipher;
import Components.BasicValidation;
import Components.CipherSelector;
import Components.CipherValidation;
import Components.FlagCollector;
import Constants.Flags.ActionTypes;
import Constants.Messages;
import Exceptions.*;
import Structures.FlagTuple;

import java.util.List;

public class JCipher {

    static Messages messages = Messages.getInstance();

    public static void main(String[] args) {
        String[] tempArgs = new String[] {
            "-t", "ROT13",
//            "--type", "ROT13",
            "-e", "./rot13.txt",
//            "--encrypt", "./rot13.txt",
//            "-d", "./rot13.txt",
//            "--decrypt", "./rot13.txt",
//            "-g",
//            "--generate",
//            "-k", ".key.txt",
            "--key", ".key.txt",
//            "-o", "./encRot13.txt"
//            "--output", "./encRot13.txt"
//            "-h",
//            "--help",
        };
        String throwValue = "";
        try {
            if (tempArgs[0].equals("-h") || tempArgs[0].equals("--help"))
                if (tempArgs.length > 1) throw new FlagException(messages.get("err.flg.typ.hlp"));
                else System.out.println(messages.get("inf.flg.typ.hlp"));
            else { // Entry flags checker
                BasicValidation.flagsNumber(tempArgs, messages);

                // Flags collection
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
                UtilCipher cipher = lookForCipher.select(sortedFlags);
                cipher.run();
            }
        } catch (FlagException flagException) {
            throwValue = flagException.getMessage();
        } catch (EncryptionException | DecryptionException | GenerateException cipherException) {
            throwValue = cipherException.getMessage();
        } catch (FileException fileException) {
            throwValue = fileException.getMessage();
        }
        System.err.print(throwValue);
    }
}