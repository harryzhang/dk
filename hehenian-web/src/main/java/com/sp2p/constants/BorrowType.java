/**
 * 
 */
package com.sp2p.constants;

/**
 * 借款类型
 * 
 */
public enum BorrowType {
	WORTH(1,"worth"),SECONDS(2,"seconds"),  ORDINARY(3,"ordinary"), FIELD(4,"field"), INSTITUTIONS(
			5,"institutions"),FLOW(6,"flow");
	private final int borrowWay;
	private final String value;

	private BorrowType(int borrowWay,String val) {
		this.borrowWay = borrowWay;
		this.value = val;
	}

	public String getValue() {
		return this.value;
	}

	public int getBorrowWay() {
		return borrowWay;
	}
	
	public static String getNidByBorrowWay(int borrowWay){
		if(WORTH.borrowWay==borrowWay){
			return WORTH.value;
		}else if(SECONDS.borrowWay==borrowWay){
			return SECONDS.value;
		}else if(ORDINARY.borrowWay==borrowWay){
			return ORDINARY.value;
		}else if(FIELD.borrowWay==borrowWay){
			return FIELD.value;
		}else if(INSTITUTIONS.borrowWay==borrowWay){
			return INSTITUTIONS.value;
		}
		return null;
	}
}
