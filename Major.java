package eecs1021;

import org.firmata4j.I2CDevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Timer;

public class Major {
    public static void main(String[] args) throws IOException, InterruptedException {

        String USBPort = "COM3";
        var device = new FirmataDevice(USBPort);
        device.start();
        device.ensureInitializationIsDone();
        var potObject = device.getPin(16); //a2
        potObject.setMode(Pin.Mode.ANALOG);
        var buttonObject = device.getPin(6); //d6
        buttonObject.setMode(Pin.Mode.INPUT);

        I2CDevice i2cObject = device.getI2CDevice((byte) 0x3C); // Use 0x3C for the Grove OLED
        SSD1306 myOledDisplay = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64); // 128x64 OLED SSD1515
        myOledDisplay.init();

        // 1
        String[] media = new String[]{
                "netflix.ca",
                "youtube.com",
                "open.spotify.com",
                "primevideo.com"
        };

        // 2
        String[] school = new String[]{
                "eclass.yorku.ca",
                "gmail.com",
                "cengage.com/dashboard",
                "open.spotify.com",
                "desmos.com/scientific",
                "todoist.com/app"
        };

        // 3
        String[] misc = new String[]{
                "cbc.ca",
                "desmos.com",
                "drive.google.com",
                "calendar.google.com"
        };
        String[][] options = {media, school, misc};

        device.addEventListener(new buttonListener(buttonObject, potObject, options));
        var task = new potListener(potObject, myOledDisplay, options);
        new Timer().schedule(task,0, 1000);

    }
    public static void open_in_chrome(String[] args) throws IOException,
            AWTException, InterruptedException {

        open_chrome();
        Robot robot = new Robot();
        var terms = convert_terms(args);

        for (int i = 0; i < terms.length; i++) {
            for (int j = 0; j < terms[i].length; j++) {
                robot.keyPress(terms[i][j]);
                Thread.sleep(10);
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            Thread.sleep(1000);
            new_tab(robot);
        }
    }
    public static byte[][] convert_terms(String[] terms) {
        byte[][] terms_a = new byte[terms.length][];
        for (int i = 0; i < terms.length; i++){
            terms[i] = terms[i].toUpperCase();
            terms_a[i] = terms[i].getBytes(StandardCharsets.US_ASCII);
        }return terms_a;
    }

    public static void open_chrome() throws IOException{
        String command = "start chrome";
        Runtime.getRuntime().exec("cmd /c start cmd.exe /C " + command);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void new_tab(Robot robot) throws InterruptedException {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);
    }
}

