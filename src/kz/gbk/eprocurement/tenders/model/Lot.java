package kz.gbk.eprocurement.tenders.model;

import java.math.BigDecimal;

/**
 * Created by abai on 25.07.2016.
 */
public class Lot {
    private long tenderID;
    private int lotID;
    private String lotName;
    private String lotDesc;
    private BigDecimal lotQuantity;
    private BigDecimal lotPrice;
    private BigDecimal lotSum;
    private String lotPlace;
    private String lotTimeframe;
    private String lotCondition;

    public long getTenderID() {return tenderID;}

    public void setTenderID(long tenderID) {this.tenderID = tenderID;}

    public int getLotID() {return lotID;}

    public void setLotID(int lotID) {this.lotID = lotID;}

    public String getLotName() {return lotName;}

    public void setLotName(String lotName) {this.lotName = lotName;}

    public String getLotDesc() {return lotDesc;}

    public void setLotDesc(String lotDesc) {this.lotDesc = lotDesc;}

    public BigDecimal getLotPrice() {return lotPrice;}

    public void setLotPrice(BigDecimal lotPrice) {this.lotPrice = lotPrice;}

    public BigDecimal getLotQuantity() {return lotQuantity;}

    public void setLotQuantity(BigDecimal lotQuantity) {this.lotQuantity = lotQuantity;}

    public BigDecimal getLotSum() {return lotSum;}

    public void setLotSum(BigDecimal lotSum) {this.lotSum = lotSum;}

    public String getLotPlace() {return lotPlace;}

    public void setLotPlace(String lotPlace) {this.lotPlace = lotPlace;}

    public String getLotTimeframe() {return lotTimeframe;}

    public void setLotTimeframe(String lotTimeframe) {this.lotTimeframe = lotTimeframe;}

    public String getLotCondition() {return lotCondition;}

    public void setLotCondition(String lotCondition) {this.lotCondition = lotCondition;}

}
