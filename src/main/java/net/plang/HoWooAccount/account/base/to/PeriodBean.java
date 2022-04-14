package net.plang.HoWooAccount.account.base.to;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.plang.HoWooAccount.system.base.to.BaseBean;

@Setter
@Getter
public class PeriodBean extends BaseBean{

	private String accountPeriodNo;
	private String fiscalYear;
	private String workplaceCode;
	private String periodStartDate;
	private String periodEndDate;
	
}
