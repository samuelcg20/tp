package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for the guided tour window
 */
public class GuidedTourWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(GuidedTourWindow.class);
    private static final String FXML = "GuidedTourWindow.fxml";

    private int currentStep = 0;
    private final String[] stepTitles = {"Menu Bar", "Command Box", "Result Display", "Member/Event List Display",
        "StatusBar"
    };

    private final String[] stepDescriptions = {
        "Menu Bar has File and Help tabs. File tab has the option to exit the application. "
            + "Help tab has the option to open the Help Window and serves as a GUI "
            + "alternative to the command box.",

        "Command Box allows you to enter commands. "
            + "See our User Guide for more information on the commands.",

        "See the results of your commands here. "
            + "Any success messages or error messages will appear in this area.",

        "Detailed information of the selected member appears here, including phone number, "
            + "email, year of study, and role. "
            + "When the [list event] command is executed, detailed information of the selected event "
            + "appears here, including date, time, and venue.",

        "A UI for the status bar that is displayed at the footer of the application."
    };



    @FXML
    private ImageView screenshotView;

    @FXML
    private Label stepTitleLabel;

    @FXML
    private Label stepDescriptionLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label progressLabel;

    @FXML
    private Pane overlayPane;

    @FXML
    private Rectangle highlightBox1;

    @FXML
    private Rectangle highlightBox2;

    @FXML
    private Rectangle highlightBox3;

    @FXML
    private Rectangle highlightBox4;

    @FXML
    private Rectangle highlightBox5;

    /**
     * Creates a new GuidedTourWindow.
     *
     * @param root Stage to use as the root of the GuidedTourWindow.
     */
    public GuidedTourWindow(Stage root) {
        super(FXML, root);
        loadScreenshot();
        updateTourStep();
    }

    /**
     * Creates a new GuidedTourWindow.
     */
    public GuidedTourWindow() {
        this(new Stage());
    }

    /**
     * Loads the screenshot image for the guided tour.
     */
    private void loadScreenshot() {
        try {
            Image screenshot = new Image("/images/address_book_screenshot.png");
            screenshotView.setImage(screenshot);
            // Preserve aspect ratio
            screenshotView.setPreserveRatio(true);
            screenshotView.setFitWidth(800);
        } catch (Exception e) {
            logger.warning("Failed to load guided tour screenshot: " + e.getMessage());
            stepDescriptionLabel.setText("Failed to load tour image. "
                    + "Please ensure address_book_screenshot.png exists in the images folder.");
        }
    }

    /**
     * Updates the tour display based on the current step.
     */
    private void updateTourStep() {
        // Update labels
        stepTitleLabel.setText(stepTitles[currentStep]);
        stepDescriptionLabel.setText(stepDescriptions[currentStep]);
        progressLabel.setText(String.format("Step %d of %d", currentStep + 1, stepTitles.length));

        // Update button states
        backButton.setDisable(currentStep == 0);
        nextButton.setText(currentStep == stepTitles.length - 1 ? "Finish" : "Next");

        // Update highlight boxes - show only the current step's box
        hideAllHighlightBoxes();

        switch (currentStep) {
        case 0:
            highlightBox1.setVisible(true);
            break;
        case 1:
            highlightBox2.setVisible(true);
            break;
        case 2:
            highlightBox3.setVisible(true);
            break;
        case 3:
            highlightBox4.setVisible(true);
            break;
        case 4:
            highlightBox5.setVisible(true);
            break;
        default:
            break;
        }
    }

    /**
     * Hides all highlight boxes.
     */
    private void hideAllHighlightBoxes() {
        highlightBox1.setVisible(false);
        highlightBox2.setVisible(false);
        highlightBox3.setVisible(false);
        highlightBox4.setVisible(false);
        highlightBox5.setVisible(false);
    }

    /**
     * Handles the Next button click.
     */
    @FXML
    private void handleNext() {
        if (currentStep < stepTitles.length - 1) {
            currentStep++;
            updateTourStep();
        } else {
            // Close the window when finished
            hide();
        }
    }

    /**
     * Handles the Back button click.
     */
    @FXML
    private void handleBack() {
        if (currentStep > 0) {
            currentStep--;
            updateTourStep();
        }
    }

    /**
     * Shows the guided tour window.
     */
    public void show() {
        logger.fine("Showing guided tour window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the guided tour window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the guided tour window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the guided tour window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Resets the tour to the first step.
     */
    public void resetTour() {
        currentStep = 0;
        updateTourStep();
    }
}
