package Components;

import Constants.Flags.ActionTypes;
import Constants.Messages;
import Exceptions.FlagExcpetion;
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

     public void collectFlags() throws FlagExcpetion {
         if (args[0].charAt(0) != '-')
             throw new FlagExcpetion(args[0].concat(messages.get("err.flg.typ.fst")));
         int counter = 0;
         ActionTypes flag;
         String value;
         if (args.length % 2 != 0)
             throw new FlagExcpetion(messages.get("err.flg.typ.mod"));
         while (counter < args.length) {
             switch (args[counter].trim().toLowerCase()) {
                 case "-t", "--type" -> flag = ActionTypes.TYPE;
                 case "-e", "--encrypt" -> flag = ActionTypes.ENCRYPT;
                 case "-d", "--decrypt" -> flag = ActionTypes.DECRYPT;
                 case "-g", "--generate" -> flag = ActionTypes.GENERATE;
                 case "-k", "--key" -> flag = ActionTypes.KEY;
                 case "-o", "--output" -> flag = ActionTypes.OUTPUT;
                 case "-h", "--help" -> throw new FlagExcpetion(messages.get("err.flg.typ.hlp"));
                 default -> throw new FlagExcpetion(
                     args[counter] + " " + messages.get("err.flg.typ.reg"));
             }
             value = args[counter + 1];
             flags.add(new FlagTuple(flag, value));
             counter+=2;
         }
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
