package com.github.sebing.luminar;

import java.sql.*;

public class Connect {

    private String url = "jdbc:sqlite:" +
            "/Users/m_816259/Pictures/1/1.luminar" +
//            "/Users/m_816259/work/dataFiles/win.luminar" +
//            "/Users/m_816259/Pictures/Luminar 4 Catalog/windows.luminar"
            "";

    public Connect() {}
    public Connect(String url) {
        this.url = url;
    }

    public void listPaths(){
        try(Connection conn = DriverManager.getConnection(url)) {

            Statement stmt = conn.createStatement();
            String updateQurey = "UPDATE paths SET path_wide_ch = '/' || path_wide_ch WHERE path_wide_ch <> ''";
            stmt.executeUpdate(updateQurey);

//            String query = "select path_wide_ch from paths order by path_wide_ch";
//            ResultSet resultSet = stmt.executeQuery(query);
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("path_wide_ch"));
//            }
//                ResultSetMetaData md = resultSet.getMetaData();
//                int colCount = md.getColumnCount();
//
//                for (int i = 1; i <= colCount ; i++){
//                    String col_name = md.getColumnName(i);
//                            System.out.println(col_name);
//                    System.out.println(resultSet.getString(i));
//                }
//            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePaths(){
        try(Connection conn = DriverManager.getConnection(url)) {

            Statement stmt = conn.createStatement();
            String updateQurey = "UPDATE paths SET volume_id_int_64 = 3 where path_wide_ch<>''";
            stmt.executeUpdate(updateQurey);

//            String query = "select path_wide_ch from paths order by path_wide_ch";
//            ResultSet resultSet = stmt.executeQuery(query);
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("path_wide_ch"));
//            }
//                ResultSetMetaData md = resultSet.getMetaData();
//                int colCount = md.getColumnCount();
//
//                for (int i = 1; i <= colCount ; i++){
//                    String col_name = md.getColumnName(i);
//                            System.out.println(col_name);
//                    System.out.println(resultSet.getString(i));
//                }
//            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listAllData() {


        try(Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");

            ResultSet rs = conn.getMetaData().getTables(null, null, null, null);
                while (rs.next()) {
                    String table_name = rs.getString("TABLE_NAME");
;
                    String query = "select * from " + table_name;
                    Statement stmt = conn.createStatement();
                    ResultSet resultSet = stmt.executeQuery(query);
                    while (resultSet.next()) {

                        ResultSetMetaData md = resultSet.getMetaData();
                        int colCount = md.getColumnCount();

                        for (int i = 1; i <= colCount ; i++){
                            String col_name = md.getColumnName(i);
                            String string = resultSet.getString(i);

//                            System.out.println(col_name);
                            if(string.toLowerCase().indexOf("files") != -1) {
                                System.out.println("TABLE_NAME::"+ table_name);
                                System.out.println(string);
                            }
                        }


                    }
                }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Connect().listPaths();
//        new Connect().listAllData();
//        new Connect().updatePaths();
    }
}