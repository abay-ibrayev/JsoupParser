package kz.gbk.eprocurement.tenders.parsers;

import kz.gbk.eprocurement.tenders.model.Lot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abai on 21.07.2016.
 */
public class LotHTMLParser {
    public List<Lot> parseLots(String url, long link) throws Exception{
        Document doc = Jsoup.connect(url+link).timeout(10*1000).get();
        List<Lot> manyLots = new ArrayList<>();
        for (Element table : doc.select("table[class=showtab]")) {
            if(table==doc.select("table[class=showtab]").first()){
                continue;
            }
            for (Element row : table.select("tr[bgcolor=#fafafa")) {
                if(row==table.select("tr[bgcolor=#fafafa").first()){
                    continue;
                }
                Lot oneLot = new Lot();
                Elements tds = row.select("td");
                oneLot.setTenderID(link);
                System.out.println("We're saving it to lot_sk: "+ tds.get(0).text()+ " " + link+ " "+ tds.get(1).text());
                oneLot.setLotID(Integer.parseInt(tds.get(0).text()));
                oneLot.setLotName(tds.get(1).text());
                oneLot.setLotDesc(tds.get(2).text());
                oneLot.setLotQuantity(new BigDecimal(tds.get(3).text().replaceAll(",","")));
                oneLot.setLotPrice(new BigDecimal(tds.get(4).text().replaceAll(",","").replaceAll(" ","")));
                oneLot.setLotSum(new BigDecimal(tds.get(4).text().replaceAll(",","").replaceAll(" ","")));
                oneLot.setLotPlace(tds.get(6).text());
                oneLot.setLotTimeframe(tds.get(7).text());
                oneLot.setLotCondition(tds.get(8).text());
                manyLots.add(oneLot);
            }

        }
        return manyLots;

    }
}
