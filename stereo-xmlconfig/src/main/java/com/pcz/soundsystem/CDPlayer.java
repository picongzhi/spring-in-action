package com.pcz.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author picongzhi
 */
public class CDPlayer implements MediaPlayer {
    private CompactDisc cd;

    @Autowired
    public CDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public void play() {
        cd.play();
    }
}
