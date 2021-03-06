package org.jarcem.recognizer.tools.batch;

import org.jarcem.recognizer.decoder.search.Token;
import org.jarcem.recognizer.frontend.DataProcessor;
import org.jarcem.recognizer.linguist.language.grammar.BatchForcedAlignerGrammar;
import org.jarcem.recognizer.linguist.language.grammar.ForcedAlignerGrammar;
import org.jarcem.recognizer.recognizer.Recognizer;
import org.jarcem.recognizer.result.Result;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Copyright 1999-2002 Carnegie Mellon University. Portions Copyright 2002 Sun Microsystems, Inc. Portions Copyright
 * 2002 Mitsubishi Electric Research Laboratories. All Rights Reserved.  Use is subject to license terms.
 * <p>
 * See the file "license.terms" for information on usage and redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 * <p>
 * User: Peter Wolf Date: Jan 9, 2006 Time: 5:35:54 PM
 * <p>
 * Utility for generating word segmentation by forced alignment
 * <p>
 * Given a CTL file that specifies a series of audio and coresponding correct transcripts, this utility creates a
 * trivial grammar from the transcript, and runs the recognizer on the utterance.  The output is words with beginning
 * and end times.
 * <p>
 * See BatchNISTRecognizer for more information about the format of CTL and audio files.
 */

public class BatchForcedAlignerRecognizer extends BatchNISTRecognizer {

    public BatchForcedAlignerGrammar bfaGrammar;
    String segFile;
    ForcedAlignerGrammar forcedAlignerGrammar;


    public BatchForcedAlignerRecognizer(
            BatchForcedAlignerGrammar bfaGrammar,
            Recognizer recognizer,
            List<DataProcessor> inputDataProcessors,
            String ctlFile,
            String dataDir,
            String refFile,
            String ctmFile,
            int bitsPerSample,
            int samplesPerSecond,
            int framesPerSecond,
            int channelCount
    ) {
        super(recognizer,
                inputDataProcessors,
                ctlFile,
                dataDir,
                refFile,
                ctmFile,
                bitsPerSample,
                samplesPerSecond,
                framesPerSecond,
                channelCount);

        this.bfaGrammar = bfaGrammar;
    }


    public BatchForcedAlignerRecognizer() {

    }

    public static void main(String[] argv) {

        if (argv.length != 1) {
            System.out.println("Usage: BatchForcedAlignerRecognizer propertiesFile");
            System.exit(1);
        }

        BatchNISTRecognizer.main(argv);
    }

    @Override
    protected void setInputStream(CTLUtterance utt) throws IOException {
        super.setInputStream(utt);
        bfaGrammar.setUtterance(utt.getName());
    }

    @Override
    protected void handleResult(DataOutputStream out, CTLUtterance utt, Result result) throws IOException {
        System.out.println(utt + " --> " + result);
        Token token = result.getBestToken();
        dumpTokenTimes(token);
    }

    void dumpTokenTimes(Token token) {
        if (token != null) {
            dumpTokenTimes(token.getPredecessor());
            System.out.println(token.getWord() + " " + token.getCollectTime());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see Configurable#newProperties(PropertySheet)
     */
    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);

        bfaGrammar = (BatchForcedAlignerGrammar) ps.getComponent("forcedAlignerGrammar");
    }
}
