package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the student record data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StudentRecord studentRecord;
    private final UserPrefs userPrefs;
    private FilteredStudents filteredStudents;

    /**
     * Initializes a ModelManager with the given studentRecord and userPrefs.
     */
    public ModelManager(ReadOnlyStudentRecord studentRecord, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(studentRecord, userPrefs);

        logger.fine("Initializing with student record: " + studentRecord + " and user prefs " + userPrefs);

        this.studentRecord = new StudentRecord(studentRecord);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredStudents = new FilteredStudents(new FilteredList<>(this.studentRecord.getStudentList()));
    }

    public ModelManager() {
        this(new StudentRecord(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getStudentRecordFilePath() {
        return userPrefs.getStudentRecordFilePath();
    }

    @Override
    public void setStudentRecordFilePath(Path studentRecordFilePath) {
        requireNonNull(studentRecordFilePath);
        userPrefs.setStudentRecordFilePath(studentRecordFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setStudentRecord(ReadOnlyStudentRecord studentRecord) {
        this.studentRecord.resetData(studentRecord);
    }

    @Override
    public ReadOnlyStudentRecord getStudentRecord() {
        return studentRecord;
    }

    @Override
    public boolean hasStudent(Student person) {
        requireNonNull(person);
        return studentRecord.hasStudent(person);
    }

    @Override
    public void deleteStudent(Student target) {
        studentRecord.removePerson(target);
    }

    @Override
    public void addStudent(Student person) {
        studentRecord.addStudent(person);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        studentRecord.setStudent(target, editedStudent);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedStudentRecord}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return this.filteredStudents.getFilteredStudentList();
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        this.filteredStudents.updateFilteredStudentList(predicate);
    }

    @Override
    public void toggleStudentListInfoConcise() {
        this.filteredStudents.toggleConciseInfo();
    }

    @Override
    public boolean isStudentListInfoConcise() {
        return this.filteredStudents.hasConciseInfo();
    }

    @Override
    public FilteredStudents getFilteredStudents() {
        return this.filteredStudents;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return studentRecord.equals(other.studentRecord)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

}
