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

/* Example flags */
// "-t", "ROT13",
// "--type", "RSA",
// "-e", "test/ClearText/Rsa.txt",
// "--encrypt", "Tests/Rot13/Rot13.txt",
// "-d", "test/CipherText/Rsa.txt",
// "--decrypt", "test/CipherText/Rsa.txt",
// "-g",
// "--generate",
// "-g", "4000",
// "-k", "test/Keys/Rsa.txt.private",
// "--key", "test/Keys/Rsa.txt.public",
// "-o", "test/CipherText/Rsa.txt"
// "--output", "test/Keys/Rsa.txt",
// "-h",
// "--help",

public class JCipher {

    static Messages messages = Messages.getInstance();

    public static void main(String[] args) {
        String throwValue = "";
        try {
            if (args.length == 0)
                throw new FlagException(messages.get("err.flg.bas.zer"));
            if (args[0].equals("-h") || args[0].equals("--help"))
                if (args.length > 1) throw new FlagException(messages.get("err.flg.typ.hlp"));
                else System.out.println(messages.get("inf.flg.typ.hlp"));
            else { // Entry flags checker
                BasicValidation.flagsNumber(args, messages);

                // Flags collection
                var flags = new FlagCollector(args);
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
        if (!throwValue.isEmpty()) System.err.print(throwValue);
    }
}