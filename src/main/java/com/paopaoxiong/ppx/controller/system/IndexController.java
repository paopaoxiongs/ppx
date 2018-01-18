package com.paopaoxiong.ppx.controller.system;

import com.paopaoxiong.ppx.annotation.RetryProcess;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/sys")
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping("/restry")
    @RetryProcess(value = 3)
    public ResultInfo getRetryInfo(){
        ResultInfo resultInfo = new ResultInfo();
        userService.retry(null);
        return resultInfo;
    }
}
