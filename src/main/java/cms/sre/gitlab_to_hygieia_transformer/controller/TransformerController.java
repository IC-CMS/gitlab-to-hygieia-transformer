package cms.sre.gitlab_to_hygieia_transformer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/gitlab-to-hygieia-transformer")
public class TransformerController {

    @RequestMapping(method = RequestMethod.GET)
    public String get(){
        return "{status: 'ok'}";
    }
}
