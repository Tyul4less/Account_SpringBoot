package net.plang.HoWooAccount.system.authority.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.plang.HoWooAccount.system.authority.serviceFacade.AuthorityServiceFacade;
import net.plang.HoWooAccount.system.authority.to.AuthorityEmpBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityServiceFacade authorityServiceFacade;

    @GetMapping("/findAuthorityEmp")
    public ArrayList<AuthorityEmpBean> findAuthorityEmp(@RequestParam String deptCode) {
        return authorityServiceFacade.findAuthorityEmp(deptCode);
    }

    @PutMapping("/editAuthority")
    public void editAuthority(@RequestParam String authority, @RequestParam String dept) {

        Gson gson = new Gson();
        ArrayList<AuthorityEmpBean> authorityEmpBean =
                gson.fromJson(authority, new TypeToken<ArrayList<AuthorityEmpBean>>() {
                }.getType());
        authorityServiceFacade.updateAuthority(authorityEmpBean, dept);

    }

}
