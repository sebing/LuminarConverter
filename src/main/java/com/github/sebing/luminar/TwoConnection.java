package com.github.sebing.luminar;

import java.sql.*;

public class TwoConnection {
    private String url1 = "jdbc:sqlite:/Users/m_816259/Pictures/1/2.luminar";
    private String url2 = "jdbc:sqlite:/Users/m_816259/Pictures/1/3.luminar";

    public void listPaths() {
        try (
                Connection conn1 = DriverManager.getConnection(url1);
                Connection conn2 = DriverManager.getConnection(url2);
                Statement stmt1 = conn1.createStatement();
                Statement stmt2 = conn2.createStatement();
            ) {


            ResultSet rs = conn1.getMetaData().getTables(null, null, null, null);
            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                ;
                String query = "select * from " + table_name;
                ResultSet resultSet1 = stmt1.executeQuery(query);
                ResultSet resultSet2 = stmt2.executeQuery(query);

                while (resultSet1.next() && resultSet2.next()) {

                    ResultSetMetaData md = resultSet1.getMetaData();
                    int colCount = md.getColumnCount();

                    for (int i = 1; i <= colCount ; i++){
                        String col_name = md.getColumnName(i);
                        String string1 = resultSet1.getString(i);
                        String string2 = resultSet2.getString(i);
                        if(
//                                !table_name.equals("img_history_states") &&
                                !string1.equals(string2)){
                            System.out.println("Table::" + table_name + " column:: "+ col_name + " value:" + string1 + " --- " + string2);
                        }

//                            System.out.println(col_name);
//                        if(string.toLowerCase().startsWith("c:")) {
//                            System.out.println("TABLE_NAME::"+ table_name);
//                            System.out.println(string);
//                        }
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new TwoConnection().listPaths();
    }
}
