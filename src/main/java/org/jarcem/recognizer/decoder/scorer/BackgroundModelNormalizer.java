package org.jarcem.recognizer.decoder.scorer;

import org.jarcem.recognizer.decoder.search.SimpleBreadthFirstSearchManager;
import org.jarcem.recognizer.decoder.search.Token;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;
import org.jarcem.recognizer.util.props.S4Component;

import java.util.List;
import java.util.logging.Logger;

/**
 * Normalizes a set of Tokens against the best scoring Token of a background model.
 *
 * @author Holger Brandl
 */
public class BackgroundModelNormalizer implements ScoreNormalizer {

    /**
     * The active list provider used to determined the best token for normalization. If this reference is not defined no
     * normalization will be applied.
     */
    @S4Component(type = SimpleBreadthFirstSearchManager.class, mandatory = false)
    public static final String ACTIVE_LIST_PROVIDER = "activeListProvider";
    private SimpleBreadthFirstSearchManager activeListProvider;

    private Logger logger;

    public BackgroundModelNormalizer() {
    }

    /**
     * @param activeListProvider The active list provider used to determined the best token for normalization. If this reference is not defined no
     *                           normalization will be applied.
     */
    public BackgroundModelNormalizer(SimpleBreadthFirstSearchManager activeListProvider) {
        this.activeListProvider = activeListProvider;
        this.logger = Logger.getLogger(getClass().getName());

        logger.warning("no active list set.");
    }

    public void newProperties(PropertySheet ps) throws PropertyException {
        this.activeListProvider = (SimpleBreadthFirstSearchManager) ps.getComponent(ACTIVE_LIST_PROVIDER);
        this.logger = ps.getLogger();

        logger.warning("no active list set.");
    }

    public Scoreable normalize(List<? extends Scoreable> scoreableList, Scoreable bestToken) {
        if (activeListProvider == null) {
            return bestToken;
        }

        Token normToken = activeListProvider.getActiveList().getBestToken();

        float normScore = normToken.getScore();

        for (Scoreable scoreable : scoreableList) {
            if (scoreable instanceof Token) {
                scoreable.normalizeScore(normScore);
            }
        }

        return bestToken;
    }
}
