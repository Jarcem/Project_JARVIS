package org.jarcem.recognizer.beans;
/*
  Author: Jarcem
  Date: 2019/1/10
  Purpose: recognize service properties data
*/

import org.jarcem.recognizer.api.Configuration;
import org.jarcem.recognizer.api.LiveSpeechRecognizer;
import org.jarcem.recognizer.api.SpeechResult;

import java.io.IOException;

public class RecognizeBean {
    protected static Configuration configuration = null;
    protected static LiveSpeechRecognizer liveSpeechRecognizer = null;
    protected static ConfigurationBean configurationBean = null;
    protected static SpeechResult speechResult = null;

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        RecognizeBean.configuration = configuration;
    }

    public static LiveSpeechRecognizer getRecognizer() {
        return liveSpeechRecognizer;
    }

    public static void setRecognizer(Configuration configuration) throws IOException {
        liveSpeechRecognizer = new LiveSpeechRecognizer(configuration);
    }

    public static ConfigurationBean getConfigurationBean() {
        return configurationBean;
    }

    public static void setConfigurationBean(ConfigurationBean configurationBean) {
        RecognizeBean.configurationBean = configurationBean;
    }
}