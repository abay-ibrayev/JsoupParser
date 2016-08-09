package kz.gbk.eprocurement.tenders.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by abai on 24.07.2016.
 */
public class Tender implements Comparable<Tender>{
    private long tenderID;
    private String companyName;
    private String tenderName;
    private String tenderMethod;
    private Date tenderStart;
    private  Date tenderEnd;
    private String tenderStatus;

    public void setTenderID(long tenderID) {this.tenderID = tenderID;}

    public long getTenderID() { return tenderID;}

    public String getCompanyName() {return companyName;}

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTenderName() {return tenderName;}

    public void setTenderName(String tenderName) {this.tenderName = tenderName;}

    public String getTenderMethod() {return tenderMethod;}

    public void setTenderMethod(String tenderMethod) {this.tenderMethod = tenderMethod;}

    public Date getTenderStart() {return tenderStart;}

    public void setTenderStart(Date tenderStart) {this.tenderStart = tenderStart;}

    public Date getTenderEnd() {return tenderEnd;}

    public void setTenderEnd(Date tenderEnd) {this.tenderEnd = tenderEnd;}

    public String getTenderStatus() {return tenderStatus;}

    public void setTenderStatus(String tenderStatus) {this.tenderStatus = tenderStatus;}

    @Override
    public int compareTo(Tender o) {
        int a = (int) this.getTenderID();
        int b = (int) o.getTenderID();
        return a - b;
    }
}
