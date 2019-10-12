package com.pcz.knights;

/**
 * @author picongzhi
 */
public class DamselRescuingKight implements Knight {
    private RescureDamselQuest quest;

    public DamselRescuingKight() {
        this.quest = new RescureDamselQuest();
    }

    @Override
    public void embarkOnQuest() {
        quest.embark();
    }
}
