import kz.gbk.eprocurement.tenders.parsers.LotHTMLParser;
import kz.gbk.eprocurement.tenders.persistence.LotDaoJdbc;
import kz.gbk.eprocurement.tenders.persistence.TenderDaoJdbc;

import java.util.Collections;
import java.util.List;

/**
 * Created by abai on 21.07.2016.
 */
public class LotStarting {

    private static final  String url = "http://tender.sk.kz/index.php/ru/negs/show/";
    /*public void startLot(LotHTMLParser parser, LotDaoJdbc writer) throws Exception {
        for(long link:writer.selectTenderLinks(0)){
            parser.parseLots(url,link).forEach(writer::save);
        }


    }*/
    public void updateLot (LotHTMLParser parser, LotDaoJdbc writer) throws Exception {
        long lastlotID = writer.getLastOneID();
        for(long link:writer.selectTenderLinks(lastlotID)){
            parser.parseLots(url,link).forEach(writer::save);

        }
    }
}
