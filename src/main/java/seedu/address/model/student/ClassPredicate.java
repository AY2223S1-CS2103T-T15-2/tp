package seedu.address.model.student;

import java.util.function.Predicate;

/**
 * Tests that a {@code Student}'s {@code Name} matches any of the keywords given.
 */
public class ClassPredicate implements Predicate<Student> {
    private Class className;

    public ClassPredicate(Class className) {
        this.className = className;
    }

    @Override
    public boolean test(Student student) {
        return student.getClassName().equals(this.className);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassPredicate // instanceof handles nulls
                && className.equals(((ClassPredicate) other).className)); // state check
    }

}
