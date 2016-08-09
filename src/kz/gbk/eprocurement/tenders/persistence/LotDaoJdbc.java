package kz.gbk.eprocurement.tenders.persistence;

import kz.gbk.eprocurement.tenders.model.Lot;
import kz.gbk.eprocurement.tenders.model.Tender;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by abai on 25.07.2016.
 */
public class LotDaoJdbc implements LotDao {
    private static final String INSERT_LOT_SQL = "INSERT INTO lot_sk(id, tender_id, lot_id, lot_name, lot_desc, lot_quantity, lot_price,lot_sum, lot_place, lot_timeframe, lot_condition) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CLEAN_ALL_LOT_SQL = "TRUNCATE TABLE lot_sk, tender_sk";
    private static final String SELECT_ALL_LOT_SQL = "SELECT * FROM lot_sk";
    private final DataSource dataSource;
    public LotDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public long save(Lot lot) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(INSERT_LOT_SQL)) {
            long ID = getNextLotID(connection);
            assert ID != -1;
            pStmt.setLong(1,ID);
            pStmt.setLong(2,lot.getTenderID());
            pStmt.setInt(3,lot.getLotID());
            pStmt.setString(4,lot.getLotName());
            pStmt.setString(5,lot.getLotDesc());
            pStmt.setBigDecimal(6,lot.getLotQuantity());
            pStmt.setBigDecimal(7, lot.getLotPrice());
            pStmt.setBigDecimal(8,lot.getLotSum());
            pStmt.setString(9,lot.getLotPlace());
            pStmt.setString(10,lot.getLotTimeframe());
            pStmt.setString(11, lot.getLotCondition());
            pStmt.executeUpdate();
            return ID;

        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
    private long getNextLotID(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("SELECT nextval('lot_sk_seq') as lot_id");
        ResultSet rs = stmt.getResultSet();
        long lotID = -1;
        if (rs.next()) {
            lotID = rs.getLong("lot_id");
        }
        rs.close();
        stmt.close();

        return lotID;
    }

    @Override
    public List<Lot> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(SELECT_ALL_LOT_SQL)) {
            ResultSet rs = pStmt.executeQuery();
            List<Lot> lotList = new ArrayList<>();
            int sum = 0;
            while(rs.next()){
                System.out.println(rs.getString(2)+ rs.getString(3)+rs.getString(4));
                Lot newOne = new Lot();
                newOne.setTenderID(rs.getLong(2));
                newOne.setLotID(rs.getInt(3));
                newOne.setLotName(rs.getString(4));
                newOne.setLotDesc(rs.getString(5));
                newOne.setLotQuantity(rs.getBigDecimal(6));
                newOne.setLotPrice(rs.getBigDecimal(7));
                newOne.setLotSum(rs.getBigDecimal(8));
                newOne.setLotPlace(rs.getString(9));
                newOne.setLotTimeframe(rs.getString(10));
                newOne.setLotCondition(rs.getString(11));
                lotList.add(newOne);
                sum++;
            }
            System.out.println(SELECT_ALL_LOT_SQL + sum);
            return lotList;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public Lot findOne(long tenderID, int lotID){
    try (Connection connection = dataSource.getConnection();
    PreparedStatement pStmt = connection.prepareStatement(SELECT_ALL_LOT_SQL)) {
        ResultSet rs = pStmt.executeQuery();
        Lot newOne = new Lot();
        while(rs.next()){
            if(rs.getLong(2)==tenderID && rs.getInt(3)==lotID) {
                System.out.println(rs.getString(2) + rs.getString(3));
                newOne.setTenderID(rs.getLong(2));
                newOne.setLotID(rs.getInt(3));
                newOne.setLotName(rs.getString(4));
                newOne.setLotDesc(rs.getString(5));
                newOne.setLotQuantity(rs.getBigDecimal(6));
                newOne.setLotPrice(rs.getBigDecimal(7));
                newOne.setLotSum(rs.getBigDecimal(8));
                newOne.setLotPlace(rs.getString(9));
                newOne.setLotTimeframe(rs.getString(10));
                newOne.setLotCondition(rs.getString(11));
                return newOne;
            }
        }

    } catch (SQLException sqlEx) {
        throw new RuntimeException(sqlEx);
    }
    return null;
    }

    @Override
    public void cleanAllData() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(CLEAN_ALL_LOT_SQL)) {
            dropSequence(connection);
            System.out.println("CLEANING...");
            pStmt.execute();

        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }

    }
    private void dropSequence(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("DROP SEQUENCE lot_sk_seq");
        stmt.execute("CREATE SEQUENCE lot_sk_seq");
        stmt.execute("ALTER SEQUENCE lot_sk_seq owner to tender");
        stmt.close();
    }
    public List<Long> selectTenderLinks(long lastlotID){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement("SELECT tender_id from tender_sk WHERE tender_id > (?)")) {
            pStmt.setLong(1,lastlotID);
            List<Long> links = new ArrayList<>();
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()){
                links.add(rs.getLong(1));
            }
            Collections.sort(links);
            Collections.reverse(links);
            return links;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }

    }
    public long getLastOneID(){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement("SELECT tender_id FROM lot_sk ORDER BY tender_id DESC LIMIT 1")) {
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
        return 0;
    }

}
