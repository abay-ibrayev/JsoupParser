import kz.gbk.eprocurement.tenders.model.Tender;
import kz.gbk.eprocurement.tenders.parsers.TenderHTMLParser;
import kz.gbk.eprocurement.tenders.persistence.TenderDaoJdbc;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by abai on 19.07.2016.
 */
public class TenderStarting{
    private static final String URL = "http://tender.sk.kz/index.php/ru/negs/";
    Tender lastOne = null;
    long lastID = 0;
    public void startTender(TenderHTMLParser parser,TenderDaoJdbc writer, int startPage, int endPage) throws IOException, ParseException {
        for(int i =startPage*10; i<=endPage*10;i+=10) {
            List<Tender> myList = parser.parseTenders(URL+i,lastOne, lastID);
            lastOne = myList.get(myList.size()-1);
            myList.forEach(writer::save);
        }
        //writer.cleanAllData();
        writer.findAll();
        //writer.findOne((long) 265340);
    }
    public void updateTender(TenderHTMLParser parser, TenderDaoJdbc writer) throws  IOException, ParseException{
        lastID = writer.getLastOneID();
        System.out.println(lastID  + " our lastID in DB");
        int i = 0;
        while(true){
            List<Tender> myList = parser.parseTenders(URL+i,lastOne,lastID);
            lastOne = myList.get(myList.size()-1);
            if(lastOne == null){
                myList.remove(myList.size()-1);
                myList.forEach(writer::save);
                System.out.println(" HERE WE STOP");
                break;
            }
            myList.forEach(writer::save);
            i += 10;

        }

    }
}
