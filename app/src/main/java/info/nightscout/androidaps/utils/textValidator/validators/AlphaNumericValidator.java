package info.nightscout.androidaps.utils.textValidator.validators;


public class AlphaNumericValidator extends RegexpValidator {
    public AlphaNumericValidator(String message) {
        super(message, "[a-zA-Z0-9\u00C0-\u00FF \\./-\\?]*");
    }

}
