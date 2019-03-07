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
 * A Null pruner. Does no actual pruning
 */
public class NullPruner implements Pruner {


    /**
     * Creates a simple pruner
     */
    public NullPruner() {
    }

    /* (non-Javadoc)
     * @see Configurable#newProperties(PropertySheet)
     */
    public void newProperties(PropertySheet ps) throws PropertyException {
    }

    /**
     * starts the pruner
     */
    public void startRecognition() {
    }


    /**
     * prunes the given set of states
     *
     * @param activeList the active list of tokens
     * @return the pruned (and possibly new) activeList
     */
    public ActiveList prune(ActiveList activeList) {
        return activeList;
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
