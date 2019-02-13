package org.sopt.seminar2.api;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("Get")
public class GetController {
    @GetMapping("/noVar")
    public String hello(){
        return "hello world!";
    }

    @GetMapping("/pathVar/{pathVar}")
    public String getName(@PathVariable(value="pathVar") final String pathVar){
        return pathVar;
    }

    @GetMapping("/queryVar")
    public String getPart(@RequestParam(value="req",defaultValue="")final String req) {
        return req;
    }
}
