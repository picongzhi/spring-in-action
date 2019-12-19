package com.pcz.spittr.web;

import com.pcz.spittr.Spittle;
import com.pcz.spittr.data.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author picongzhi
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {
    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String spittles(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max,
                           @RequestParam(value = "count", defaultValue = "20") int count,
                           Model model) {
        List<Spittle> spittleList = spittleRepository.findSpittles(max, count);
        model.addAttribute(spittleList);

        return "spittles";
    }

    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
    public String spittle(@PathVariable("spittleId") long spittleId,
                          Model model) {
        Spittle spittle = spittleRepository.findOne(spittleId);
        if (spittle == null) {
            throw new SpittleNotFoundException();
        }
        model.addAttribute(spittle);

        return "spittle";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveSpittle(SpittleForm form, Model model) {
        try {
            spittleRepository.save(new Spittle(null, form.getMessage(),
                    new Date(), form.getLongitude(), form.getLatitude()));
            return "redirect:/spittles";
        } catch (DuplicateSpittleException e) {
            return "error/duplicate";
        }
    }

    @ExceptionHandler(DuplicateSpittleException.class)
    public String handleDuplicate() {
        return "error/duplicate";
    }
}
