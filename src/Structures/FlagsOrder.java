package Structures;

import Constants.Flags.ActionTypes;

import java.util.Comparator;

public class FlagsOrder implements Comparator<FlagTuple<ActionTypes, String>> {

    @Override
    public int compare(FlagTuple<ActionTypes, String> a1, FlagTuple<ActionTypes, String> a2) {
        return a2.flag.getBitStructure() - a1.flag.getBitStructure();
    }
}
