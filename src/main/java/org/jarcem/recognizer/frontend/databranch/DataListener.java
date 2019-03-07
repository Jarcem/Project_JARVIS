package org.jarcem.recognizer.frontend.databranch;

import org.jarcem.recognizer.frontend.Data;


/**
 * Defines some API-elements for Data-observer classes.
 */
public interface DataListener {

    /**
     * This method is invoked when a new {@link Data} object becomes available.
     *
     * @param data feature frame
     */
    public void processDataFrame(Data data);

}
