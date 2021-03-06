/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */
package org.jarcem.recognizer.decoder.search;

import org.jarcem.recognizer.decoder.scorer.Scoreable;
import org.jarcem.recognizer.util.props.PropertyException;
import org.jarcem.recognizer.util.props.PropertySheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author plamere
 */
public class SortingActiveListFactory extends ActiveListFactory {
    /**
     * @param absoluteBeamWidth absolute pruning beam
     * @param relativeBeamWidth relative pruning beam
     */
    public SortingActiveListFactory(int absoluteBeamWidth,
                                    double relativeBeamWidth) {
        super(absoluteBeamWidth, relativeBeamWidth);
    }

    public SortingActiveListFactory() {

    }

    /*
     * (non-Javadoc)
     *
     * @see Configurable#newProperties(PropertySheet)
     */
    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
    }


    /*
     * (non-Javadoc)
     *
     * @see ActiveListFactory#newInstance()
     */
    @Override
    public ActiveList newInstance() {
        return new SortingActiveList(absoluteBeamWidth, logRelativeBeamWidth);
    }


    /**
     * An active list that tries to be simple and correct. This type of active list will be slow, but should exhibit
     * correct behavior. Faster versions of the ActiveList exist (HeapActiveList, TreeActiveList).
     * <p>
     * This class is not thread safe and should only be used by a single thread.
     * <p>
     * Note that all scores are maintained in the LogMath log base.
     */

    class SortingActiveList implements ActiveList {

        private final static int DEFAULT_SIZE = 1000;
        private final int absoluteBeamWidth;
        private final float logRelativeBeamWidth;
        private Token bestToken;
        // when the list is changed these things should be
        // changed/updated as well
        private List<Token> tokenList;


        /**
         * Creates an empty active list
         *
         * @param absoluteBeamWidth    beam for absolute pruning
         * @param logRelativeBeamWidth beam for relative pruning
         */
        public SortingActiveList(int absoluteBeamWidth, float logRelativeBeamWidth) {
            this.absoluteBeamWidth = absoluteBeamWidth;
            this.logRelativeBeamWidth = logRelativeBeamWidth;

            int initListSize = absoluteBeamWidth > 0 ? absoluteBeamWidth : DEFAULT_SIZE;
            this.tokenList = new ArrayList<Token>(initListSize);
        }


        /**
         * Adds the given token to the list
         *
         * @param token the token to add
         */
        public void add(Token token) {
            tokenList.add(token);
            if (bestToken == null || token.getScore() > bestToken.getScore()) {
                bestToken = token;
            }
        }

        /**
         * Purges excess members. Reduce the size of the token list to the absoluteBeamWidth
         *
         * @return a (possible new) active list
         */
        public ActiveList purge() {
            // if the absolute beam is zero, this means there
            // should be no constraint on the abs beam size at all
            // so we will only be relative beam pruning, which means
            // that we don't have to sort the list
            if (absoluteBeamWidth > 0 && tokenList.size() > absoluteBeamWidth) {
                Collections.sort(tokenList, Scoreable.COMPARATOR);
                tokenList = tokenList.subList(0, absoluteBeamWidth);
            }
            return this;
        }


        /**
         * gets the beam threshold best upon the best scoring token
         *
         * @return the beam threshold
         */
        public float getBeamThreshold() {
            return getBestScore() + logRelativeBeamWidth;
        }


        /**
         * gets the best score in the list
         *
         * @return the best score
         */
        public float getBestScore() {
            float bestScore = -Float.MAX_VALUE;
            if (bestToken != null) {
                bestScore = bestToken.getScore();
            }
            return bestScore;
        }

        /**
         * Gets the best scoring token for this active list
         *
         * @return the best scoring token
         */
        public Token getBestToken() {
            return bestToken;
        }

        /**
         * Sets the best scoring token for this active list
         *
         * @param token the best scoring token
         */
        public void setBestToken(Token token) {
            bestToken = token;
        }

        /**
         * Retrieves the iterator for this tree.
         *
         * @return the iterator for this token list
         */
        public Iterator<Token> iterator() {
            return tokenList.iterator();
        }


        /**
         * Gets the list of all tokens
         *
         * @return the list of tokens
         */
        public List<Token> getTokens() {
            return tokenList;
        }

        /**
         * Returns the number of tokens on this active list
         *
         * @return the size of the active list
         */
        public final int size() {
            return tokenList.size();
        }


        /* (non-Javadoc)
         * @see ActiveList#newInstance()
         */
        public ActiveList newInstance() {
            return SortingActiveListFactory.this.newInstance();
        }
    }
}
