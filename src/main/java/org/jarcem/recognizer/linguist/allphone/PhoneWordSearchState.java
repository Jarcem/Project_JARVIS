package org.jarcem.recognizer.linguist.allphone;

import org.jarcem.recognizer.linguist.SearchStateArc;
import org.jarcem.recognizer.linguist.WordSearchState;
import org.jarcem.recognizer.linguist.acoustic.HMM;
import org.jarcem.recognizer.linguist.acoustic.LeftRightContext;
import org.jarcem.recognizer.linguist.acoustic.Unit;
import org.jarcem.recognizer.linguist.acoustic.UnitManager;
import org.jarcem.recognizer.linguist.dictionary.Pronunciation;
import org.jarcem.recognizer.linguist.dictionary.Word;
import org.jarcem.recognizer.util.LogMath;

import java.util.ArrayList;

public class PhoneWordSearchState extends PhoneNonEmittingSearchState implements WordSearchState {

    public PhoneWordSearchState(Unit unit, AllphoneLinguist linguist, float insertionProb, float languageProb) {
        super(unit, linguist, insertionProb, languageProb);
    }

    public SearchStateArc[] getSuccessors() {
        ArrayList<SearchStateArc> result = new ArrayList<SearchStateArc>();
        Unit rc = UnitManager.SILENCE;
        Unit base = unit.getBaseUnit();
        if (unit.isContextDependent())
            rc = ((LeftRightContext) unit.getContext()).getRightContext()[0];
        ArrayList<HMM> successors = linguist.useContextDependentPhones() ? linguist.getCDSuccessors(base, rc) : linguist.getCISuccessors();
        for (HMM successor : successors)
            result.add(new PhoneHmmSearchState(successor.getInitialState(), linguist, linguist.getPhoneInsertionProb(), LogMath.LOG_ONE));
        return result.toArray(new SearchStateArc[result.size()]);
    }

    public boolean isFinal() {
        return true;
    }

    public Pronunciation getPronunciation() {
        Unit[] pronUnits = new Unit[1];
        pronUnits[0] = unit;
        Pronunciation p = new Pronunciation(pronUnits, "", 1.0f);
        p.setWord(new Word(unit.getName(), null, false));
        return p;
    }

    public boolean isWordStart() {
        return false;
    }

    public int getOrder() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PhoneWordSearchState))
            return false;
        boolean haveSameBaseId = ((PhoneWordSearchState) obj).unit.getBaseID() == unit.getBaseID();
        boolean haveSameContex = ((PhoneWordSearchState) obj).unit.getContext().equals(unit.getContext());
        return haveSameBaseId && haveSameContex;
    }

    @Override
    public int hashCode() {
        return unit.getContext().hashCode() * 91 + unit.getBaseID();
    }
}
