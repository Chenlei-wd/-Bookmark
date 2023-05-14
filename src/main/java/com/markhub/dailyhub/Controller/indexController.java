package com.markhub.dailyhub.Controller;

import com.markhub.dailyhub.base.dto.UserDto;
import com.markhub.dailyhub.base.lang.Consts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController extends BaseController{

    @RequestMapping(value = {"", "/"})
    public String index(){

        UserDto userDto = (UserDto) req.getSession().getAttribute(Consts.CURRENT_USER);
        req.setAttribute("username", userDto.getUsername());
        return "index";
    }
}
