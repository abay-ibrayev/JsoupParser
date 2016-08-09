package kz.gbk.eprocurement.tenders.persistence;

import kz.gbk.eprocurement.tenders.model.Tender;

import java.util.List;

/**
 * Created by abai on 24.07.2016.
 */
public interface TenderDao {

    long save(Tender tender);

    List<Tender> findAll();

    Tender findOne(Long tenderID);

    void cleanAllData();
}
