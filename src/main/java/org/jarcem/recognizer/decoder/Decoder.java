/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */
package org.jarcem.recognizer.decoder;

import org.jarcem.recognizer.decoder.search.SearchManager;
import org.jarcem.recognizer.result.Result;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;
import org.jarcem.recognizer.util.props.S4Integer;

import java.util.List;

/**
 * The primary decoder class
 */
public class Decoder extends AbstractDecoder {

    /**
     * The property for the number of features to recognize at once.
     */
    @S4Integer(defaultValue = Integer.MAX_VALUE)
    public final static String PROP_FEATURE_BLOCK_SIZE = "featureBlockSize";
    private int featureBlockSize;

    public Decoder() {
        // Keep this or else XML configuration fails.
    }

    /**
     * Main decoder
     *
     * @param searchManager       search manager to configure search space
     * @param fireNonFinalResults should we notify about non-final results
     * @param autoAllocate        automatic allocation of all componenets
     * @param resultListeners     listeners to get signals
     * @param featureBlockSize    frequency of notification about results
     */
    public Decoder(SearchManager searchManager, boolean fireNonFinalResults, boolean autoAllocate, List<ResultListener> resultListeners, int featureBlockSize) {
        super(searchManager, fireNonFinalResults, autoAllocate, resultListeners);
        this.featureBlockSize = featureBlockSize;
    }

    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
        featureBlockSize = ps.getInt(PROP_FEATURE_BLOCK_SIZE);
    }

    /**
     * Decode frames until recognition is complete.
     *
     * @param referenceText the reference text (or null)
     * @return a result
     */
    @Override
    public Result decode(String referenceText) {
        searchManager.startRecognition();
        Result result;
        do {
            result = searchManager.recognize(featureBlockSize);
            if (result != null) {
                result.setReferenceText(referenceText);
                fireResultListeners(result);
            }
        } while (result != null && !result.isFinal());
        searchManager.stopRecognition();
        return result;
    }
}
