/**
 * Copyright 1999-2012 Carnegie Mellon University.
 * Portions Copyright 2002 Sun Microsystems, Inc.
 * Portions Copyright 2002 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 * <p>
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */

package org.jarcem.recognizer.fst.operations;

import org.jarcem.recognizer.fst.Arc;
import org.jarcem.recognizer.fst.Fst;
import org.jarcem.recognizer.fst.ImmutableFst;
import org.jarcem.recognizer.fst.State;

import java.util.Comparator;

/**
 * ArcSort operation.
 *
 * @author John Salatas
 */
public class ArcSort {
    /**
     * Default Constructor
     */
    private ArcSort() {
    }

    /**
     * Applies the ArcSort on the provided fst. Sorting can be applied either on
     * input or output label based on the provided comparator.
     * <p>
     * ArcSort can be applied to both {@link Fst} and
     * {@link ImmutableFst}
     *
     * @param fst the fst to sort it's arcs
     * @param cmp the provided Comparator
     */
    public static void apply(Fst fst, Comparator<Arc> cmp) {
        int numStates = fst.getNumStates();
        for (int i = 0; i < numStates; i++) {
            State s = fst.getState(i);
            s.arcSort(cmp);
        }
    }
}
