package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilteredStudents;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyStudentRecord;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.StudentRecord;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class AddStudCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Student validPerson = new StudentBuilder().build();

        CommandResult commandResult = new AddStudCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddStudCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Student validPerson = new StudentBuilder().build();
        AddStudCommand addStudCommand = new AddStudCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(
                CommandException.class, AddStudCommand.MESSAGE_DUPLICATE_PERSON, () -> addStudCommand.execute(modelStub)
        );
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withStudentName("Alice").build();
        Student bob = new StudentBuilder().withStudentName("Bob").build();
        AddStudCommand addAliceCommand = new AddStudCommand(alice);
        AddStudCommand addBobCommand = new AddStudCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudCommand addAliceCommandCopy = new AddStudCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getStudentRecordFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudentRecordFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        public void addStudent(Student person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudentRecord(ReadOnlyStudentRecord newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyStudentRecord getStudentRecord() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudentListInfoConcise(boolean b) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isStudentListInfoConcise() {
            return false;
        }

        @Override
        public FilteredStudents getFilteredStudents() {
            return null;
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Student student;

        ModelStubWithPerson(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.hasSameNameOrId(student);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::hasSameNameOrId);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyStudentRecord getStudentRecord() {
            return new StudentRecord();
        }
    }

}
