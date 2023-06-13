package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForgotResponse {

    @SerializedName("succ")
    @Expose
    private Boolean succ;
    @SerializedName("public_msg")
    @Expose
    private String publicMsg;
    @SerializedName("_err_codes")
    @Expose
    private List<Object> errCodes = null;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getSucc() {
        return succ;
    }

    public void setSucc(Boolean succ) {
        this.succ = succ;
    }

    public String getPublicMsg() {
        return publicMsg;
    }

    public void setPublicMsg(String publicMsg) {
        this.publicMsg = publicMsg;
    }

    public List<Object> getErrCodes() {
        return errCodes;
    }

    public void setErrCodes(List<Object> errCodes) {
        this.errCodes = errCodes;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {
        @SerializedName("otp")
        private String otp;



        private String pan_file;

        private String agent_id;

        private String approx_month_vol;

        private String gst_nb;

        private String retainership_fee;

        private String discount;

        private String customer_category;

        private String is_ref_id;



        private String domain_name;

        private String password;

        private String user_type;

        private String date_of_register;

        private String credit_limit_exceded;

        private String state;

        private String id;

        private String unique_code;



        private String debit_note_billing;

        private String fname;

        private String join_date;

        private String testting_purpose;

        private String min_qty;

        private String emp_img_path;

        private String mob_no;

        private String web_link;

        private String total_invoice;

        private String adhar_card;

        private String user_head_id;

        private String tds_percentage;

        private String due_days;

        private String user_id;

        private String company_name;

        private String blood_group;

        private String credit_days;


        private String status;

        private String present_address;

        private String city;

        private String date_of_birth;

        private String sms_alert;

        private String pin_code;

        private String mname;

        private String SalesM_id;

        private String currency_code;

        private String login_mast_id;



        private String c_opening_bal;

        private String send_invoice_by;

        private String customer_grade;

        private String address_file;



        private String r_invoice_amount;

        private String emp_code;

        private String address;

        private String company_id;

        private String department_id;

        private String designation_id;

        private String sex;



        private String currency_value;

        private String email_alert;

        private String bill_contact_person;

        private String pool_id;

        private String customer_segment;

        private String cust_code;

        private String createdDate;

        private String crm_id;

        private String is_flag;

        private String ops_cust_mast_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        private String per_address;

        private String InvoicePeriodAdjustment;

        private String phone_no;

        private String company_type;

        private String country;

        private String is_ref_id_by_pass;

        private String IsAdjusted;

        private String opening_status;

        private String report_to;

        private String alternate_email_id;

        private String cust_ref_num;

        private String customer_code_bracnet;

        private String image;

        private String hold_invoices;

        private String client_type_id;

        private String app_login;

        private String postcode;

        private String pan_card;

        private String created_by;

        private String web_login;

        private String dar_crm_id;

        private String sam_id;

        private String imagees;

        private String adhar_file;

        private String um_dactive_reason;

        private String alteremail_id;

        private String fax_no;

        private String company_client;

        private String email_id;

        private String allowedLocalIp;

        private String remark;

        private String associated_branch;

        private String lname;

        private String channel_partner;

        private String branch_id;

        private String emp_img_path1;

        private String business_type;



        private String credit_limit;

        private String merital_status;

        private String branch_association;

        private String allowedIp;



        private String sl_nb;

        private String holiday_group_id;

        private String is_gps_by_pass;

        private String seller_code;

        private String um_gl_code;

        private String custome_cate_type;
        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }


    }

}
