/**
 * Copyright 1998-2009 Sun Microsystems, Inc.
 * <p>
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */
package org.jarcem.recognizer.jsgf.rule;

public class JSGFRuleCount extends JSGFRule {
    public static final int OPTIONAL = 2;
    public static final int ONCE_OR_MORE = 3;
    public static final int ZERO_OR_MORE = 4;
    protected JSGFRule rule;
    protected int count;

    public JSGFRuleCount() {
        setRule(null);
        setCount(OPTIONAL);
    }

    public JSGFRuleCount(JSGFRule rule, int count) {
        setRule(rule);
        setCount(count);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if ((count != OPTIONAL) && (count != ZERO_OR_MORE)
                && (count != ONCE_OR_MORE)) {
            return;
        }
        this.count = count;
    }

    public JSGFRule getRule() {
        return rule;
    }

    public void setRule(JSGFRule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        if (count == OPTIONAL) {
            return '[' + rule.toString() + ']';
        }
        String str = null;

        if ((rule instanceof JSGFRuleToken) || (rule instanceof JSGFRuleName))
            str = rule.toString();
        else {
            str = '(' + rule.toString() + ')';
        }

        if (count == ZERO_OR_MORE)
            return str + " *";
        if (count == ONCE_OR_MORE) {
            return str + " +";
        }
        return str + "???";
    }
}
