package net.plang.HoWooAccount.account.slip.controller;

import com.google.gson.Gson;
import net.plang.HoWooAccount.account.slip.serviceFacade.SlipServiceFacade;
import net.plang.HoWooAccount.account.slip.to.JournalBean;
import net.plang.HoWooAccount.account.slip.to.JournalDetailBean;
import net.plang.HoWooAccount.account.slip.to.SlipBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class SlipController {

    @Autowired
    private SlipServiceFacade slipServiceFacade;

    @PostMapping("/updateSlip")
    public String updateSlip(@RequestParam String slipObj, @RequestParam String journalObj, @RequestParam String slipStatus) {

        Gson gson = new Gson();
        JSONObject slipJson = JSONObject.fromObject(slipObj); //전표
        JSONArray journalJson = JSONArray.fromObject(journalObj); //분개
        SlipBean slipBean = gson.fromJson(slipObj.toString(), SlipBean.class);
        ArrayList<JournalBean> journalBeans = new ArrayList<>();
        for (Object journal : journalJson) {
            JournalBean journalBean = gson.fromJson(journal.toString(), JournalBean.class);

            journalBean.setSlipNo(slipBean.getSlipNo());
            journalBeans.add(journalBean);
        }

        if(slipStatus.equals("승인요청")) {
            slipBean.setSlipStatus("승인요청");
        }else if(slipStatus.equals("작성중(반려)")){
            slipBean.setSlipStatus("승인요청");
        }
        return slipServiceFacade.updateSlip(slipBean,journalBeans);
    }

    @PostMapping("/addSlip")
    public String addSlip(HttpServletRequest request, @RequestParam String slipObj, @RequestParam String journalObj, @RequestParam String slipStatus) {

        System.out.println("addSlip진입");
        System.out.println("slipObj = "+slipObj);
        System.out.println("journalObj = "+journalObj);
        System.out.println("slipStatus = "+slipStatus);
        Gson gson = new Gson();
        JSONObject slipJson = JSONObject.fromObject(slipObj); //전표
        JSONArray journalObjs = JSONArray.fromObject(journalObj); //분개

        SlipBean slipBean = gson.fromJson(slipJson.toString(), SlipBean.class);
        slipBean.setReportingEmpCode(request.getSession().getAttribute("empCode").toString());  // beanCreator에서 셋팅하는데 또함..(dong)
        slipBean.setDeptCode(request.getSession().getAttribute("deptCode").toString());

        if(slipStatus.equals("승인요청")) {
            slipBean.setSlipStatus("승인요청");
        }

        ArrayList<JournalBean> journalBeans = new ArrayList<>();

        for (Object journal : journalObjs) {//분개에 slipNo를 넣어주기 위한 코드

            JournalBean journalBean = gson.fromJson(journal.toString(), JournalBean.class);

            journalBean.setSlipNo(slipBean.getSlipNo());
            journalBeans.add(journalBean);

        }
        slipServiceFacade.addSlip(slipBean, journalBeans);
        return slipObj;
    }

    @PostMapping("/deleteSlip")
    public  HashMap<String, String> deleteSlip(@RequestParam String slipNo) {
        slipServiceFacade.deleteSlip(slipNo);
        return new HashMap<String, String>();
    }

    @GetMapping("/approveSlip")
    public String approveSlip(HttpServletRequest request, @RequestParam String approveSlipList, @RequestParam String isApprove) {

        JSONArray approveSlipJson = JSONArray.fromObject(approveSlipList); // slip_no만 가지고옴
        String slipStatus = isApprove; // true
        ArrayList<SlipBean> slipBeans = new ArrayList<>();

        for (Object approveSlip : approveSlipJson) { // 승인일자를 자바로 만든다
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";
            String month = "0" + (calendar.get(Calendar.MONTH) + 1);
            String date = "0" + calendar.get(Calendar.DATE);
            String today = year + "-" + month.substring(month.length() - 2) + "-" + date.substring(date.length() - 2);
            //2019-07-20
            System.out.println(approveSlip);
            SlipBean slipBean = new SlipBean();
            slipBean.setSlipNo(approveSlip.toString());
            slipBean.setApprovalDate(today);
            slipBean.setSlipStatus(slipStatus);
            slipBean.setApprovalEmpCode(request.getSession().getAttribute("empCode").toString());
            slipBeans.add(slipBean);
        }
        slipServiceFacade.approveSlip(slipBeans);
        return slipStatus;
    }

    @GetMapping("/findRangedSlipList")
    public ArrayList<SlipBean> findRangedSlipList(@RequestParam String from, @RequestParam String to, @RequestParam String slipStatus) {
        HashMap<String, String> param = new HashMap<>();
        param.put("from", from);
        param.put("to", to);
        param.put("slipStatus", slipStatus);
        ArrayList result = slipServiceFacade.findRangedSlipList(param);
        return result;
    }

    @GetMapping("/findDisApprovalSlipList")
    public ArrayList<SlipBean> findDisApprovalSlipList() {
        return slipServiceFacade.findDisApprovalSlipList();
    }

    @GetMapping("/callAccountingSettlementStatus")
    public HashMap<String, Object> callAccountingSettlementStatus(@RequestParam String accountPeriodNo, @RequestParam String callResult) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("accountPeriodNo", accountPeriodNo);
        param.put("callResult", callResult);
        slipServiceFacade.getAccountingSettlementStatus(param);
        return param;
    }
}