package com.pcz.spittr.web;

import com.pcz.spittr.Spitter;
import com.pcz.spittr.data.SpitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

/**
 * @author picongzhi
 */
@Controller
@RequestMapping("/spitter")
public class SpitterController {
    private SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute(new Spitter());
        return "registerForm";
    }

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public String processRegistration(@Valid SpitterForm spitterForm,
//                                      Errors errors) throws IOException {
//        if (errors.hasErrors()) {
//            return "registerForm";
//        }
//
//        Spitter spitter = spitterForm.toSpitter();
//        spitterRepository.save(spitter);
//        MultipartFile profilePicture = spitterForm.getProfilePicture();
//        profilePicture.transferTo(new File("/tmp/spittr/" + spitter.getUsername() + ".jpg"));
//
//        return "redirect:/spitter/" + spitter.getUsername();
//    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistration(@RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture,
                                      RedirectAttributes redirectAttributes,
                                      @Valid Spitter spitter,
                                      Errors errors) throws IOException {
        if (errors.hasErrors()) {
            return "registerForm";
        }

        spitterRepository.save(spitter);
        if (profilePicture != null) {
            profilePicture.transferTo(new File("/tmp/spittr/" + spitter.getUsername() + ".jpg"));
        }

//        redirectAttributes.addAttribute("username", spitter.getUsername());
        redirectAttributes.addFlashAttribute(spitter);

        return "redirect:/spitter/" + spitter.getUsername();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showSpitterProfile(@PathVariable() String username,
                                     Model model) {
        if (!model.containsAttribute("spitter")) {
            model.addAttribute(spitterRepository.findByUsername(username));
        }

        return "profile";
    }
}
