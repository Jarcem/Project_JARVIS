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

package org.jarcem.recognizer.trainer;

import org.jarcem.recognizer.linguist.acoustic.tiedstate.trainer.TrainerScore;
import org.jarcem.recognizer.util.props.Configurable;

import java.io.IOException;


/**
 * Provides mechanisms for computing statistics given a set of states and input data.
 */
public interface Learner extends Configurable {

    /**
     * Starts the Learner.
     */
    public void start();


    /**
     * Stops the Learner.
     */
    public void stop();


    /**
     * Sets the learner to use a utterance.
     *
     * @param utterance the utterance
     * @throws IOException if exception occurred
     */
    public void setUtterance(Utterance utterance) throws IOException;


    /**
     * Initializes computation for current utterance and utterance graph.
     *
     * @param utterance the current utterance
     * @param graph     the current utterance graph
     * @throws IOException if exception occurred
     */
    public void initializeComputation(Utterance utterance,
                                      UtteranceGraph graph) throws IOException;


    /**
     * Implements the setGraph method. Since the flat initializer does not need a graph, this method produces an error.
     *
     * @param graph the graph
     */
    public void setGraph(UtteranceGraph graph);


    /**
     * @return posterior probabilities for a given state.
     */
    public TrainerScore[] getScore();
}
