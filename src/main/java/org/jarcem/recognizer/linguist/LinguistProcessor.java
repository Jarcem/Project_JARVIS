/*
 * Copyright 1999-2003 Carnegie Mellon University.
 * Portions Copyright 2002-2003 Sun Microsystems, Inc.
 * Portions Copyright 2002-2003 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package org.jarcem.recognizer.linguist;

import org.jarcem.recognizer.util.props.Configurable;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;
import org.jarcem.recognizer.util.props.S4Component;

/**
 * A standard interface for a linguist processor
 */
public class LinguistProcessor implements Configurable, Runnable {

    /**
     * The property that defines the name of the linguist to process
     */
    @S4Component(type = Linguist.class)
    public final static String PROP_LINGUIST = "linguist";


    // ----------------------------
    // Configuration data
    // ----------------------------
    private String name;
    private Linguist linguist;

    public LinguistProcessor(Linguist linguist) {
        this.linguist = linguist;
    }

    public LinguistProcessor() {
    }

    /* (non-Javadoc)
     * @see Configurable#newProperties(PropertySheet)
     */
    public void newProperties(PropertySheet ps) throws PropertyException {
        linguist = (Linguist) ps.getComponent(PROP_LINGUIST);
    }


    /* (non-Javadoc)
     * @see Configurable#getName()
     */
    public String getName() {
        return name;
    }


    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {

    }


    /**
     * Returns the configured lingust
     *
     * @return the linguist
     */
    protected Linguist getLinguist() {
        return linguist;
    }
}


