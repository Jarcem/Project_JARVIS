package org.jarcem.recognizer.linguist.language.ngram;

import org.jarcem.recognizer.linguist.WordSequence;
import org.jarcem.recognizer.linguist.dictionary.Word;
import org.jarcem.recognizer.linguist.util.LRUCache;
import org.jarcem.recognizer.util.LogMath;
import org.jarcem.recognizer.util.props.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/*
 * The client of the SRILM language model server. It needs to read the
 * vocabulary from a vocabulary file though.
 */
public class NetworkLanguageModel implements LanguageModel {

    /**
     * The property specifying the host of the language model server.
     */
    @S4String(defaultValue = "localhost")
    public final static String PROP_HOST = "host";

    /**
     * The property specifying the port of the language model server.
     */
    @S4Integer(defaultValue = 2525)
    public final static String PROP_PORT = "port";

    LogMath logMath;
    int maxDepth;
    Socket socket;
    LRUCache<WordSequence, Float> cache;
    private String host;
    private int port;
    private URL location;
    private BufferedReader inReader;
    private PrintWriter outWriter;
    private boolean allocated;

    /**
     * Creates network language model client
     *
     * @param host     server host
     * @param port     server port
     * @param location URL of the file with vocabulary (only needed for 1-stage
     *                 model)
     * @param maxDepth depth of the model
     */
    public NetworkLanguageModel(String host, int port, URL location,
                                int maxDepth) {
        this.host = host;
        this.port = port;
        this.maxDepth = maxDepth;
        this.location = location;
        logMath = LogMath.getLogMath();
    }

    public NetworkLanguageModel() {
    }

    /*
     * (non-Javadoc)
     * @see
     * Configurable#newProperties(org.jarcem.recognizer.util
     * .props.PropertySheet)
     */
    public void newProperties(PropertySheet ps) throws PropertyException {

        if (allocated) {
            throw new RuntimeException("Can't change properties after allocation");
        }
        host = ps.getString(PROP_HOST);
        port = ps.getInt(PROP_PORT);
        location = ConfigurationManagerUtils.getResource(PROP_LOCATION, ps);

        maxDepth = ps.getInt(PROP_MAX_DEPTH);
        if (maxDepth == -1)
            maxDepth = 3;
    }

    public void allocate() throws IOException {
        allocated = true;

        socket = new Socket(host, port);
        inReader =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outWriter = new PrintWriter(socket.getOutputStream(), true);
        String greeting = inReader.readLine();
        if (!greeting.equals("probserver ready")) {
            throw new IOException("Incorrect input");
        }
        cache = new LRUCache<WordSequence, Float>(1000);
    }

    public void deallocate() {
        allocated = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public float getProbability(WordSequence wordSequence) {

        Float probability = cache.get(wordSequence);

        if (probability != null) {
            return probability.floatValue();
        }

        StringBuilder builder = new StringBuilder();
        if (wordSequence.size() == 0)
            return 0.0f;
        for (Word w : wordSequence.getWords()) {
            builder.append(w.toString());
            builder.append(' ');
        }
        outWriter.println(builder.toString());
        String result = "0";
        try {
            result = inReader.readLine();
            if (result.charAt(0) == 0)
                result = result.substring(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!result.equals("-inf"))
            probability = logMath.log10ToLog(Float.parseFloat(result));
        else
            probability = LogMath.LOG_ZERO;

        cache.put(wordSequence, probability);
        return probability.floatValue();
    }

    public float getSmear(WordSequence wordSequence) {
        return 0.0f;
    }

    public Set<String> getVocabulary() {
        Set<String> result = new HashSet<String>();
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(location.openStream()));
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null)
                    break;
                if (line.length() == 0)
                    continue;
                result.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onUtteranceEnd() {
        //TODO not implemented
    }

}
