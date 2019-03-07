/*
 * Copyright 1999-2013 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */

package org.jarcem.recognizer.tools.transcriber;

import org.jarcem.recognizer.api.Configuration;
import org.jarcem.recognizer.api.SpeechResult;
import org.jarcem.recognizer.api.StreamSpeechRecognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Transcriber {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        configuration
                .setAcousticModelPath("resource:/org/jarcem/recognizer/models/en-us/en-us");
        configuration
                .setDictionaryPath("resource:/org/jarcem/recognizer/models/en-us/cmudict-en-us.dict");
        configuration
                .setLanguageModelPath("resource:/org/jarcem/recognizer/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
                configuration);
        InputStream stream = new FileInputStream(new File(args[0]));
        stream.skip(44);

        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.println(result.getHypothesis());
        }
        recognizer.stopRecognition();
    }
}
