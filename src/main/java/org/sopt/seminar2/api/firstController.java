package org.sopt.seminar2.api;

import org.springframework.web.bind.annotation.*;

@RestController

public class firstController {
    @GetMapping("")
    public String hello(){
        return "hello world!";
    }
//    @RequestMapping(method = RequestMethod.GET, value = "")
//    public String hello2(){
//        return "Nice to Meet you!";
//    }
    @GetMapping("1")
    public String hello2(){
        return "hello world! (In mapping 1)";
    }
    @GetMapping("/get1")
    public String get1(){
        return "1";
    }
    @GetMapping("/get1/김민정")
    public String get2(){
        return "김민정";
    }
    @GetMapping("/name/{name}")
    public String getName(@PathVariable(value="name") final String name){
        return name;
    }

    @GetMapping("/part")
    public String getPart(@RequestParam(value="part",defaultValue="")final String part) {
        return part;
    }

    @GetMapping("/info")
    public String getPart2(
            @RequestParam(value="name") final String name,
            @RequestParam(value="type", defaultValue = "yb") final String type) {
        System.out.println(name + "이고 " + type + "입니다.");
        return name + "이고 " + type + "입니다.";
    }

    @GetMapping("/num")
    public int number(@RequestParam(value ="num") final int[] num) {
        int sum = 0;
        for(int i : num) {
            sum += i;
        }
        return sum;
    }

}
