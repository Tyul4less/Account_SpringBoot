package net.plang.HoWooAccount.system.base.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
@Mapper
public interface PeriodDAO {
	public String getPeriodNo(String today);
    
    public void insertPeriodNo(String sdate,String edate);
    
    public HashMap<String, Object> setEarlyStatements(String periodNo);

}
