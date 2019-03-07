package org.jarcem.recognizer.decoder.scorer;

import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;

import java.util.List;

/**
 * Performs a simple normalization of all token-scores by
 *
 * @author Holger Brandl
 */
public class MaxScoreNormalizer implements ScoreNormalizer {


    public MaxScoreNormalizer() {
    }

    public void newProperties(PropertySheet ps) throws PropertyException {
    }

    public Scoreable normalize(List<? extends Scoreable> scoreableList, Scoreable bestToken) {
        for (Scoreable scoreable : scoreableList) {
            scoreable.normalizeScore(bestToken.getScore());
        }

        return bestToken;
    }
}
