package com.pcz.soundsystem;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class EnvironmentInjectionTest {
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = EnvironmentConfig.class)
    public static class InjectFromProperties {
        @Autowired
        private BlankDisc blankDisc;

        @Test
        public void assertBlankDiscProperties() {
            assertEquals("The Beatles", blankDisc.getArtist());
            assertEquals("Sgt. Peppers Lonely Hearts Club Band", blankDisc.getTitle());
        }
    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = EnvironmentConfigWithDefaults.class)
    public static class InjectFromPropertiesWithDefaultValues {
        @Autowired
        private BlankDisc blankDisc;

        @Test
        public void assertBlankDiscProperties() {
            assertEquals("U2", blankDisc.getArtist());
            assertEquals("Rattle and Hum", blankDisc.getTitle());
        }
    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration("classpath:placeholder-config.xml")
    public static class InjectFromPropertiesXMLConfig {
        @Autowired
        private BlankDisc blankDisc;

        @Test
        public void assertBlankDiscProperties() {
            assertEquals("The Beatles", blankDisc.getArtist());
            assertEquals("Sgt. Peppers Lonely Hearts Club Band", blankDisc.getTitle());
        }
    }
}
