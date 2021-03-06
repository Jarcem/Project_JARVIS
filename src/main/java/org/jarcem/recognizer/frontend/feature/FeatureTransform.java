/*
 * Copyright 1999-2010 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * Portions Copyright 2008 PC-NG Inc.
 *
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */
package org.jarcem.recognizer.frontend.feature;

import org.jarcem.recognizer.frontend.BaseDataProcessor;
import org.jarcem.recognizer.frontend.Data;
import org.jarcem.recognizer.frontend.DataProcessingException;
import org.jarcem.recognizer.frontend.FloatData;
import org.jarcem.recognizer.linguist.acoustic.tiedstate.Loader;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;
import org.jarcem.recognizer.util.props.S4Component;

/**
 * Implements a linear feature transformation transformation.
 * <p>
 * It might be a dimension reduction or just a decorrelation transform. This
 * component requires a special model trained with LDA/MLLT transform.
 */
public class FeatureTransform extends BaseDataProcessor {

    /**
     * The name of the transform matrix file.
     */
    @S4Component(type = Loader.class)
    public final static String PROP_LOADER = "loader";
    protected Loader loader;
    float[][] transform;
    int rows;
    int values;

    public FeatureTransform(Loader loader) {
        initLogger();
        init(loader);
    }

    public FeatureTransform() {
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * Configurable#newProperties(org.jarcem.recognizer.util
     * .props.PropertySheet)
     */
    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
        init((Loader) ps.getComponent(PROP_LOADER));
    }

    private void init(Loader loader) {
        this.loader = loader;

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        transform = loader.getTransformMatrix();
    }

    /**
     * Returns the next Data object being processed by this LDA, or if it is a
     * Signal, it is returned without modification.
     *
     * @return the next available Data object, returns null if no Data object is
     * available
     * @throws DataProcessingException if there is a processing error
     * @see Data
     */
    @Override
    public Data getData() throws DataProcessingException {
        Data data = getPredecessor().getData();

        if (null == transform || null == data || !(data instanceof FloatData))
            return data;

        FloatData floatData = (FloatData) data;
        float[] features = floatData.getValues();

        if (features.length > transform[0].length + 1)
            throw new IllegalArgumentException("dimenstion mismatch");

        float[] result = new float[transform.length];

        for (int i = 0; i < transform.length; ++i) {
            for (int j = 0; j < features.length; ++j)
                result[i] += transform[i][j] * features[j];
        }

        if (features.length > transform[0].length) {
            for (int i = 0; i < transform.length; ++i)
                result[i] += transform[i][features.length];
        }

        return new FloatData(result,
                floatData.getSampleRate(),
                floatData.getFirstSampleNumber());
    }
}
