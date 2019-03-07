package org.jarcem.recognizer.linguist.allphone;

import org.jarcem.recognizer.linguist.SearchGraph;
import org.jarcem.recognizer.linguist.SearchState;
import org.jarcem.recognizer.linguist.acoustic.HMMPosition;
import org.jarcem.recognizer.linguist.acoustic.HMMState;
import org.jarcem.recognizer.linguist.acoustic.UnitManager;
import org.jarcem.recognizer.util.LogMath;

public class AllphoneSearchGraph implements SearchGraph {

    private AllphoneLinguist linguist;

    public AllphoneSearchGraph(AllphoneLinguist linguist) {
        this.linguist = linguist;
    }

    public SearchState getInitialState() {
        HMMState silHmmState = linguist.getAcousticModel().lookupNearestHMM(UnitManager.SILENCE, HMMPosition.UNDEFINED, true).getInitialState();
        return new PhoneHmmSearchState(silHmmState, linguist, LogMath.LOG_ONE, LogMath.LOG_ONE);
    }

    public int getNumStateOrder() {
        return 2;
    }

    public boolean getWordTokenFirst() {
        return false;
    }
}
