package com.happyheng;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Created by happyheng on 2017/6/13.
 */
@RestController
public class TestController {

    @RequestMapping("/testController")
    public String test(){
        return "hello mq";
    }

}
