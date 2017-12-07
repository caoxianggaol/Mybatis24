package com.kaishengit.entity;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class Seckill implements Serializable {
    private Integer id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品副标题
     */
    private String productTitle;

    /**
     * 商品库存
     */
    private Integer productInventory;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 市场价格
     */
    private Long productMarketPice;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 商品描述
     */
    private String productDesc;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductMarketPice() {
        return productMarketPice;
    }

    public void setProductMarketPice(Long productMarketPice) {
        this.productMarketPice = productMarketPice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Long getStartTimeTs() {
        return getStartTime().getTime();
    }

    public boolean isStart() {
        DateTime dateTime = new DateTime(getStartTime().getTime());
        return dateTime.isBeforeNow();
    }

    public boolean isEnd() {
        DateTime dateTime = new DateTime(getEndTime().getTime());
        return dateTime.isBeforeNow();
    }
}