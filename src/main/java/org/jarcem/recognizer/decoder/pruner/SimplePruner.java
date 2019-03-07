/*
 * Copyright 1999-2002 Carnegie Mellon University.
 * Portions Copyright 2002 Sun Microsystems, Inc.
 * Portions Copyright 2002 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package org.jarcem.recognizer.decoder.pruner;

import org.jarcem.recognizer.decoder.search.ActiveList;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;

/**
 * Performs the default pruning behavior which is to invoke the purge on the active list
 */
public class SimplePruner implements Pruner {

    private String name;


    public SimplePruner() {
    }

    /* (non-Javadoc)
     * @see Configurable#newProperties(PropertySheet)
     */
    public void newProperties(PropertySheet ps) throws PropertyException {
    }

    /* (non-Javadoc)
     * @see Configurable#getName()
     */
    public String getName() {
        return name;
    }


    /**
     * Starts the pruner
     */
    public void startRecognition() {
    }


    /**
     * prunes the given set of states
     *
     * @param activeList a activeList of tokens
     */
    public ActiveList prune(ActiveList activeList) {
        return activeList.purge();
    }


    /**
     * Performs post-recognition cleanup.
     */
    public void stopRecognition() {
    }


    /* (non-Javadoc)
     * @see Pruner#allocate()
     */
    public void allocate() {
    }


    /* (non-Javadoc)
     * @see Pruner#deallocate()
     */
    public void deallocate() {

    }


}


