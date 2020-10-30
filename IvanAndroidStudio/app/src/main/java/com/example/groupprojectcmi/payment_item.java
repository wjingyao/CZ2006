package com.example.groupprojectcmi;

public class payment_item {
    int paymentId;
    String paymentCardNo;
    String expiryDate;

    public payment_item(int ipaymentId, String ipaymentCardNo, String iexpiryDate) {
        paymentId = ipaymentId;
        paymentCardNo = ipaymentCardNo;
        expiryDate = iexpiryDate;
    }

    public int getPaymentId() { return paymentId; }

    public void setPaymentId(int ipaymentId) { this.paymentId = ipaymentId; }

    public String getPaymentCardNo() { return paymentCardNo; }

    public void setPaymentCardNo(String ipaymentCardNo) { this.paymentCardNo = ipaymentCardNo; }

    public String getExpiryDate() { return expiryDate; }

    public void setExpiryDate(String iexpiryDate) { this.expiryDate = iexpiryDate; }
}
