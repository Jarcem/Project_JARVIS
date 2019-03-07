/*
 * Copyright 2007 LORIA, France.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */
package org.jarcem.recognizer.linguist.acoustic.tiedstate.HTK;

/**
 * This is simply a GMMDiag with a label which is a HMM name (String) and a state
 * number
 *
 * @author Christophe Cerisara
 */
public class HMMState {
    public final GMMDiag gmm;
    public int gmmidx = -1;
    public Lab lab;

    public HMMState(GMMDiag g, Lab l) {
        lab = l;
        gmm = g;
    }

    public float getLogLike() {
        return gmm.getLogLike();
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab l) {
        lab = l;
    }
}
