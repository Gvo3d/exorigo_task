package org.yakimovdenis.exorigo_task.support;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

class ExactErrorLevelFilter implements IFeedbackMessageFilter {
    private int errorLevel;

    public ExactErrorLevelFilter(int errorLevel){
        this.errorLevel = errorLevel;
    }

    @Override
    public boolean accept(FeedbackMessage message) {
        return message.getLevel() == errorLevel;
    }
}

