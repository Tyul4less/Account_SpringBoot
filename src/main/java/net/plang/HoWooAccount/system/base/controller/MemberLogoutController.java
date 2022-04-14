package net.plang.HoWooAccount.system.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Controller
public class MemberLogoutController  {

    @GetMapping("/logout")
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView("loginForm");
        HttpSession session = request.getSession();
        session.invalidate();

        return modelAndView;
    }

}
