/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject
 * @Title: PropertyDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:30:27
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hehenian.biz.common.util.StringUtil;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:30:27
 */
public class PropertyDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              propertyId;           // 个人资产ID
    private Long              loanId;               // 借款申请ID
    private Long              loanPersonId;         // 借款人ID
    private String            houseAddress;         // 房产地址
    private PurchaseWay       purchaseWay;          // 购买方式
    private Integer           houstWay;             // 房子购买方式：0-一次性,1-按揭  
    private Date              purchaseDate;         // 购买日期
    private String            purchaseDateString;         // 购买日期
    private Integer           hyear;
    private Integer           hmonth;
    private Integer           hday ;
    private Double            housePrice;           // 房子购买价格
    private String            housePriceString;     // 房子购买价格
    private Double            coveredArea;          // 建筑面积，单位:平方米
    private String            coveredAreaString;          // 建筑面积，单位:平方米
    private String            advisedPeople;        // 共有权人
    private Integer           houseDy;              // 房子是否抵押他人：0-否,1-是
    private Integer           houseReDur;           // 房子已按揭还贷时长，单位：月
    private Double            houseMthOwing;        // 房子每月还贷
    private String            houseMthOwingString;  // 房子每月还贷
    private Double            houseReMtg;           // 剩余房贷
    private String            houseReMtgString;           // 剩余房贷
    
    private String            carNo;                // 车牌号码
    private String            carBrand;             // 车辆品牌型号
    private Double            carPrice;             // 车子购买价格
    private String            carPriceString;             // 车子购买价格
    private Date              carDate;              // 车子购买日期 
    private Integer           cyear;
    private Integer           cmonth;
    private Integer           cday;
    private Integer           carWay;               // 车子购买方式：0-一次性,1-按揭  
    private Integer           carDy;                // 车子是否抵押他人：0-否,1-是
    private Integer           carReDur;             // 车子已按揭时长，单位月
    private Double            carMthOwing;          // 车子每月还贷
    private String            carMthOwingString;          // 车子每月还贷
    private Double            carReMtg;             // 剩余车贷
    private String            carReMtgString;             // 剩余车贷
    
    private HouseStatus 	  houseStatus;			//个人资产验证状态
    private Date              createTime;           // 创建日期
    private Date              updateTime;           // 最后修改日期
    
    private String remark;	//备注

    DecimalFormat df = new DecimalFormat("###0.#");
    /** 购买方式（NOMORTGAGE-自置无按揭，MORTGAGE-自置有按揭） */
    public enum PurchaseWay {
        NOMORTGAGE, MORTGAGE;
    }
    
    /** 个人资产验证状态（PASS-审批通过，NOPASS-审批不过） */
    public enum HouseStatus {
        PASS, NOPASS;
    }

    /**
     * @return propertyId
     */
    public Long getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId
     *            the propertyId to set
     */
    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * @return loanId
     */
    public Long getLoanId() {
        return loanId;
    }

    /**
     * @param loanId
     *            the loanId to set
     */
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    /**
     * @return loanPersonId
     */
    public Long getLoanPersonId() {
        return loanPersonId;
    }

    /**
     * @param loanPersonId
     *            the loanPersonId to set
     */
    public void setLoanPersonId(Long loanPersonId) {
        this.loanPersonId = loanPersonId;
    }

    /**
     * @return houseAddress
     */
    public String getHouseAddress() {
        return houseAddress;
    }

    /**
     * @param houseAddress
     *            the houseAddress to set
     */
    public void setHouseAddress(String houseAddress) {
        this.houseAddress = StringUtil.filterDangerString(houseAddress);
    }

    /**
    * @return purchaseWay
    */
    public PurchaseWay getPurchaseWay() {
        return purchaseWay;
    }

    /**
    * @param purchaseWay
    *            the purchaseWay to set
    */
    public void setPurchaseWay(PurchaseWay purchaseWay) {
        this.purchaseWay = purchaseWay;
    }

    /**
     * @return purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate
     *            the purchaseDate to set
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
        setPurchaseDateString(new SimpleDateFormat("yyyy-MM-dd ").format(purchaseDate) );
    }

    
    public String getPurchaseDateString() {
		return purchaseDateString;
	}

	public void setPurchaseDateString(String purchaseDateString) {
		this.purchaseDateString = purchaseDateString;
	}

	/**
     * @return carNo
     */
    public String getCarNo() {
        return carNo;
    }

    /**
     * @param carNo
     *            the carNo to set
     */
    public void setCarNo(String carNo) {
        this.carNo = StringUtil.filterDangerString(carNo);
    }

    /**
     * @return carBrand
     */
    public String getCarBrand() {
        return carBrand;
    }

    /**
     * @param carBrand
     *            the carBrand to set
     */
    public void setCarBrand(String carBrand) {
        this.carBrand = StringUtil.filterDangerString(carBrand);
    }

    /**
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public HouseStatus getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(HouseStatus houseStatus) {
		this.houseStatus = houseStatus;
	}

	public Double getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(Double housePrice) {
		if(null!=housePrice){
			this.housePrice = housePrice;
			setHousePriceString(df.format(housePrice));
		}
	}
	
	public String getHousePriceString() {
		return housePriceString;
	}

	public void setHousePriceString(String housePriceString) {
		this.housePriceString = housePriceString;
	}

	public Double getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(Double coveredArea) {
		if(null!=coveredArea){
			this.coveredArea = coveredArea;
			setCoveredAreaString(df.format(coveredArea));
		}
	}

	public String getCoveredAreaString() {
		return coveredAreaString;
	}

	public void setCoveredAreaString(String coveredAreaString) {
		this.coveredAreaString = coveredAreaString;
	}

	public String getAdvisedPeople() {
		return advisedPeople;
	}

	public void setAdvisedPeople(String advisedPeople) {
		this.advisedPeople =  StringUtil.filterDangerString(advisedPeople);
	}

	public Integer getHouseDy() {
		return houseDy;
	}

	public void setHouseDy(Integer houseDy) {
		this.houseDy = houseDy;
	}

	public Integer getHouseReDur() {
		return houseReDur;
	}

	public void setHouseReDur(Integer houseReDur) {
		this.houseReDur = houseReDur;
	}

	public Double getHouseMthOwing() {
		return houseMthOwing;
	}

	public void setHouseMthOwing(Double houseMthOwing) {
		if(null !=houseMthOwing){
			this.houseMthOwing = houseMthOwing;
			setHouseMthOwingString(df.format(houseMthOwing));
		}
	}

	public String getHouseMthOwingString() {
		return houseMthOwingString;
	}

	public void setHouseMthOwingString(String houseMthOwingString) {
		this.houseMthOwingString = houseMthOwingString;
	}

	public Double getHouseReMtg() {
		return houseReMtg;
	}

	public void setHouseReMtg(Double houseReMtg) {
		if(null!=houseReMtg){
			this.houseReMtg = houseReMtg;
			setHouseReMtgString(df.format(houseReMtg));
		}
	}

	public String getHouseReMtgString() {
		return houseReMtgString;
	}

	public void setHouseReMtgString(String houseReMtgString) {
		this.houseReMtgString = houseReMtgString;
	}

	public Double getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(Double carPrice) {
		if(null!=carPrice){
			this.carPrice = carPrice;
			setCarPriceString(df.format(carPrice));
		}
	}

	public String getCarPriceString() {
		return carPriceString;
	}

	public void setCarPriceString(String carPriceString) {
		this.carPriceString = carPriceString;
	}

	public Date getCarDate() {
		return carDate;
	}

	public void setCarDate(Date carDate) {
		this.carDate = carDate;
	}

	public Integer getCarWay() {
		return carWay;
	}

	public void setCarWay(Integer carWay) {
		this.carWay = carWay;
	}

	public Integer getCarDy() {
		return carDy;
	}

	public void setCarDy(Integer carDy) {
		this.carDy = carDy;
	}

	public Integer getCarReDur() {
		return carReDur;
	}

	public void setCarReDur(Integer carReDur) {
		this.carReDur = carReDur;
	}

	public Double getCarMthOwing() {
		return carMthOwing;
	}

	public void setCarMthOwing(Double carMthOwing) {
		if(null!=carMthOwing){
			this.carMthOwing = carMthOwing;
			setCarMthOwingString(df.format(carMthOwing));
		}
	}

	public String getCarMthOwingString() {
		return carMthOwingString;
	}

	public void setCarMthOwingString(String carMthOwingString) {
		this.carMthOwingString = carMthOwingString;
	}

	public Double getCarReMtg() {
		return carReMtg;
	}

	public void setCarReMtg(Double carReMtg) {
		if(null!=carReMtg){
			this.carReMtg = carReMtg;
			setCarReMtgString(df.format(carReMtg));
		}
	}

	public String getCarReMtgString() {
		return carReMtgString;
	}

	public void setCarReMtgString(String carReMtgString) {
		this.carReMtgString = carReMtgString;
	}

	public Integer getHoustWay() {
		return houstWay;
	}

	public void setHoustWay(Integer houstWay) {
		this.houstWay = houstWay;
	}

	public Integer getHyear() {
		return hyear;
	}

	public void setHyear(Integer hyear) {
		this.hyear = hyear;
	}

	public Integer getHmonth() {
		return hmonth;
	}

	public void setHmonth(Integer hmonth) {
		this.hmonth = hmonth;
	}

	public Integer getHday() {
		return hday;
	}

	public void setHday(Integer hday) {
		this.hday = hday;
	}

	public Integer getCyear() {
		return cyear;
	}

	public void setCyear(Integer cyear) {
		this.cyear = cyear;
	}

	public Integer getCmonth() {
		return cmonth;
	}

	public void setCmonth(Integer cmonth) {
		this.cmonth = cmonth;
	}

	public Integer getCday() {
		return cday;
	}

	public void setCday(Integer cday) {
		this.cday = cday;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
