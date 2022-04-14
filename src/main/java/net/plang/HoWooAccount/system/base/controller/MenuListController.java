package net.plang.HoWooAccount.system.base.controller;

import net.plang.HoWooAccount.system.base.serviceFacade.BaseServiceFacade;
import net.plang.HoWooAccount.system.base.to.MenuBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MenuListController {  // 하나의 컨트롤러에서 여러개의 요청처리 지원
    @Autowired
    private BaseServiceFacade baseServiceFacade;

    @GetMapping("/findUserMenuList")
    public ArrayList<MenuBean> findUserMenuList(@RequestParam String empCode) {
        return baseServiceFacade.findUserMenuList(empCode);
    }
}
