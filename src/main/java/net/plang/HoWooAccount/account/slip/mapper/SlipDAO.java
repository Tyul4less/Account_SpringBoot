package net.plang.HoWooAccount.account.slip.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.slip.to.SlipBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SlipDAO {

    ArrayList<SlipBean> selectSlipDataList(String slipDate);

    void deleteSlip(String slipNo);

    void updateSlip(SlipBean slipBean);

    void insertSlip(SlipBean slipBean);

    void approveSlip(SlipBean slipBean);

    ArrayList<SlipBean> selectRangedSlipList(HashMap<String, String> param);

    ArrayList<SlipBean> selectDisApprovalSlipList();

    int selectSlipCount(String today);

    HashMap<String,Object> callAccountingSettlementStatus(HashMap<String, Object> param);

}
