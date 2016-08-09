package kz.gbk.eprocurement.tenders.persistence;

import kz.gbk.eprocurement.tenders.model.Tender;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abai on 24.07.2016.
 */
public class TenderDaoJdbc implements TenderDao {

    private static final String INSERT_TENDER_SQL = "INSERT INTO tender_sk(id, tender_id, company_name, tender_name, tender_method, tender_start, tender_end,tender_status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_TENDER_SQL = "SELECT * FROM tender_sk";
    private static final String CLEAN_ALL_TENDER_SQL = "TRUNCATE TABLE tender_sk, lot_sk";
    private final DataSource dataSource;

    public TenderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long save(Tender tender) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(INSERT_TENDER_SQL)) {
            long ID = getNextTenderId(connection);
            assert ID != -1;
            System.out.println("We're saving it to tender_sk: " + ID+ " "+ tender.getTenderID()+ " "+ tender.getCompanyName());
            pStmt.setLong(1, ID);
            pStmt.setLong(2, tender.getTenderID());
            pStmt.setString(3, tender.getCompanyName());
            pStmt.setString(4, tender.getTenderName());
            pStmt.setString(5, tender.getTenderMethod());
            pStmt.setDate(6, new java.sql.Date(tender.getTenderStart().getTime()));
            pStmt.setDate(7, new java.sql.Date(tender.getTenderEnd().getTime()));
            pStmt.setString(8, tender.getTenderStatus());
            pStmt.executeUpdate();
            return ID;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }


    private long getNextTenderId(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("SELECT nextval('tender_sk_seq') as tender_id");
        ResultSet rs = stmt.getResultSet();
        long tenderID = -1;
        if (rs.next()) {
            tenderID = rs.getLong("tender_id");
        }
        rs.close();
        stmt.close();

        return tenderID;
    }

    @Override
    public List<Tender> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(SELECT_ALL_TENDER_SQL)) {
            ResultSet rs = pStmt.executeQuery();
            List<Tender> tenderList = new ArrayList<>();
            int sum = 0;
            while(rs.next()){
                System.out.println(rs.getString(2)+ rs.getString(3));
                Tender newOne = new Tender();
                newOne.setTenderID(rs.getLong(2));
                newOne.setCompanyName(rs.getString(3));
                newOne.setTenderName(rs.getString(4));
                newOne.setTenderMethod(rs.getString(5));
                newOne.setTenderStart(rs.getDate(6));
                newOne.setTenderEnd(rs.getDate(7));
                newOne.setTenderStatus(rs.getString(8));
                tenderList.add(newOne);
                sum++;
            }
            System.out.println(SELECT_ALL_TENDER_SQL + sum+ "we finded all of it bro");
            return tenderList;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }


    @Override
    public Tender findOne(Long tenderID) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(SELECT_ALL_TENDER_SQL)) {
            ResultSet rs = pStmt.executeQuery();
            Tender newOne = new Tender();
            while(rs.next()){
                if(rs.getLong(2)==tenderID) {
                    System.out.println(rs.getString(2) + rs.getString(3));
                    newOne.setTenderID(rs.getLong(2));
                    newOne.setCompanyName(rs.getString(3));
                    newOne.setTenderName(rs.getString(4));
                    newOne.setTenderMethod(rs.getString(5));
                    newOne.setTenderStart(rs.getDate(6));
                    newOne.setTenderEnd(rs.getDate(7));
                    newOne.setTenderStatus(rs.getString(8));
                    return newOne;
                }
            }

        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    return null;}

    @Override
    public void cleanAllData() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(CLEAN_ALL_TENDER_SQL)) {
            dropSequence(connection);
            System.out.println("CLEANING...");
            pStmt.executeUpdate();

        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }

    }
    private void dropSequence(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("DROP SEQUENCE tender_sk_seq");
        stmt.execute("CREATE SEQUENCE tender_sk_seq");
        stmt.execute("ALTER SEQUENCE tender_sk_seq owner to tender");
        stmt.close();

    }
    public long getLastOneID(){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement("SELECT tender_id FROM tender_sk ORDER BY tender_id DESC LIMIT 1")) {
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
