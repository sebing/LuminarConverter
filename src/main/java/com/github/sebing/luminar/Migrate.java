package com.github.sebing.luminar;

import java.sql.*;

public class Migrate {

    private final String windowsUrl;
//    private final String windowsUrl = "jdbc:sqlite:" +
//            "/Users/m_816259/work/dataFiles/win.luminar";
    private final String macUrl;
//    private final String macUrl = "jdbc:sqlite:" +
//            "/Users/m_816259/work/dataFiles/mac.luminar";

    public Migrate(String windowsCatalogFile, String macCatalogFile) {
        this.windowsUrl = "jdbc:sqlite:" + windowsCatalogFile;
        this.macUrl = "jdbc:sqlite:" +macCatalogFile;
        modifyVolumes();
        updatePaths();
    }


    private void modifyVolumes() {
        String insertVolumes = "insert into volumes values(?,?,?,?) ";
        String deleteVolumes = "Delete from volumes";
        try (
                Connection windowsConn = DriverManager.getConnection(windowsUrl);
                Connection macConn = DriverManager.getConnection(macUrl);
                Statement selectStatement = macConn.createStatement();
                PreparedStatement insertStatement = windowsConn.prepareStatement(insertVolumes);
                PreparedStatement deleteStatement = windowsConn.prepareStatement(deleteVolumes);
        ){
            deleteStatement.executeUpdate();

            String selectVolumes = "select * from volumes" ;
            ResultSet resultSet = selectStatement.executeQuery(selectVolumes);
            while (resultSet.next()) {
                ResultSetMetaData md = resultSet.getMetaData();
                int colCount = md.getColumnCount();

                for (int i = 1; i <= colCount ; i++) {
                    System.out.println(md.getColumnName(i) + "::" + resultSet.getString(i));
                    insertStatement.setString(i,resultSet.getString(i));
                }
                insertStatement.executeUpdate();
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePaths(){
        try(Connection conn = DriverManager.getConnection(windowsUrl)) {

            Statement stmt = conn.createStatement();
            String updateQurey = "UPDATE paths SET path_wide_ch = '/' || path_wide_ch WHERE path_wide_ch <> ''";
            stmt.executeUpdate(updateQurey);

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Migrate("/Users/m_816259/work/dataFiles/win.luminar",
                "/Users/m_816259/work/dataFiles/mac.luminar");
    }}
