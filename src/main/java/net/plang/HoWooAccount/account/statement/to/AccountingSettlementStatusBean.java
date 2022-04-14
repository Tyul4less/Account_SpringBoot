package net.plang.HoWooAccount.account.statement.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountingSettlementStatusBean {

	private int accountPeriodNo;
	private String totalTrialBalance;
	private String incomeStatement;
	private String financialPosition;

	
}
