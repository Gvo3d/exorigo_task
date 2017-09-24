package org.yakimovdenis.exorigo_task.components;

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;

public class StringRegexValidator extends CompoundValidator<String> {
    private static final long serialVersionUID = 1L;

    public StringRegexValidator(String regex) {
        add(new PatternValidator(regex));
    }
}
