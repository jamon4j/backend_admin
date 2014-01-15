package com.ksyun.vm.kpi.common;

import java.sql.*;
import java.text.*;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class KPI {
	
	public static final Logger logger = Logger.getLogger(KPI.class);

    public static int submit(String productName, String fieldName, String day, int value) {
        return submit(productName, fieldName, day, String.valueOf(value));
    }

    public static int submit(String productName, String fieldName, String day, long value) {
        return submit(productName, fieldName, day, String.valueOf(value));
    }

    public static int submit(String productName, String fieldName, String day, float value) {
        return submit(productName, fieldName, day, String.valueOf(value));
    }

    /**
     * @return 0 - SUCC!
     */
    public static int submit(String productName, String fieldName, String day, String value) {
        return submit(productName, fieldName, day, String.valueOf(value), "----","update");
    }
    
    public static int submitInc(String productName, String fieldName, String day, String value) {
        return submit(productName, fieldName, day, String.valueOf(value), "----","inc");
    }

    public static int submit(String productName, String fieldName, String day, String value, String src,String op) {
        String sqlQ = "SELECT count(*) FROM kpi_" + productName + " WHERE day='" + day + "'";
        String sqlI = "INSERT INTO kpi_" + productName + "(day) VALUES('" + day + "')";
        String sqlU = "UPDATE kpi_" + productName + " SET " + fieldName + "=" + value + " WHERE day='" + day + "'";
        if (op.equals("inc"))
        {
        	sqlU = "UPDATE kpi_" + productName + " SET " + fieldName + "=" + fieldName + "+" + value + " WHERE day='" + day + "'";	
        }

        Connection con = null;
        PreparedStatement psQ = null, psI = null, psU = null;
        ResultSet rsQ = null;
        boolean needInsert = true;
        try {
            con = MysqlConnectionPools.getConnection("KPI");
            if (con == null) {
                System.err.println("KPI.submit got DB Connection failed!!");
                return -1;
            }
            con.setAutoCommit(false);
            psQ = con.prepareStatement(sqlQ);
            rsQ = psQ.executeQuery();
            if (rsQ.next()) {
                int count = rsQ.getInt(1);
                if (count > 0) needInsert = false;
            }

            if (needInsert) {
                psI = con.prepareStatement(sqlI);
                psI.executeUpdate();
            }

            psU = con.prepareStatement(sqlU);
            psU.executeUpdate();

            con.commit();
            con.setAutoCommit(true);
            close(null, psI, null);
            close(null, psU, null);
            String logstr = "KPI: p=" + productName + " f=" + fieldName + " day=" + day + " v=" + value + " src=" + src;
            logger.info(logstr);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (Exception er) {
                er.printStackTrace();
            }
            return -1;
        } finally {
            close(con, psQ, rsQ);
        }
        return 0;
    }
    

    /*
     * 求某个字段的一年总和
     */
    public static int sumbyYear(String productName, String fieldName, String fieldTarget , String year, String day,String op) 
    {
    	String yearB = "\"" + year+"-00-00\"";
    	String yearE = "\"" + year+"-12-31\"";
    	day = "\"" + day + "\"";
    	
    	String sqlGetSum = "SELECT sum(" + fieldName + ") FROM kpi_" + productName + " WHERE day between " + yearB + " and " + yearE;
    	String sqlGetYesterday = null;
    	String sqlUpdate = "UPDATE kpi_" + productName + " SET " + fieldTarget + "=%s  WHERE day=" + day;
    	
    	
//    	if (op.equals("inc")) 
//    	{
//    		String today = "\"" + yesterday() + "\"";
//    		String yesterday = "\"" + yesterdayBefore() +"\"";
//    		
//    		sqlGetSum = "SELECT "+ fieldName  +" FROM kpi_" + productName + " WHERE day=" + today;     		
//    		sqlGetYesterday = "SELECT "+ fieldTarget  +" FROM kpi_" + productName + " WHERE day=" + yesterday;
//    	}
    	
    	
    	if (op.equals("inc")) 
    	{
    		String today = day;
    		String yesterday = "\"" + yesterday() +"\"";
    		
    		sqlGetSum = "SELECT "+ fieldName  +" FROM kpi_" + productName + " WHERE day=" + today;     		
    		sqlGetYesterday = "SELECT "+ fieldTarget  +" FROM kpi_" + productName + " WHERE day=" + yesterday;
    	}
    	
    	Connection con = null;
    	PreparedStatement psG=null, psU = null, psGY = null; 
        ResultSet rsQ = null;
    	try{
            con = MysqlConnectionPools.getConnection("KPI");    		
            con.setAutoCommit(false);
            
            //System.out.println("sqlGetSum="+sqlGetSum);
            
            psG = con.prepareStatement(sqlGetSum);
            rsQ = psG.executeQuery();
            if (rsQ.next()) {
                double sum = rsQ.getDouble(1);
                //System.out.println("get sum=" + sum);
                
                if (op.equals("inc"))
                {
                	//System.out.println("sqlGetYesterday = " + sqlGetYesterday);
                	psGY = con.prepareStatement(sqlGetYesterday);
                	rsQ = psGY.executeQuery();
                    if (rsQ.next()) {
                    	 double yValue = rsQ.getDouble(1);	                   	 
                    	 //System.out.println("yValue = "+yValue);                   	 
                    	 sum = sum + yValue;
                    }
                    
                }
                
                sqlUpdate = String.format(sqlUpdate, sum);
                //System.out.println("sqlUpdate="+sqlUpdate);
                
                psU = con.prepareStatement(sqlUpdate);
                int res = psU.executeUpdate();
                //System.out.println("sqlUpdate result="+res);
                
                con.commit();                
                if(res>=0) return 0;                	               
                return res;
            }
            
            
    	}catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (Exception er) {
                er.printStackTrace();
            }
            return -1;
        } finally {
            close(con, psG, rsQ);
        }
    	
    	return 0;
    }
    

    /** 对于联合主键，需要设置多个primaryKey */
    public static int submit2(String productName,
                              String pkName1, String pkVal1,
                              String pkName2, String pkVal2,
                              String fieldName, String fieldValue,
                              String src) {
        String sqlQ = "SELECT count(*) FROM kpi_" + productName + " WHERE " + pkName1 + "='" + pkVal1 + "' AND " + pkName2 + " ='" + pkVal2 + "'";
        String sqlI = "INSERT INTO kpi_" + productName + "(" + pkName1 + "," + pkName2 + ") VALUES('" + pkVal1 + "','" + pkVal2 + "')";
        String sqlU = "UPDATE kpi_" + productName + " SET " + fieldName + "=" + fieldValue + " WHERE "+pkName1+"='" + pkVal1 + "' AND " + pkName2 + "='" + pkVal2 + "'";

        Connection con = null;
        PreparedStatement psQ = null, psI = null, psU = null;
        ResultSet rsQ = null;
        boolean needInsert = true;
        try {
            con = MysqlConnectionPools.getConnection("KPI");
            if (con == null) {
                System.err.println("KPI.submit got DB Connection failed!!");
                return -1;
            }
            con.setAutoCommit(false);
            psQ = con.prepareStatement(sqlQ);
            rsQ = psQ.executeQuery();
            if (rsQ.next()) {
                int count = rsQ.getInt(1);
                if (count > 0) needInsert = false;
            }

            if (needInsert) {
                System.out.println(sqlI);
                psI = con.prepareStatement(sqlI);
                psI.executeUpdate();
            }

            System.out.println(sqlU);
            psU = con.prepareStatement(sqlU);
            psU.executeUpdate();

            con.commit();
            con.setAutoCommit(true);
            close(null, psI, null);
            close(null, psU, null);
            String logstr = "KPI: p=" + productName + ":" + pkName1 + "=" + pkVal1 + ";"
                            + pkName2 + "=" + pkVal2 + ";" + fieldName + "=" + fieldValue + " src=" + src;
            logger.info(logstr);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (Exception er) {
                er.printStackTrace();
            }
            return -1;
        } finally {
            close(con, psQ, rsQ);
        }
        return 0;
    }

    public static int selectCount(String dbName, String sql, String src) {
        if(sql == null || !sql.startsWith("SELECT ")) return -2012;
        int count = 0;
        Connection con = null;
        PreparedStatement psQ = null;
        ResultSet rsQ = null;
        try {
            con = MysqlConnectionPools.getConnection(dbName);
            if (con == null) {
                System.err.println("KPI.submit got DB Connection failed!! dbName=" + dbName);
                return -1;
            }
            psQ = con.prepareStatement(sql);
            rsQ = psQ.executeQuery();
            if (rsQ.next()) {
                count = rsQ.getInt(1);
            }

            String logstr = "dbName=" + dbName + " sql=" + sql + " src=" + src;
            logger.info(logstr);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (Exception er) {
                er.printStackTrace();
            }
            return -1;
        } finally {
            close(con, psQ, rsQ);
        }
        return count;
    }

    // ----------------------  工具函数 ------------------------------
    public static String today() {
        return getDateString(0);
    }

    public static String yesterday() {
        return getDateString(1);
    }
    
    
    public static String yesterdayBefore() {
        return getDateString(2);
    }
    
    public static String getYear()
    {
    	java.util.Date d = new java.util.Date();
    	return new SimpleDateFormat("yyyy").format(d);
    }
    

    /**
     * 获取回滚N天的日期字符串
     */
    public static String getDateString(int rollback) {
        java.util.Date d = new java.util.Date(System.currentTimeMillis() - 3600L * 1000L * 24L * rollback - 7L * 60L * 1000L);
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    //关闭连接等
    public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();

        } catch (SQLException e) {
        }
    }

    public static void main(String[] args) throws Exception {
    	//sumbyYear("pub_kvm", "revenue_day", "revenue" , "2013","2013-12-23","inc");
    	System.out.println(yesterdayBefore());
    }
//        if (args == null || args.length < 1) {
//            System.out.println("Usage: java com.ipower.tools.KPI {cmd} {args...}");
//            System.out.println("\ttoday\t\t(get today's date string)");
//            System.out.println("\tyesterday\t(get yesterday's date string)");
//            System.out.println("\tsubmit {priduct_name} {field_name} {date string} {value} [src]\t(submit a value to table)");
//            //System.out.println("\tsubmit2 {priduct_name} {field1} {val1} {field2} {val2} {field} {value} {src}\t(submit2 a value to table)");
//            System.out.println("\tselectCount {dbName} {sql} {src}\t(count a sql)");
//            return;
//        }
//        if ("today".equals(args[0])) {
//            System.out.println(today());
//        } else if ("yesterday".equals(args[0])) {
//            System.out.println(yesterday());
//        } else if ("submit".equals(args[0])) {
//            if (args.length >= 7)
//                submit(args[1], args[2], args[3], args[4], args[5],args[6]);
//            else
//                submit(args[1], args[2], args[3], args[4]);
//        } else if("submit2".equalsIgnoreCase(args[0])){
//            submit2(args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
//        } else if("selectCount".equals(args[0])) {
//            int c = selectCount(args[1], args[2], args[3]);
//            System.out.println("selectCount " + c);
//        }
//    }
}

