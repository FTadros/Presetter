package eecs1021;

import org.firmata4j.IODeviceEventListener;
import org.firmata4j.IOEvent;
import org.firmata4j.Pin;

import java.awt.*;
import java.io.IOException;

public class buttonListener implements
        IODeviceEventListener {
    private final Pin buttonPin;
    private final Pin potPin;
    private final String[][] options;

    buttonListener(Pin buttonPin, Pin potPin, String[][] options) {
        this.buttonPin = buttonPin;
        this.potPin = potPin;
        this.options = options;
    }

    @Override
    public void onPinChange(IOEvent event) {
        if (event.getPin().getIndex() != buttonPin.getIndex()) {
            return;
        }
        if (buttonPin.getValue() == 1) {
            if ((potPin.getValue() <= 307)) {
                choice(0);
            } else if (potPin.getValue() >= 615) {
                choice(2);
            } else {
                choice(1);
            }
        }
    }

    public void choice(int choice){
        try {
            Major.open_in_chrome(this.options[choice]);
        } catch (IOException | InterruptedException | AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceive(IOEvent ioEvent, String s) {}
    @Override
    public void onStart(IOEvent ioEvent) {}
    @Override
    public void onStop(IOEvent ioEvent) {}
}