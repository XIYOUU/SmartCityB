package com.example.smartcityb.livePaymentBean;

public class LivingBillBean {

    /**
     * msg : 操作成功
     * code : 200
     * data : {"id":1,"billNo":"202104240810306","amount":0,"chargeUnit":"大连电力公司","paymentNo":"15670226","categoryId":3,"payStatus":"1","address":"大连高新园区大华锦绣3号楼2单元2803"}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * billNo : 202104240810306
         * amount : 0
         * chargeUnit : 大连电力公司
         * paymentNo : 15670226
         * categoryId : 3
         * payStatus : 1
         * address : 大连高新园区大华锦绣3号楼2单元2803
         */

        private int id;
        private String billNo;
        private int amount;
        private String chargeUnit;
        private String paymentNo;
        private int categoryId;
        private String payStatus;
        private String address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBillNo() {
            return billNo;
        }

        public void setBillNo(String billNo) {
            this.billNo = billNo;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getChargeUnit() {
            return chargeUnit;
        }

        public void setChargeUnit(String chargeUnit) {
            this.chargeUnit = chargeUnit;
        }

        public String getPaymentNo() {
            return paymentNo;
        }

        public void setPaymentNo(String paymentNo) {
            this.paymentNo = paymentNo;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
