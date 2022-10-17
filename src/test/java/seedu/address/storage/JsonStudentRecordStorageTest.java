package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.HOON;
import static seedu.address.testutil.TypicalStudents.IDA;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudentRecord;
import seedu.address.model.StudentRecord;

public class JsonStudentRecordStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyStudentRecord> readAddressBook(String filePath) throws Exception {
        return new JsonStudentRecordStorage(Paths.get(filePath))
                .readStudentRecord(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatStudentRecord.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidStudentRecord.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidStudentRecord.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        StudentRecord original = getTypicalStudentRecord();
        JsonStudentRecordStorage jsonAddressBookStorage = new JsonStudentRecordStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveStudentRecord(original, filePath);
        ReadOnlyStudentRecord readBack = jsonAddressBookStorage.readStudentRecord(filePath).get();
        assertEquals(original, new StudentRecord(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveStudentRecord(original, filePath);
        readBack = jsonAddressBookStorage.readStudentRecord(filePath).get();
        assertEquals(original, new StudentRecord(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        jsonAddressBookStorage.saveStudentRecord(original); // file path not specified
        readBack = jsonAddressBookStorage.readStudentRecord().get(); // file path not specified
        assertEquals(original, new StudentRecord(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyStudentRecord addressBook, String filePath) {
        try {
            new JsonStudentRecordStorage(Paths.get(filePath))
                    .saveStudentRecord(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new StudentRecord(), null));
    }
}
