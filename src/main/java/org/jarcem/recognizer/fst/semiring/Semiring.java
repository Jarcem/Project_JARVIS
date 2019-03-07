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

package org.jarcem.recognizer.fst.semiring;

import java.io.Serializable;

/**
 * Abstract semiring class.
 *
 * @author "John Salatas"
 */
public abstract class Semiring implements Serializable {

    // significant decimal digits in floating point numbers
    protected static final int accuracy = 5;
    private static final long serialVersionUID = 1L;

    public abstract float plus(float w1, float w2);

    public abstract float reverse(float w1);

    public abstract float times(float w1, float w2);


    public abstract float divide(float w1, float w2);

    public abstract float zero();

    public abstract float one();

    public abstract boolean isMember(float w);

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getClass().toString();
    }

    /**
     * NATURAL ORDER
     * <p>
     * By definition: a &lt;= b iff a + b = a
     * <p>
     * The natural order is a negative partial order iff the semiring is
     * idempotent. It is trivially monotonic for plus. It is left (resp. right)
     * monotonic for times iff the semiring is left (resp. right) distributive.
     * It is a total order iff the semiring has the path property.
     * <p>
     * See Mohri,
     * "Semiring Framework and Algorithms for Shortest-Distance Problems",
     * Journal of Automata, Languages and Combinatorics 7(3):321-350, 2002.
     * <p>
     * We define the strict version of this order below.
     *
     * @param w1 first operand
     * @param w2 second operand
     * @return less or more
     */
    public boolean naturalLess(float w1, float w2) {
        return (plus(w1, w2) == w1) && (w1 != w2);
    }

}
