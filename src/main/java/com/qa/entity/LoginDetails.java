package com.qa.entity;

import lombok.Data;
import uk.co.jemos.podam.common.PodamStringValue;

@Data
public class LoginDetails {
    @PodamStringValue(strValue = "Admin")
    private String userName;

    @PodamStringValue(strValue = "admin123")
    private String passWord;
    /**
     * Function to connect to IBM DB2 database. Requires a .propeties file with
     * values URL, Username and Password URL is the connection string like
     * jdbc:db2://cpaiqaeb.homedepot.com:50123/DQ4012SA
     *
     * @param sqlQuery
     *            Your SQL query string like
     *            "select ATTRNAME from ACATTR where ACATTR_ID = 10002"
     * @return Returns an ArrayList with values
     */
    public ArrayList<HashMap<String, Object>> connectDB2DatabaseGetRowSet(
            String sqlQuery) {
        ArrayList<HashMap<String, Object>> list = null;
        try {
            Properties prop = new Properties();
            // Load the DB2(R) Universal JDBC Driver with DriverManager
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            //prop.load(new FileInputStream("dbconfig.properties"));
            //Class.forName("com.ibm.db2.jcc.DB2Driver");
                    /*String strURL = (String) prop.get("URL");
                    String strUsername = (String) prop.get("Username");
                    String strPassword = (String) prop.get("Password");*/

            String strURL = dataTable.getData("WCS_DB_URL");
            String strUsername = dataTable.getData("WCS_DB_Username");
            String strPassword = dataTable.getData("WCS_DB_Password");

            Connection con = DriverManager.getConnection(strURL, strUsername,
                    strPassword);
            if (!con.isClosed())
            {
                System.out.println("Successfully connected to MySQL server");
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            report.updateTestLog("Excuete the query <b>"+sqlQuery+"</b> in DB", "Mentioned query is excueted", Status.DONE);

            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            list = new ArrayList<HashMap<String, Object>>(50);
            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<String, Object>(
                        columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
