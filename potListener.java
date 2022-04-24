package eecs1021;

import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;

import java.util.TimerTask;

public class potListener extends TimerTask {
    private final Pin potPin;
    private final SSD1306 screen;
    private final String[][] options;

    //constructor
    potListener(Pin potPin, SSD1306 screen, String[][] options){
        this.potPin = potPin;
        this.screen = screen;
        this.options = options;
    }
    @Override
    public void run() {
        if ((potPin.getValue() <= 307)){
            this.screen.getCanvas().drawString(0,20, "You selected option 1");
        }
        else if (potPin.getValue() >= 615){
            this.screen.getCanvas().drawString(0,20, "You selected option 3");
        }
        else{
            this.screen.getCanvas().drawString(0,20, "You selected option 2");
        }
        this.screen.getCanvas().drawString(0,40, "Press the button to confirm");
        this.screen.display();
    }
}