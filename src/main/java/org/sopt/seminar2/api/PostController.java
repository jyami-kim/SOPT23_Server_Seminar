package org.sopt.seminar2.api;


import org.sopt.seminar2.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post") //request mapping은 get이 기본값
//일단 method=get으로 설정하고, value="post"로 들어간다.
//먼저 URL /post로 설정
public class PostController {
    @PostMapping("") // url을  /로 설정  즉, PostUser를 실행하려면 /post/  를 하면 된다!
    public String postUser(@RequestBody final User user){
        return "name: " + user.getName() + "\npart: " + user.getPart();
    }
}
