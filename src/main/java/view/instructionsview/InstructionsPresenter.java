package main.java.view.instructionsview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class InstructionsPresenter {
        private InstructionsView view;

        public InstructionsPresenter(InstructionsView view) {
            this.view = view;
            updateView();
        }

        private void updateView() {
            String instructionsText = loadInstructionsText();
            view.setInstructionsText(instructionsText);
        }

        private String loadInstructionsText() {
            InputStream inputStream = getClass().getResourceAsStream("/txtfiles/instructions.txt");

            if (inputStream == null) {
                return "Instructions file not found.";
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                return "Error reading instructions.";
            }
        }
    }
