package org.yakimovdenis.exorigo_task.support;

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class StringRegexValidator extends CompoundValidator<String> {
    private static final long serialVersionUID = 1L;

    public StringRegexValidator(String regex) {
        add(new PatternValidator(regex));
    }
}
