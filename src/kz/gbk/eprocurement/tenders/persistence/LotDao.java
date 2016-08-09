package kz.gbk.eprocurement.tenders.persistence;

import kz.gbk.eprocurement.tenders.model.Lot;

import java.util.List;

/**
 * Created by abai on 25.07.2016.
 */
public interface LotDao {

    long save(Lot lot);

    List<Lot> findAll();

    Lot findOne(long tenderID, int lotID);

    void cleanAllData();
}
