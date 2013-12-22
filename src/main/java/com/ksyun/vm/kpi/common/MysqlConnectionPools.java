package com.ksyun.vm.kpi.common;

import java.sql.*;

import javax.sql.DataSource;


public class MysqlConnectionPools {
	
	  private static DataSource dataSource = null;
	  
	  static{
		  init(); 
	  }
	
	  public static Connection getConnection(String Poolname) throws SQLException {
		   return dataSource.getConnection();
	  }
	  
	  private static void init()
	  {
		  //dataSource = (DataSource) DaoFactory.getBean("dataSource_kpi");  
		  dataSource = (DataSource) DaoFactory.getBean("dataSource");  
	  }
	
	
//    public static boolean DEBUG = false;
//    public static int DBCONN_GET_COUNT = 0;
//	public static int DBCONN_PRINT_FACTOR = 32;
//	public static Logger logger = Logger.getLogger(MysqlConnectionPools.class);
//
//	static {
//		String path = MysqlConnectionPools.class.getPackage().getName()
//				.replaceAll("\\.", "/") + "/MySQLConfig.xml";
//		
//		
//		System.out.println(path);
//		
//		URL url = null;
//		try {
//			if (Thread.currentThread().getContextClassLoader() != null) {
//				url = Thread.currentThread().getContextClassLoader().getResource(path);
//			}
//			if (url == null) {
//				url = MysqlConnectionPools.class.getClassLoader().getResource(path);
//			}
//			if (url == null) {
//				url = MysqlConnectionPools.class.getClassLoader().getResource("/" + path);
//			}
//			if (url != null) {
//                System.out.println("INFO: MysqlConnectionPools init by: " + url);
//				JAXPConfigurator.configure(url.getFile(), false);
//			} else {
//                System.err.println("ERROR: " + path + " not found!");
//            }
//		} catch (Exception e) {
//			InputStream in = null;
//			BufferedReader reader = null;
//			try {
//				in = MysqlConnectionPools.class.getClassLoader().getResourceAsStream("/"+path);
//				reader= new BufferedReader(new InputStreamReader(in));
//				JAXPConfigurator.configure(reader, false);
//			} catch (Exception ex) {
//				in = MysqlConnectionPools.class.getClassLoader().getResourceAsStream(path);
//				reader= new BufferedReader(new InputStreamReader(in));
//				try {
//					JAXPConfigurator.configure(reader, false);
//				} catch (ProxoolException e1) {
//					e1.printStackTrace();
//				}
//			} finally {
//				if(reader != null) {
//					try {
//						reader.close();
//					} catch (Exception ex) {
//						ex.printStackTrace();
//					}
//
//				}
//			}
//		}
//		try {
//			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//    public static void close(Connection conn , Statement ps) {
//        close(conn,ps,null);
//    }
//
//    public static void close(Connection conn , Statement ps , ResultSet rs){
//        try{
//            if(rs != null) rs.close();
//            if(ps != null) ps.close();
//            if(conn != null) conn.close();
//        }catch(SQLException e){
//            e.printStackTrace();
//            //System.err.println(e);
//        }
//
//    }
//	public static void main(String[] v) {
//		DEBUG = true;
//		Connection con = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		if (true) {
//			con = MysqlConnectionPools.getConnection("KPI");
//			if (con == null) {
//				System.out.println("the con is null");
//			} else {
//				System.out.println("ok");
//			}
//		}
//		try {
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("select count(*) from kpi_x");
//			while(rs.next()) {
//				System.out.println("kpi_x\t" + rs.getInt(1));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				con.close();
//			} catch (Exception e) {
//			}
//
//		}
//
//	}
//
//
//    public static Connection getConnection(String Poolname) {
//		DBCONN_GET_COUNT++;
//		Connection conn = null;
//		try {
//			conn = DriverManager.getConnection("proxool." + Poolname);
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			logger.error("main_DB connection failed");
//			try {
//				conn = DriverManager.getConnection("proxool."+Poolname);
//			} catch (SQLException e2) {
//				e2.printStackTrace();
//				logger.error("backup_DB connection failed");
//			}
//		} finally {
//			if(DEBUG || (DBCONN_GET_COUNT%DBCONN_PRINT_FACTOR) == 0 ) {
//				getConns(Poolname);
//			}
//		}
//		return conn;
//	}
//
//    public static void getConns(String Poolname) {
//		try {
//			SnapshotIF snapshot = ProxoolFacade.getSnapshot(Poolname, true);
//			int curActiveCount = snapshot.getActiveConnectionCount();// 获得活动连接数
//			int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数
//			int maxCount = snapshot.getMaximumConnectionCount();// 获得总连接数
//			//long connectionCount =  snapshot.getConnectionCount();
//			//long refusedCount =  snapshot.getRefusedCount();
//			//long servedCount = snapshot.getServedCount();
//			System.out.println("ConnPool[" + Poolname + "]: Act=" + curActiveCount
//					+ " ; Tot=" + availableCount + " ; Max=" + maxCount);
//			//System.out.println(curActiveCount + " ( curActiveCount )  " + availableCount+ " ( availableCount )  " + maxCount + " (max) ");
//			//System.out.println(connectionCount + " ( connectionCount ) " + refusedCount+ "( refusedCount )  " + ServedCount + " ( servedCount ) ");
//		} catch (ProxoolException e) {
//			e.printStackTrace();
//		}
//	}

}
