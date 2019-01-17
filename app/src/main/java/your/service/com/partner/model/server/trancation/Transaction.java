package your.service.com.partner.model.server.trancation;

public class Transaction {
    private String amount;
    private String credits;
    private String totalCredits;
    private String transactionID;
    private String paypalTID;
    private String transactionStartingDate;
    private String transactionEndDate;
    private String transactionStatus;
    private String partnerID;
    private String partnerNumber;
    private String transactionReport;
    private String serverTransactionID;



    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(String totalCredits) {
        this.totalCredits = totalCredits;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getPaypalTID() {
        return paypalTID;
    }

    public void setPaypalTID(String paypalTID) {
        this.paypalTID = paypalTID;
    }

    public String getTransactionStartingDate() {
        return transactionStartingDate;
    }

    public void setTransactionStartingDate(String transactionStartingDate) {
        this.transactionStartingDate = transactionStartingDate;
    }

    public String getTransactionEndDate() {
        return transactionEndDate;
    }

    public void setTransactionEndDate(String transactionEndDate) {
        this.transactionEndDate = transactionEndDate;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    public String getPartnerNumber() {
        return partnerNumber;
    }

    public void setPartnerNumber(String partnerNumber) {
        this.partnerNumber = partnerNumber;
    }

    public String getTransactionReport() {
        return transactionReport;
    }

    public void setTransactionReport(String transactionReport) {
        this.transactionReport = transactionReport;
    }

    public String getServerTransactionID() {
        return serverTransactionID;
    }

    public void setServerTransactionID(String serverTransactionID) {
        this.serverTransactionID = serverTransactionID;
    }
}
