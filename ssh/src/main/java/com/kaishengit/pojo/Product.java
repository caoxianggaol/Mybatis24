package com.kaishengit.pojo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by xiaogao on 2017/11/29.
 */
@Entity
@Table(name = "kaola")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "product_name")
    private String productName;
    private BigDecimal price;
    @Column(name = "market_price")
    private BigDecimal marketPrice;
    private String place;
    @Column(name = "comment_num")
    private Integer commentNum;

    @ManyToOne(fetch = FetchType.LAZY)//不延迟加载，通过fetch = FetchType.LAZY开启
    @JoinColumn(name = "type_id")//多的一端中的外键属性
    private KaolaType kaolaType;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPricep) {
        this.marketPrice = marketPricep;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public KaolaType getKaolaType() {
        return kaolaType;
    }

    public void setKaolaType(KaolaType kaolaType) {
        this.kaolaType = kaolaType;
    }
}
