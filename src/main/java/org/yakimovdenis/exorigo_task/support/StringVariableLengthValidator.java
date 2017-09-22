package org.yakimovdenis.exorigo_task.support;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class StringVariableLengthValidator implements IValidator<String> {
    private static final String MINIMAL_TEXT = "The value must be longer than ${placeholder} chars.";
    private static final String MAXIMAL_TEXT = "The value must be shorter than ${placeholder} chars.";
    private int minimalLength;
    private int maximalLength;

    public StringVariableLengthValidator(int minimalLength, int maximalLength) {
        this.minimalLength = minimalLength;
        this.maximalLength = maximalLength;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String chosenUserName = validatable.getValue();

        if (chosenUserName.length()<minimalLength){
            ValidationError error = new ValidationError(this);
            error.setVariable("variableLength",
                    MINIMAL_TEXT.replace("${placeholder}",String.valueOf(minimalLength)));
            validatable.error(error);
        } else if (chosenUserName.length()>maximalLength){
            ValidationError error = new ValidationError(this);
            error.setVariable("variableLength",
                    MAXIMAL_TEXT.replace("${placeholder}",String.valueOf(maximalLength)));
            validatable.error(error);
        }
    }
}