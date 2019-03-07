package org.jarcem.recognizer.beans;

import java.io.IOException;
import java.util.Properties;

/*
  Author: Jarcem
  Date: 2019/1/3
  Purpose: whole configuration data bean
*/

public class ConfigurationBean {
    private static String ACOUSTIC_MODEl_PATH = null;
    private static String DICTIONARY_PATH = null;
    private static String LANGUAGE_MODEL_PATH = null;
    private static String GRAMMAR_PATH = null;
    private static String GRAMMAR_NAME = null;
    private static boolean GRAMMAR_SWITCH = false;

    public static void initConfiguration() {
        Properties properties = new Properties();
        try {
            properties.load(new ConfigurationBean().getClass().getResourceAsStream("/org/jarcem/recognizer/data/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setACOUSTIC_MODEl_PATH(properties.getProperty("ACOUSTIC_MODEl_PATH"));
        setDictionaryPath(properties.getProperty("DICTIONARY_PATH"));
        setLanguageModelPath(properties.getProperty("LANGUAGE_MODEL_PATH"));
        setGrammarPath(properties.getProperty("GRAMMAR_PATH"));
        setGrammarName(properties.getProperty("GRAMMAR_NAME"));
        setGrammarSwitch(Boolean.valueOf(properties.getProperty("GRAMMAR_SWITCH")));

//        Enumeration<?> enumeration = properties.propertyNames();
//        while (enumeration.hasMoreElements()){
//            String key=(String) enumeration.nextElement();
//            String value=properties.getProperty(key);
//            System.out.println(key +"                  "+value);
//        }
    }

    public static String getACOUSTIC_MODEl_PATH() {
        return ACOUSTIC_MODEl_PATH;
    }

    public static void setACOUSTIC_MODEl_PATH(String ACOUSTIC_MODEl_PATH) {
        ConfigurationBean.ACOUSTIC_MODEl_PATH = ACOUSTIC_MODEl_PATH;
    }

    public static String getDictionaryPath() {
        return DICTIONARY_PATH;
    }

    public static void setDictionaryPath(String dictionaryPath) {
        DICTIONARY_PATH = dictionaryPath;
    }

    public static String getLanguageModelPath() {
        return LANGUAGE_MODEL_PATH;
    }

    public static void setLanguageModelPath(String languageModelPath) {
        LANGUAGE_MODEL_PATH = languageModelPath;
    }

    public static String getGrammarPath() {
        return GRAMMAR_PATH;
    }

    public static void setGrammarPath(String grammarPath) {
        GRAMMAR_PATH = grammarPath;
    }

    public static boolean isGrammarSwitch() {
        return GRAMMAR_SWITCH;
    }

    public static void setGrammarSwitch(boolean grammarSwitch) {
        GRAMMAR_SWITCH = grammarSwitch;
    }

    public static String getGrammarName() {
        return GRAMMAR_NAME;
    }

    public static void setGrammarName(String grammarName) {
        GRAMMAR_NAME = grammarName;
    }
}
