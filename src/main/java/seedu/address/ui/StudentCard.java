package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * A UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label studentName;
    @FXML
    private Label id;
    @FXML
    private Label studentId;
    @FXML
    private Label parentName;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code StudentCard} with the given {@code Student} and index to display.
     */
    public StudentCard(Student student, int displayedIndex, boolean conciseInfo) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        studentId.setText(student.getId().toString());
        studentName.setText(student.getStudentName().fullName);
        parentName.setText(student.getParentName().fullName);
        phone.setText(student.getPhone().value);
        address.setText(student.getAddress().value);
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (conciseInfo) {
            showConciseInfo();
        } else {
            showAllInfo();
        }
    }

    /**
     * Updates PersonCard to show all info
     */
    public void showAllInfo() {
        parentName.setManaged(true);
        parentName.setVisible(true);
        phone.setManaged(true);
        phone.setVisible(true);
        address.setManaged(true);
        address.setVisible(true);
        tags.setManaged(true);
        tags.setVisible(true);
    }

    /**
     * Updates PersonCard to only show student's name and ID
     */
    public void showConciseInfo() {
        parentName.setManaged(false);
        parentName.setVisible(false);
        phone.setManaged(false);
        phone.setVisible(false);
        address.setManaged(false);
        address.setVisible(false);
        tags.setManaged(false);
        tags.setVisible(false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        StudentCard card = (StudentCard) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}
