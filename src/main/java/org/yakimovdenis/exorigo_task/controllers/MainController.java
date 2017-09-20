package org.yakimovdenis.exorigo_task.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

/**
 * Created by Gvozd on 30.12.2016.
 */
@RestController
public class MainController {

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Timestamp tms = new Timestamp(System.currentTimeMillis());
        System.out.println("tms="+tms);
        mav.addObject("tms", tms);
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value="/editor")
    ModelAndView editor(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("editor");
        return mav;
    }
}
