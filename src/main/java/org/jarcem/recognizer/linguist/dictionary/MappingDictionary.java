/*
 * Copyright 1999-2009 Carnegie Mellon University.
 * Copyright 2009 PC-NG Inc.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package org.jarcem.recognizer.linguist.dictionary;

import org.jarcem.recognizer.linguist.acoustic.Context;
import org.jarcem.recognizer.linguist.acoustic.Unit;
import org.jarcem.recognizer.linguist.acoustic.UnitManager;
import org.jarcem.recognizer.util.props.ConfigurationManagerUtils;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;
import org.jarcem.recognizer.util.props.S4String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Maps the phones from one phoneset to another to use dictionary from the one
 * acoustic mode with another one. The mapping file is specified with a mapList
 * property. The contents should look like
 *
 * <pre>
 * AX AH
 * IX IH
 * </pre>
 */
public class MappingDictionary extends TextDictionary implements Dictionary {

    @S4String(mandatory = true, defaultValue = "")
    public static final String PROP_MAP_FILE = "mapFile";
    private final Map<String, String> mapping = new HashMap<String, String>();
    private URL mappingFile;

    public MappingDictionary(URL mappingFile, URL wordDictionaryFile, URL fillerDictionaryFile, List<URL> addendaUrlList,
                             String wordReplacement, UnitManager unitManager) {
        super(wordDictionaryFile, fillerDictionaryFile, addendaUrlList, wordReplacement, unitManager);
        this.mappingFile = mappingFile;
    }

    public MappingDictionary() {

    }

    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);

        mappingFile = ConfigurationManagerUtils.getResource(PROP_MAP_FILE, ps);
    }

    /*
     * (non-Javadoc)
     *
     * @see Dictionary#allocate()
     */
    @Override
    public void allocate() throws IOException {
        super.allocate();
        if (!mappingFile.getFile().equals(""))
            loadMapping(mappingFile.openStream());
    }

    /**
     * Gets a context independent unit. There should only be one instance of any
     * CI unit
     * <p>
     * <p>
     * the name of the unit
     * <p>
     * if true, the unit is a filler unit
     *
     * @return the unit
     */
    @Override
    protected Unit getCIUnit(String name, boolean isFiller) {
        if (mapping.containsKey(name)) {
            name = mapping.get(name);
        }
        return unitManager.getUnit(name, isFiller, Context.EMPTY_CONTEXT);
    }

    protected void loadMapping(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            if (st.countTokens() != 2) {
                throw new IOException("Wrong file format");
            }
            mapping.put(st.nextToken(), st.nextToken());
        }
        br.close();
        isr.close();
        inputStream.close();
    }
}
