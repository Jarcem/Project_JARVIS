package org.jarcem.recognizer.frontend.databranch;

import org.jarcem.recognizer.frontend.Data;
import org.jarcem.recognizer.util.props.Configurable;

/**
 * Some API-elements which are shared by components which can generate {@link Data}s.
 */
public interface DataProducer extends Configurable {

    /**
     * Registers a new listener for <code>Data</code>s.
     *
     * @param l listener to add
     */
    void addDataListener(DataListener l);


    /**
     * Unregisters a listener for <code>Data</code>s.
     *
     * @param l listener to remove
     */
    void removeDataListener(DataListener l);
}
