package com.pcz.soundsystem.properties;

import com.pcz.soundsystem.CompactDisc;
import com.pcz.soundsystem.MediaPlayer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author picongzhi
 */
public class CDPlayer implements MediaPlayer {
    private CompactDisc compactDisc;

    @Autowired
    public void setCompactDisc(CompactDisc compactDisc) {
        this.compactDisc = compactDisc;
    }

    @Override
    public void play() {
        compactDisc.play();
    }
}
