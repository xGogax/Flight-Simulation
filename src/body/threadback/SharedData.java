package body.threadback;

import body.let.Let;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    public volatile int hours = 0;
    public volatile int minutes = 0;

    public volatile boolean wasPaused = false;

    public volatile boolean paused = false;
    public volatile boolean running = false;

    public volatile List<Let> activeFlights = new ArrayList<>();
}
