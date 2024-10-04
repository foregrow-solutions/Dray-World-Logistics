package com.loonds.acl.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EchoController {

    @RequestMapping(value="/echo",method= RequestMethod.GET)
    public ModelAndView echo() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("layouts/default");
        return vi;
    }


}
