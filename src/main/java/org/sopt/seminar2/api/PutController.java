package org.sopt.seminar2.api;

import org.sopt.seminar2.model.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class PutController {

    @RestController
    @RequestMapping("put")
    public class PostController {

        @PutMapping("")
        public String putUser(@RequestBody final User user){
            return user.getName();
        }
    }

}
