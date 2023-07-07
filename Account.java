public class Account {
    String vendor;
    private String rtnum;
    private String acctnum;
    String type;
    String check;
    String amount;

    public Account() {
        vendor = "";
        rtnum = "";
        acctnum = "";
        type = "";
        check = "";
        amount = "";
    }

    public String printing() {
        String returning = (vendor + "\t" + check + "\t" + acctnum + "\t" + type + "\t"+ rtnum+"\t"+amount);
        return returning;
    }

    void setVendor(String Vendor) {
        this.vendor = Vendor;
    }

    void setRT(String RT) {
        this.rtnum = RT;
    }

    void setAcctNum(String acctNum) {
        this.acctnum = acctNum;
    }

    void setType(String Type) {
        this.type = Type;
    }

    void setCheck(String Check) {
        this.check = Check;
    }

    void setAmount(String Amount) {
        this.amount = Amount;
    }

    String getVendor() {
        return vendor;
    }

    String getRT() {
        return rtnum;
    }

    String getAcctNum() {
        return acctnum;
    }

    String getType() {
        return type;
    }

    String getCheck() {
        return check;
    }

    String getAmount() {
        return amount;
    }
}