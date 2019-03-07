package org.jarcem.recognizer.frontend.databranch;

import org.jarcem.recognizer.frontend.BaseDataProcessor;
import org.jarcem.recognizer.frontend.Data;
import org.jarcem.recognizer.frontend.DataProcessingException;
import org.jarcem.recognizer.util.props.Configurable;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;
import org.jarcem.recognizer.util.props.S4ComponentList;
import org.jarcem.recognizer.decoder.FrameDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates push-branches out of a Frontend. This might be used for for push-decoding or to create new pull-streams
 *
 * @see FrameDecoder
 * @see DataBufferProcessor
 */
public class FrontEndSplitter extends BaseDataProcessor implements DataProducer {


    @S4ComponentList(type = Configurable.class, beTolerant = true)
    public static final String PROP_DATA_LISTENERS = "dataListeners";
    private List<DataListener> listeners = new ArrayList<DataListener>();

    public FrontEndSplitter() {
    }

    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);

        listeners = ps.getComponentList(PROP_DATA_LISTENERS, DataListener.class);
    }


    /**
     * Reads and returns the next Data frame or return <code>null</code> if no data is available.
     *
     * @return the next Data or <code>null</code> if none is available
     * @throws DataProcessingException if there is a data processing error
     */
    @Override
    public Data getData() throws DataProcessingException {
        Data input = getPredecessor().getData();

        for (DataListener l : listeners)
            l.processDataFrame(input);

        return input;
    }


    public void addDataListener(DataListener l) {
        if (l == null) {
            return;
        }
        listeners.add(l);
    }


    public void removeDataListener(DataListener l) {
        if (l == null) {
            return;
        }
        listeners.remove(l);
    }
}

