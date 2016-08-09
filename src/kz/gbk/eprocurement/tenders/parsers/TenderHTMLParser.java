package kz.gbk.eprocurement.tenders.parsers; /**
 * Created by abai on 19.07.2016.
 */
import kz.gbk.eprocurement.tenders.model.Tender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TenderHTMLParser {
    public List<Tender> parseTenders(String url, Tender lastOne, long lastID) throws IOException, ParseException {
        List<Tender> tendersInfo = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Document doc = Jsoup.connect(url).timeout(10*1000).get();
        for (Element table : doc.select("table[width=100%]")) {
            for (Element row : table.select("tr[bgcolor=#fafafa")) {
                Tender oneTender = new Tender();
                Elements tds = row.select("td");
                if (lastID!= Long.parseLong(row.select("a[href]").first().attr("href").split("/")[7])) {
                    if (lastOne != null && Long.parseLong(row.select("a[href]").first().attr("href").split("/")[7]) == lastOne.getTenderID()) {
                        System.out.println("REPEATING.....");
                    } else {
                        oneTender.setTenderID(Long.parseLong(row.select("a[href]").first().attr("href").split("/")[7]));
                        oneTender.setCompanyName(tds.get(1).text());
                        oneTender.setTenderName(tds.get(2).text());
                        oneTender.setTenderMethod(tds.get(3).text());
                        oneTender.setTenderStart(df.parse(tds.get(4).text()));
                        oneTender.setTenderEnd(df.parse(tds.get(5).text()));
                        oneTender.setTenderStatus(tds.get(6).text());
                        tendersInfo.add(oneTender);
                    }
                }else{
                    tendersInfo.add(null);
                    return tendersInfo;
                }
            }
            break;
        }
        return tendersInfo;
    }
}
