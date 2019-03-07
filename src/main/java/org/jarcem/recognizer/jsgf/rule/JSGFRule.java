/**
 * Copyright 1998-2009 Sun Microsystems, Inc.
 * <p>
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */
package org.jarcem.recognizer.jsgf.rule;

public class JSGFRule {

    public String ruleName;
    public JSGFRule parent;

    @Override
    public String toString() {
        return ruleName;
    }
}
