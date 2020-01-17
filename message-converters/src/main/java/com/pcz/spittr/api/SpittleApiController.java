package com.pcz.spittr.api;

import com.pcz.spittr.Spittle;
import com.pcz.spittr.data.SpittleNotFoundException;
import com.pcz.spittr.data.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author picongzhi
 */
@RestController
@RequestMapping("/spittles")
public class SpittleApiController {
    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleApiController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Spittle> spittles(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max,
                                  @RequestParam(value = "count", defaultValue = "20") int count) {
        return spittleRepository.findSpittles(max, count);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Spittle> saveSpittle(@RequestBody Spittle spittle, UriComponentsBuilder uriComponentsBuilder) {
        Spittle saved = spittleRepository.save(spittle);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI uri = uriComponentsBuilder.path("/spittles/").path(String.valueOf(saved.getId())).build().toUri();
        httpHeaders.setLocation(uri);

        return new ResponseEntity<Spittle>(saved, httpHeaders, HttpStatus.CREATED);
    }

    @ExceptionHandler(SpittleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Error spittleNotFound(SpittleNotFoundException e) {
        long spittleId = e.getSpitterId();
        return new Error(4, "Spittle [" + spittleId + "] not found");
    }
}
