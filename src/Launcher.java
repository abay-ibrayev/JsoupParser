import kz.gbk.eprocurement.tenders.parsers.LotHTMLParser;
import kz.gbk.eprocurement.tenders.parsers.TenderHTMLParser;
import kz.gbk.eprocurement.tenders.persistence.LotDaoJdbc;
import kz.gbk.eprocurement.tenders.persistence.TenderDaoJdbc;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * Created by abai on 25.07.2016.
 */
public class Launcher {
    private static final String TENDER_URL = "http://tender.sk.kz/index.php/ru/negs/";
    private static final  String LOT_URL = "http://tender.sk.kz/index.php/ru/negs/show/";
    public static void main(String[]  args) throws Exception {
        TenderHTMLParser tenderParser = new TenderHTMLParser();
        LotHTMLParser lotParser = new LotHTMLParser();
        final PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/tenderdb");
        ds.setUser("tender");
        ds.setPassword("tender");
        TenderDaoJdbc tenderWriter = new TenderDaoJdbc(ds);
        LotDaoJdbc lotWriter = new LotDaoJdbc(ds);
        TenderStarting weStartTenderHere = new TenderStarting();
        LotStarting weStartLotHere = new LotStarting();
        //tenderWriter.cleanAllData();
        //weStartTenderHere.startTender(tenderParser,tenderWriter,2,10);
        weStartTenderHere.updateTender(tenderParser,tenderWriter);
        //weStartLotHere.startLot(lotParser,lotWriter);
        weStartLotHere.updateLot(lotParser,lotWriter);

    }

}
