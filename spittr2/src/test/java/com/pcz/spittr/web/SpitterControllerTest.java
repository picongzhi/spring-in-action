package com.pcz.spittr.web;

import com.pcz.spittr.Spitter;
import com.pcz.spittr.data.SpitterRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SpitterControllerTest {
    @Test
    public void shouldShowRegistration() throws Exception {
        SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
        SpitterController controller = new SpitterController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/spitter/register")).
                andExpect(MockMvcResultMatchers.view().name("registerForm"));
    }

    @Test
    @Ignore
    public void shouldProcessRegistration() throws Exception {
        SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
        Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
        Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
        Mockito.when(mockRepository.save(unsaved)).thenReturn(saved);

        SpitterController controller = new SpitterController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.
                fileUpload("/spitter/register").
                param("firstName", "Jack").
                param("lastName", "jbauer").
                param("username", "jbauer").
                param("password", "24hours").
                param("email", "jbauer@ctu.gov")).
                andExpect(MockMvcResultMatchers.redirectedUrl("/spitter/jbauer"));
        Mockito.verify(mockRepository, Mockito.atLeastOnce()).save(unsaved);
    }

    @Test
    @Ignore
    public void shouldFailValidationWithNoData() throws Exception {
        SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
        SpitterController controller = new SpitterController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/spitter/register")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("registerForm")).
                andExpect(MockMvcResultMatchers.model().errorCount(5)).
                andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors(
                        "spitter", "firstName", "lastName", "username", "password", "email"));
    }
}
