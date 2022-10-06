package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's id in the record.
 * Id is the last 4 digits of the NRIC (e.g. 123A).
 * Guarantees: immutable; is valid as declared in
 */
public class Id {

    public static final String MESSAGE_CONSTRAINTS = "Id should only contain 3 digits and 1 character";
    public static final String VALIDATION_REGEX = "\\d{3}[a-zA-Z]";
    public final String value;

    /**
     * Constructs a {@code Id}.
     *
     * @param id A valid id.
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        value = id.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public boolean isValidId(String test) {
        return test.length() == 4 && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Id // instanceof handles nulls
                && value.equals(((Id) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
