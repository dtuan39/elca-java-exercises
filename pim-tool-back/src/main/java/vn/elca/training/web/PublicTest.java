package vn.elca.training.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicTest {

    @RequestMapping("/")
    public String test() {
        return "Hello World";
    }
}
