package org.jarcem.recognizer.decoder.scorer;

import org.jarcem.recognizer.decoder.search.Token;
import org.jarcem.recognizer.frontend.*;
import org.jarcem.recognizer.frontend.endpoint.SpeechEndSignal;
import org.jarcem.recognizer.frontend.util.DataUtil;
import org.jarcem.recognizer.util.props.ConfigurableAdapter;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;
import org.jarcem.recognizer.util.props.S4Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements some basic scorer functionality, including a simple default
 * acoustic scoring implementation which scores within the current thread, that
 * can be changed by overriding the {@link #doScoring} method.
 *
 * <p>
 * Note that all scores are maintained in LogMath log base.
 *
 * @author Holger Brandl
 */
public class SimpleAcousticScorer extends ConfigurableAdapter implements AcousticScorer {

    /**
     * Property the defines the frontend to retrieve features from for scoring
     */
    @S4Component(type = BaseDataProcessor.class)
    public final static String FEATURE_FRONTEND = "frontend";
    /**
     * An optional post-processor for computed scores that will normalize
     * scores. If not set, no normalization will applied and the token scores
     * will be returned unchanged.
     */
    @S4Component(type = ScoreNormalizer.class, mandatory = false)
    public final static String SCORE_NORMALIZER = "scoreNormalizer";
    protected BaseDataProcessor frontEnd;
    protected ScoreNormalizer scoreNormalizer;

    private LinkedList<Data> storedData;
    private boolean seenEnd = false;

    /**
     * @param frontEnd        the frontend to retrieve features from for scoring
     * @param scoreNormalizer optional post-processor for computed scores that will
     *                        normalize scores. If not set, no normalization will applied
     *                        and the token scores will be returned unchanged.
     */
    public SimpleAcousticScorer(BaseDataProcessor frontEnd, ScoreNormalizer scoreNormalizer) {
        initLogger();
        this.frontEnd = frontEnd;
        this.scoreNormalizer = scoreNormalizer;
        storedData = new LinkedList<Data>();
    }

    public SimpleAcousticScorer() {
    }

    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
        this.frontEnd = (BaseDataProcessor) ps.getComponent(FEATURE_FRONTEND);
        this.scoreNormalizer = (ScoreNormalizer) ps.getComponent(SCORE_NORMALIZER);
        storedData = new LinkedList<Data>();
    }

    /**
     * Scores the given set of states.
     *
     * @param scoreableList A list containing scoreable objects to be scored
     * @return The best scoring scoreable, or <code>null</code> if there are no
     * more features to score
     */
    public Data calculateScores(List<? extends Scoreable> scoreableList) {
        Data data;
        if (storedData.isEmpty()) {
            while ((data = getNextData()) instanceof Signal) {
                if (data instanceof SpeechEndSignal) {
                    seenEnd = true;
                    break;
                }
                if (data instanceof DataEndSignal) {
                    if (seenEnd)
                        return null;
                    else
                        break;
                }
            }
            if (data == null)
                return null;
        } else {
            data = storedData.poll();
        }

        return calculateScoresForData(scoreableList, data);
    }

    public Data calculateScoresAndStoreData(List<? extends Scoreable> scoreableList) {
        Data data;
        while ((data = getNextData()) instanceof Signal) {
            if (data instanceof SpeechEndSignal) {
                seenEnd = true;
                break;
            }
            if (data instanceof DataEndSignal) {
                if (seenEnd)
                    return null;
                else
                    break;
            }
        }
        if (data == null)
            return null;

        storedData.add(data);

        return calculateScoresForData(scoreableList, data);
    }

    protected Data calculateScoresForData(List<? extends Scoreable> scoreableList, Data data) {
        if (data instanceof SpeechEndSignal || data instanceof DataEndSignal) {
            return data;
        }

        if (scoreableList.isEmpty())
            return null;

        // convert the data to FloatData if not yet done
        if (data instanceof DoubleData)
            data = DataUtil.DoubleData2FloatData((DoubleData) data);

        Scoreable bestToken = doScoring(scoreableList, data);

        // apply optional score normalization
        if (scoreNormalizer != null && bestToken instanceof Token)
            bestToken = scoreNormalizer.normalize(scoreableList, bestToken);

        return bestToken;
    }

    protected Data getNextData() {
        Data data = frontEnd.getData();
        return data;
    }

    public void startRecognition() {
        storedData.clear();
    }

    public void stopRecognition() {
        // nothing needs to be done here
    }

    /**
     * Scores a a list of <code>Scoreable</code>s given a <code>Data</code>
     * -object.
     *
     * @param scoreableList The list of Scoreables to be scored
     * @param data          The <code>Data</code>-object to be used for scoring.
     * @param <T>           type for scorables
     * @return the best scoring <code>Scoreable</code> or <code>null</code> if
     * the list of scoreables was empty.
     */
    protected <T extends Scoreable> T doScoring(List<T> scoreableList, Data data) {

        T best = null;
        float bestScore = -Float.MAX_VALUE;

        for (T item : scoreableList) {
            item.calculateScore(data);
            if (item.getScore() > bestScore) {
                bestScore = item.getScore();
                best = item;
            }
        }
        return best;
    }

    // Even if we don't do any meaningful allocation here, we implement the
    // methods because most extending scorers do need them either.

    public void allocate() {
    }

    public void deallocate() {
    }

}