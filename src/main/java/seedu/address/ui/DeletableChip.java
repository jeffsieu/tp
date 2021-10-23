package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class DeletableChip extends UiPart<Region> {
    @FXML
    private Label nameLabel;

    @FXML
    private Button removeButton;

    /**
     * Creates a chip UI element representing a selected task filter
     * @param name The text to display on the chip
     * @param onDelete The callable that is run when the close button is pressed
     */
    public DeletableChip(String name, Runnable onDelete) {
        super("DeletableChip.fxml");
        nameLabel.setText(name);
        removeButton.setOnAction(actionEvent -> onDelete.run());
    }
}
