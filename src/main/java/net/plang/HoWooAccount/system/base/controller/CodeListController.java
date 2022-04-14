package net.plang.HoWooAccount.system.base.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.plang.HoWooAccount.system.base.serviceFacade.BaseServiceFacade;
import net.plang.HoWooAccount.system.base.to.CodeBean;
import net.plang.HoWooAccount.system.base.to.DetailCodeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/system/base")
public class CodeListController {
    @Autowired
    private BaseServiceFacade baseServiceFacade;

    @GetMapping("/getDetailCodeList")
    public ArrayList<DetailCodeBean> getDetailCodeList(@RequestParam String divisionCodeNo, @RequestParam String detailCodeName) {
        HashMap<String, String> param = new HashMap<>();
        param.put("divisionCodeNo", divisionCodeNo);
        if (detailCodeName != null)
            param.put("detailCodeName", detailCodeName);
        return baseServiceFacade.getDetailCodeList(param);

    }

    @GetMapping("/findCodeList")
    public ArrayList<CodeBean> findCodeList() {
        return baseServiceFacade.findCodeList();
    }

    @GetMapping("/batchCodeProcess")
    public void batchCodeProcess(@RequestParam String batchList, @RequestParam String batchList2) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<CodeBean> codeList = mapper.readValue(batchList, new TypeReference<ArrayList<CodeBean>>() {
            });
            ArrayList<DetailCodeBean> codeList2 = mapper.readValue(batchList2,
                    new TypeReference<ArrayList<DetailCodeBean>>() {
                    });
            baseServiceFacade.batchCodeProcess(codeList, codeList2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
