package com.example.groupprojectcmi;

public class payment_item {
    String paymentCardNo;
    String expiryDate;

    public payment_item(String ipaymentCardNo, String iexpiryDate) {
        paymentCardNo = ipaymentCardNo;
        expiryDate = iexpiryDate;
    }

    public String getPaymentCardNo() { return paymentCardNo; }

    public void setPaymentCardNo(String ipaymentCardNo) { this.paymentCardNo = ipaymentCardNo; }

    public String getExpiryDate() { return expiryDate; }

    public void setExpiryDate(String iexpiryDate) { this.expiryDate = iexpiryDate; }
}
