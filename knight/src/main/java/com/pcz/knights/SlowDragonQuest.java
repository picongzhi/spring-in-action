package com.pcz.knights;

import java.io.PrintStream;

/**
 * @author picongzhi
 */
public class SlowDragonQuest implements Quest {
    private PrintStream stream;

    public SlowDragonQuest(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void embark() {
        stream.println("Embarking on quest to slay the dragon!");
    }
}
