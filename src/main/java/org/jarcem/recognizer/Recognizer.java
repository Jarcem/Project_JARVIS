package org.jarcem.recognizer;
/*
  Author: Jarcem
  Date: 2019/1/10
  Purpose: do recognize job
*/

import org.jarcem.recognizer.api.Configuration;
import org.jarcem.recognizer.api.LiveSpeechRecognizer;
import org.jarcem.recognizer.beans.ConfigurationBean;
import org.jarcem.recognizer.beans.RecognizeBean;

import java.io.IOException;

public class Recognizer extends RecognizeBean {
    public static boolean lsrSwitch = false;

    public static void initSystem() throws IOException {
        ConfigurationBean.initConfiguration();
        configuration = new Configuration();

        configuration.setAcousticModelPath(ConfigurationBean.getACOUSTIC_MODEl_PATH());
        configuration.setDictionaryPath(ConfigurationBean.getDictionaryPath());
        configuration.setLanguageModelPath(ConfigurationBean.getLanguageModelPath());
        configuration.setGrammarPath(ConfigurationBean.getGrammarPath());
        configuration.setGrammarName(ConfigurationBean.getGrammarName());
        configuration.setUseGrammar(ConfigurationBean.isGrammarSwitch());

        setRecognizer(configuration);

        if (lsrSwitch) {
            stopRecognition();
            lsrSwitch = true;
            liveSpeechRecognizer.startRecognition(lsrSwitch);
        } else {
            lsrSwitch = true;
            liveSpeechRecognizer.startRecognition(lsrSwitch);
        }
//        while ((speechResult = liveSpeechRecognizer.getResult()) != null) {
//            System.out.format("Hypothesis: %s\n", speechResult.getHypothesis());
//        }
    }

    public static LiveSpeechRecognizer getLiveSpeechRecognizer(){
        return liveSpeechRecognizer;
    }

    public static void stopRecognition() {
        getRecognizer().stopRecognition();
    }
}