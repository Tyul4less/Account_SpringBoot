package net.plang.HoWooAccount.account.statement.to;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TotalTrialBalanceBean {

    private int lev;
    private String accountName;
    private String accountInnerCode;
    private int debitsSumBalance;
    private int debitsSum;
    private int creditsSum;
    private int creditsSumBalance;
}
