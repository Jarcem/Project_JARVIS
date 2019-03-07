/*
 *
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
package org.jarcem.recognizer.instrumentation;

import org.jarcem.recognizer.decoder.ResultListener;
import org.jarcem.recognizer.recognizer.Recognizer;
import org.jarcem.recognizer.recognizer.Recognizer.State;
import org.jarcem.recognizer.recognizer.StateListener;
import org.jarcem.recognizer.result.Result;
import org.jarcem.recognizer.util.NISTAlign;
import org.jarcem.recognizer.util.props.*;

/**
 * Tracks and reports recognition accuracy
 */
abstract public class AccuracyTracker
        extends
        ConfigurableAdapter
        implements
        ResultListener,
        Resetable,
        StateListener,
        Monitor {

    /**
     * The property that defines which recognizer to monitor
     */
    @S4Component(type = Recognizer.class)
    public final static String PROP_RECOGNIZER = "recognizer";

    /**
     * The property that defines whether summary accuracy information is displayed
     */
    @S4Boolean(defaultValue = true)
    public final static String PROP_SHOW_SUMMARY = "showSummary";

    /**
     * The property that defines whether detailed accuracy information is displayed
     */
    @S4Boolean(defaultValue = true)
    public final static String PROP_SHOW_DETAILS = "showDetails";

    /**
     * The property that defines whether recognition results should be displayed.
     */
    @S4Boolean(defaultValue = true)
    public final static String PROP_SHOW_RESULTS = "showResults";


    /**
     * The property that defines whether recognition results should be displayed.
     */
    @S4Boolean(defaultValue = true)
    public final static String PROP_SHOW_ALIGNED_RESULTS = "showAlignedResults";

    /**
     * The property that defines whether recognition results should be displayed.
     */
    @S4Boolean(defaultValue = true)
    public final static String PROP_SHOW_RAW_RESULTS = "showRawResults";
    private final NISTAlign aligner = new NISTAlign(false, false);
    // ------------------------------
    // Configuration data
    // ------------------------------
    private String name;
    private Recognizer recognizer;
    private boolean showSummary;
    private boolean showDetails;
    private boolean showResults;
    private boolean showAlignedResults;
    private boolean showRaw;

    public AccuracyTracker(Recognizer recognizer, boolean showSummary, boolean showDetails, boolean showResults, boolean showAlignedResults, boolean showRawResults) {

        initRecognizer(recognizer);
        initLogger();

        this.showSummary = showSummary;
        this.showDetails = showDetails;
        this.showResults = showResults;
        this.showAlignedResults = showAlignedResults;

        this.showRaw = showRawResults;

        aligner.setShowResults(showResults);
        aligner.setShowAlignedResults(showAlignedResults);
    }

    public AccuracyTracker() {
    }

    /*
     * (non-Javadoc)
     *
     * @see Configurable#newProperties(PropertySheet)
     */
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
        initRecognizer((Recognizer) ps.getComponent(PROP_RECOGNIZER));

        showSummary = ps.getBoolean(PROP_SHOW_SUMMARY);
        showDetails = ps.getBoolean(PROP_SHOW_DETAILS);
        showResults = ps.getBoolean(PROP_SHOW_RESULTS);
        showAlignedResults = ps.getBoolean(PROP_SHOW_ALIGNED_RESULTS);

        showRaw = ps.getBoolean(PROP_SHOW_RAW_RESULTS);

        aligner.setShowResults(showResults);
        aligner.setShowAlignedResults(showAlignedResults);
    }

    private void initRecognizer(Recognizer newRecognizer) {
        if (recognizer == null) {
            recognizer = newRecognizer;
            recognizer.addResultListener(this);
            recognizer.addStateListener(this);
        } else if (recognizer != newRecognizer) {
            recognizer.removeResultListener(this);
            recognizer.removeStateListener(this);
            recognizer = newRecognizer;
            recognizer.addResultListener(this);
            recognizer.addStateListener(this);
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see Resetable
     */
    public void reset() {
        aligner.resetTotals();
    }


    /*
     * (non-Javadoc)
     *
     * @see Configurable#getName()
     */
    public String getName() {
        return name;
    }


    /**
     * Retrieves the aligner used to track the accuracy stats
     *
     * @return the aligner
     */
    public NISTAlign getAligner() {
        return aligner;
    }


    /**
     * Shows the complete details.
     *
     * @param rawText the RAW result
     */
    protected void showDetails(String rawText) {
        if (showDetails) {
            aligner.printSentenceSummary();
            if (showRaw) {
                logger.info("RAW     " + rawText);
            }
            aligner.printTotalSummary();
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see ResultListener#newResult(Result)
     */
    abstract public void newResult(Result result);

    public void statusChanged(Recognizer.State status) {
        if (status == State.DEALLOCATED) {
            if (showSummary) {
                logger.info("\n# --------------- Summary statistics ---------");
                aligner.printTotalSummary();
            }
        }
    }
}