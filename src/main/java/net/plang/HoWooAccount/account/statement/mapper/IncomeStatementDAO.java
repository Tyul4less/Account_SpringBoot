package net.plang.HoWooAccount.account.statement.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface IncomeStatementDAO {
    public HashMap<String, Object> callIncomeStatement(HashMap<String, Object> param);
}
