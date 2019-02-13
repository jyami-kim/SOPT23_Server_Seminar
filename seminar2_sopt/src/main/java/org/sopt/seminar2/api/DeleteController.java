package org.sopt.seminar2.api;

import org.sopt.seminar2.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Delete")
public class DeleteController {
    @DeleteMapping("")
    public String deleteUser(@RequestBody final User user){
        return "delete Success";
    }
}
