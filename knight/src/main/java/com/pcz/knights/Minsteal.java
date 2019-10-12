package com.pcz.knights;

import java.io.PrintStream;

/**
 * @author picongzhi
 */
public class Minsteal {
    private PrintStream stream;

    public Minsteal(PrintStream stream) {
        this.stream = stream;
    }

    public void singBeforeQuest() {
        stream.println("Fa la la, the knight is so brave!");
    }

    public void singAfterQuest() {
        stream.println("Tee hee hee, the brave knight did embark on a quest!");
    }
}
