/**
 * Portions Copyright 2002 Sun Microsystems, Inc.
 * Portions Copyright 2002 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 * <p>
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */

package org.jarcem.recognizer.fst.semiring;

/**
 * Probability semiring implementation.
 *
 * @author "John Salatas"
 */
public class ProbabilitySemiring extends Semiring {

    private static final long serialVersionUID = 5592668313009971909L;
    // zero value
    private static float zero = 0.f;

    // one value
    private static float one = 1.f;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jarcem.recognizer.fst.weight.AbstractSemiring#Plus(org.jarcem.recognizer.fst.weight
     * .float, org.jarcem.recognizer.fst.weight.float)
     */
    @Override
    public float plus(float w1, float w2) {
        if (!isMember(w1) || !isMember(w2)) {
            return Float.NEGATIVE_INFINITY;
        }

        return w1 + w2;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jarcem.recognizer.fst.weight.AbstractSemiring#Times(org.jarcem.recognizer.fst.weight
     * .float, org.jarcem.recognizer.fst.weight.float)
     */
    @Override
    public float times(float w1, float w2) {
        if (!isMember(w1) || !isMember(w2)) {
            return Float.NEGATIVE_INFINITY;
        }

        return w1 * w2;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jarcem.recognizer.fst.weight.AbstractSemiring#Divide(org.jarcem.recognizer.fst.
     * weight.float, org.jarcem.recognizer.fst.weight.float)
     */
    @Override
    public float divide(float w1, float w2) {
        // TODO Auto-generated method stub
        return Float.NEGATIVE_INFINITY;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jarcem.recognizer.fst.weight.AbstractSemiring#zero()
     */
    @Override
    public float zero() {
        return zero;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jarcem.recognizer.fst.weight.AbstractSemiring#one()
     */
    @Override
    public float one() {
        return one;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jarcem.recognizer.fst.weight.Semiring#isMember(org.jarcem.recognizer.fst.weight
     * .float)
     */
    @Override
    public boolean isMember(float w) {
        return !Float.isNaN(w) // not a NaN,
                && (w >= 0); // and positive
    }

    /*
     * (non-Javadoc)
     *
     * @see Semiring#reverse(float)
     */
    @Override
    public float reverse(float w1) {
        // TODO: ???
        System.out.println("Not Implemented");
        return Float.NEGATIVE_INFINITY;
    }

}
