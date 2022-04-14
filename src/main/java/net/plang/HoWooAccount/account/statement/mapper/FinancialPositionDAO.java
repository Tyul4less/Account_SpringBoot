package net.plang.HoWooAccount.account.statement.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface FinancialPositionDAO {

    public HashMap<String, Object> callFinancialPosition(HashMap<String, Object> param);

}
