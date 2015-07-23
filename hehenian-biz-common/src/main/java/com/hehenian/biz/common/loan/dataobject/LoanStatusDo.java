package com.hehenian.biz.common.loan.dataobject;

public class LoanStatusDo {

	private String name ;
	
	private String desc ;
	
	public LoanStatusDo(){};
	
	public LoanStatusDo(String name,String desc){
		this.name = name ;
		this.desc = desc ;
	}

	public static String getLoanStatusDesc(String loanStatus){
    	if("PENDING".equals(loanStatus)){
    		return "待处理";
    	}else if("DRAFT".equals(loanStatus)){
    		return "草稿";
    	}else if("NOPASSSTEP1".equals(loanStatus)){
    		return "一审不通过";
    	}else if("NOPASSSTEP2".equals(loanStatus)){
    		return "二审不通过";
    	}else if("PROCESSING".equals(loanStatus)){
    		return "处理中";
    	}else if("AUDITED".equals(loanStatus)){
    		return "已审核";
    	}else if("TREATY".equals(loanStatus)){
    		return "已签约";
    	}else if("SUBJECTED".equals(loanStatus)){
    		return "已上标";
    	}else if("NOPASS".equals(loanStatus)){
    		return "拒绝";
    	}else if("REPAYING".equals(loanStatus)){
    		return "还款中";
    	}else if("REPAYED".equals(loanStatus)){
    		return "已还清";
    	}else if("INVALID".equals(loanStatus)){
    		return "失效";
    	}
    	return null;
    }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
