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

import java.util.Comparator;

/**
 * Comparator used in {@link ArcSort} for sorting
 * based on output labels
 *
 * @author John Salatas
 */
public class OLabelCompare implements Comparator<Arc> {

    /*
     * (non-Javadoc)
     *
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Arc o1, Arc o2) {
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        return (o1.getOlabel() < o2.getOlabel()) ? -1 : ((o1.getOlabel() == o2
                .getOlabel()) ? 0 : 1);
    }

}
