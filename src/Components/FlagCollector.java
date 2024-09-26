package Components;

import Constants.Flags.ActionTypes;
import Constants.Messages;
import Exceptions.FlagException;
import Structures.FlagsOrder;
import Structures.FlagTuple;

import java.util.ArrayList;
import java.util.List;

public class FlagCollector {

     private String[] args;
     private List<FlagTuple<ActionTypes, String>> flags = new ArrayList<>();
     private byte actionBits = 0b000000;
     Messages messages = Messages.getInstance();

     public FlagCollector(String[] args) {
         this.args = args;
     }

     public void collectFlags() throws FlagException {
         if (args[0].charAt(0) != '-')
             throw new FlagException(args[0].concat(messages.get("err.flg.typ.fst")));
         int counter = 0;
         ActionTypes flag;
         String value;
         checkForHelp(args);
         int flagsLength = countFlags(args);
         if (flagsLength % 2 != 0)
             throw new FlagException(messages.get("err.flg.typ.mod"));
         while (counter < args.length) {
             switch (args[counter].trim().toLowerCase()) {
                 case "-t", "--type" -> flag = ActionTypes.TYPE;
                 case "-e", "--encrypt" -> flag = ActionTypes.ENCRYPT;
                 case "-d", "--decrypt" -> flag = ActionTypes.DECRYPT;
                 case "-g", "--generate" -> {
                     flag = ActionTypes.GENERATE;
                     flags.add(new FlagTuple<>(flag, null));
                     counter++;
                     continue;
                 } case "-k", "--key" -> flag = ActionTypes.KEY;
                 case "-o", "--output" -> flag = ActionTypes.OUTPUT;
                 case "-h", "--help" -> throw new FlagException(messages.get("err.flg.typ.hlp"));
                 default -> throw new FlagException(
                     args[counter] + " " + messages.get("err.flg.typ.reg"));
             }
             value = args[counter + 1];
             flags.add(new FlagTuple<>(flag, value));
             counter+=2;
         }
     }

     private void checkForHelp(String[] args) throws FlagException {
         for (String flag : args)
             if (flag.equals("--help") || flag.equals("-h"))
                 throw new FlagException(messages.get("err.flg.typ.hlp"));
     }

     private int countFlags(String[] flags) {
         int baseLength = args.length;
         for (int i = 0; i < args.length; i++)
             if (args[i].equals("-g") || args[i].equals("--generate"))
                 return ++baseLength;
         return baseLength;

     }

     public void cleanCipherName() throws FlagException {
         if (flags.getFirst().flag == ActionTypes.TYPE) {
             String cipherType = flags.getFirst().value;
             cipherType = cipherType.trim().toUpperCase();
             flags.getFirst().value = cipherType;
         } else throw new FlagException(messages.get("err.flg.typ.tps"));
     }

     public void sortFlags() {
         flags.sort(new FlagsOrder());
     }

     public void sequenceBits() {
         for (FlagTuple<ActionTypes, String> flag : flags)
             actionBits |= flag.flag.getBitStructure();
     }

     public List<FlagTuple<ActionTypes, String>> getFlags() {
         return flags;
     }

    public byte getActionBits() {
        return actionBits;
    }
}
