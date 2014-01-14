package com.ksyun.vm.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.stereotype.Component;

import com.ksyun.monitor.pojo.hbase.HBaseCell;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.Tools;

/**
 * User: liuchuandong Date: 13-11-6 Time: 上午11:01 Func:
 */
@Component
public class HBaseDao {

	private static Configuration conf = null;

	/**
	 * 初始化配置
	 */
	static {
		conf = HBaseConfiguration.create();
	}

	/**
	 * 创建表操作
	 * 
	 * @throws java.io.IOException
	 */
	public void createTable(String tablename, String[] columns, boolean deleteAndCreate) throws IOException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tablename)) {
			if (deleteAndCreate) {
				admin.disableTable(tablename);
				admin.deleteTable(tablename);
				System.out.println(tablename + " is exist,detele....");
			} else {
				System.out.println("表已经存在！");
			}
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(tablename);
			for (int i = 0; i < columns.length; i++) {
				tableDesc.addFamily(new HColumnDescriptor(columns[i]));
			}
			admin.createTable(tableDesc);
			System.out.println("表创建成功！");
		}
	}

	/**
	 * 删除表操作
	 * 
	 * @param tablename
	 * @throws IOException
	 */
	public void deleteTable(String tablename) throws IOException {
		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			admin.disableTable(tablename);
			admin.deleteTable(tablename);
			System.out.println("表删除成功！");
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入一行记录
	 * 
	 * @param tablename
	 * @param row
	 */
	public void writeCell(String tablename, HBaseCell cell) {
		try {
			HTable table = new HTable(conf, tablename);
			Put put = new Put(cell.getRowkey().getBytes());
			put.add(cell.getFamily().getBytes(), cell.getQulifer().getBytes(), cell.getValue().getBytes());
			table.put(put);
			System.out.println("写入成功！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入多行记录
	 * 
	 * @param tablename
	 * @param list
	 */
	public void writeMultCell(String tablename, List<HBaseCell> list) {
		try {
			HTable table = new HTable(conf, tablename);
			List<Put> lists = new ArrayList<Put>();
			for (HBaseCell cell : list) {
				Put put = new Put(cell.getRowkey().getBytes());
				if(cell.getTimestamp() != null && StringUtils.isNumeric(cell.getTimestamp())){
					put.add(cell.getFamily().getBytes(), cell.getQulifer().getBytes(), Long.valueOf(cell.getTimestamp()), cell.getValue().getBytes());
				}else{
					put.add(cell.getFamily().getBytes(), cell.getQulifer().getBytes(), cell.getValue().getBytes());
				}
				
				lists.add(put);
			}
			table.put(lists);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除一行记录
	 * 
	 * @param tablename
	 * @param rowkey
	 * @throws IOException
	 */
	public void deleteRow(String tablename, String rowkey) throws IOException {
		HTable table = new HTable(conf, tablename);
		List list = new ArrayList();
		Delete d1 = new Delete(rowkey.getBytes());
		list.add(d1);
		table.delete(list);
	}

	/**
	 * 查找一行记录
	 * 
	 * @param tablename
	 * @param rowKey
	 */
	public static List<HBaseCell> selectRow(String tablename, String rowKey) throws IOException {
		HTable table = new HTable(conf, tablename);
		Get g = new Get(rowKey.getBytes());
		Result rs = table.get(g);
		HBaseCell cell = new HBaseCell();
		List<HBaseCell> list = new ArrayList<HBaseCell>();
		for (KeyValue kv : rs.raw()) {
			cell.setRowkey(new String(kv.getRow()));
			cell.setFamily(new String(kv.getFamily()));
			cell.setQulifer(new String(kv.getQualifier()));
			cell.setValue(new String(kv.getValue()));
			cell.setTimestamp(kv.getTimestamp() + "");
			list.add(cell);
		}
		return list;
	}

	/**
	 * 查询表中所有行
	 * 
	 * @param tablename
	 */
	public List<Map<String, HBaseCell>> scaner(String tablename, String startRowKey, String stopRowKey) {
/*		List<HBaseCell> list = null;
	//	CollatorComparator comparator = new CollatorComparator();
		List<List<HBaseCell>> resultList = new ArrayList<List<HBaseCell>>();
		try {
			HTable table = new HTable(conf, tablename);
			Scan s = new Scan();
		//	s.setMaxVersions();
			if(startRowKey != null && stopRowKey != null){
				s.setStartRow(startRowKey.getBytes());
				s.setStopRow(stopRowKey.getBytes());
			}
			ResultScanner rs = table.getScanner(s);
			for (Result r : rs) {
				list = new ArrayList<>();
				KeyValue[] kvs = r.raw();
				for (KeyValue kv : kvs) {
					HBaseCell cell = new HBaseCell();
					cell.setRowkey(new String(kv.getRow()));
					cell.setFamily(new String(kv.getFamily()));
					cell.setQulifer(new String(kv.getQualifier()));
					cell.setValue(new String(kv.getValue()));
					cell.setTimestamp(kv.getTimestamp() + "");
					list.add(cell);
				}
				resultList.add(list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultList;*/
		
		
		Map<String, HBaseCell> rowMap = null;
		//	CollatorComparator comparator = new CollatorComparator();
			List<Map<String, HBaseCell>> resultList = new ArrayList<Map<String, HBaseCell>>();
			try {
				HTable table = new HTable(conf, tablename);
				Scan s = new Scan();
			//	s.setMaxVersions();
				if(startRowKey != null && stopRowKey != null){
					s.setStartRow(startRowKey.getBytes());
					s.setStopRow(stopRowKey.getBytes());
				}
				ResultScanner rs = table.getScanner(s);
				for (Result r : rs) {
					rowMap = new HashMap<>();
					KeyValue[] kvs = r.raw();
					for (KeyValue kv : kvs) {
						HBaseCell cell = new HBaseCell();
						cell.setRowkey(new String(kv.getRow()));
						cell.setFamily(new String(kv.getFamily()));
						cell.setQulifer(new String(kv.getQualifier()));
						cell.setValue(new String(kv.getValue()));
						cell.setTimestamp(kv.getTimestamp() + "");
						rowMap.put(cell.getQulifer(), cell);
					}
					resultList.add(rowMap);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return resultList;
	}

	
	public static void main(String[] args) throws IOException {
		HBaseDao hbaseDao = new HBaseDao();
	//	hbaseDao.deleteTable("vm_table_status");
	//	hbaseDao.deleteTable(InitConst.MONITOR_HBASE_TABLE_NETWORK+"_private");
	//	hbaseDao.deleteTable("vm_table_load");
	//	hbaseDao.deleteTable("vm_table_disk");
	//	baseDao.createTable(InitConst.MONITOR_HBASE_TABLE_LOAD, new String[] { "col1", "col2" }, true);

	/*	List<HBaseCell> list = new ArrayList<HBaseCell>();
		for(int i=0;i<5;i++){
			HBaseCell row = new HBaseCell();
			row.setRowkey("ddddd_20131212");
			row.setFamily("col1");
			row.setValue("123");
			row.setQulifer("status"+i);
			list.add(row);
		}
		
		for(int i=0;i<5;i++){
			HBaseCell row = new HBaseCell();
			row.setRowkey("ddddd_20131213");
			row.setFamily("col2");
			row.setValue("123");
			row.setQulifer("status"+i);
			list.add(row);
		}
		
		for(int i=0;i<5;i++){
			HBaseCell row = new HBaseCell();
			row.setRowkey("ddddd_20131214");
			row.setFamily("col2");
			row.setValue("123");
			row.setQulifer("status"+i);
			list.add(row);
		}

		HBaseDao dao = new HBaseDao();
		dao.writeMultCell("test", list);*/

		/*HTable table = new HTable(conf, "test");
		Put put = new Put(row.getRowkey().getBytes());
		put.add(row.getFamily().getBytes(), row.getQulifer().getBytes(), date.getTime(), row.getValue().getBytes());
		Put put1 = new Put(row.getRowkey().getBytes());
		put1.add(row1.getFamily().getBytes(), row1.getQulifer().getBytes(), date.getTime(), row1.getValue().getBytes());
		List<Put> puts = new ArrayList<>();
		puts.add(put);
		puts.add(put1);
		table.put(puts);*/
		
		
		
//		MonitorVmDiskPo po1 = new MonitorVmDiskPo();
//		po1.setDisk("vda");
//		po1.setLogTime("1387517225");
//		po1.setReadBytes("123");
//		po1.setReadTimes("123");
//		po1.setVmName("test");
//		po1.setVmUuid("111111");
//		po1.setWriteBytes("123");
//		po1.setWriteTimes("123");
//		
//		MonitorVmDiskPo po2 = new MonitorVmDiskPo();
//		po2.setDisk("vdb");
//		po2.setLogTime("1387517225");
//		po2.setReadBytes("123");
//		po2.setReadTimes("123");
//		po2.setVmName("test");
//		po2.setVmUuid("111111");
//		po2.setWriteBytes("123");
//		po2.setWriteTimes("123");
//		
//		
//		MonitorVmDiskPo po3 = new MonitorVmDiskPo();
//		po3.setDisk("vda");
//		po3.setLogTime("1387517236");
//		po3.setReadBytes("123");
//		po3.setReadTimes("123");
//		po3.setVmName("test");
//		po3.setVmUuid("111111");
//		po3.setWriteBytes("123");
//		po3.setWriteTimes("123");
//		
//		MonitorVmDiskPo po4 = new MonitorVmDiskPo();
//		po4.setDisk("vda");
//		po4.setLogTime("1387517236");
//		po4.setReadBytes("123");
//		po4.setReadTimes("123");
//		po4.setVmName("test");
//		po4.setVmUuid("111111");
//		po4.setWriteBytes("123");
//		po4.setWriteTimes("123");
//		
//		
//		HBaseCell cell1 = new HBaseCell();
//		cell1.setRowkey(po1.getVmUuid()+"_"+po1.getLogTime());
//		cell1.setFamily("body");
//		cell1.setTimestamp(po1.getLogTime());
//		cell1.setValue(JSON.toJSONString(po1));
//		cell1.setQulifer(po1.getDisk());
//		
//		
//		HBaseCell cell2 = new HBaseCell();
//		cell2.setRowkey(po2.getVmUuid()+"_"+po2.getLogTime());
//		cell2.setFamily("body");
//		cell2.setTimestamp(po2.getLogTime());
//		cell2.setValue(JSON.toJSONString(po2));
//		cell2.setQulifer(po2.getDisk());
//		
//		HBaseCell cell3 = new HBaseCell();
//		cell3.setRowkey(po3.getVmUuid()+"_"+po3.getLogTime());
//		cell3.setFamily("body");
//		cell3.setTimestamp(po3.getLogTime());
//		cell3.setValue(JSON.toJSONString(po1));
//		cell3.setQulifer(po3.getDisk());
//		
//		
//		HBaseCell cell4 = new HBaseCell();
//		cell4.setRowkey(po4.getVmUuid()+"_"+po4.getLogTime());
//		cell4.setFamily("body");
//		cell4.setTimestamp(po4.getLogTime());
//		cell4.setValue(JSON.toJSONString(po1));
//		cell4.setQulifer(po4.getDisk());
//		
//		
//		List<HBaseCell> list = new ArrayList<HBaseCell>();
//		list.add(cell1);
//		list.add(cell2);
//		list.add(cell3);
//		list.add(cell4);
//		hbaseDao.createTable("vm_table_disk", new String[] { "body"}, false);
//		hbaseDao.writeMultCell("vm_table_disk", list);
//		
//		
//		Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE,-1);
//        String startTime = String.valueOf(c.getTimeInMillis()/1000);
//        String endTime = String.valueOf(new Date().getTime()/1000);
//        System.out.println(startTime + " " + endTime);
//		System.out.println(hbaseDao.scaner("vm_table_disk","111111_"+startTime,"111111_"+endTime));
		
		Calendar c = Calendar.getInstance();
    	c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        System.out.println(startTime);
		String vmuuid="2bb345a2-4dae-4dce-8be4-17760e98cd6f";
		System.out.println(Tools.makeRowKey(vmuuid, startTime));
		System.out.println(Tools.makeRowKey(vmuuid, String.valueOf(new Date().getTime()/1000)));
		List<Map<String, HBaseCell>>  result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_LOAD+"_private", Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
		System.out.println(result);
	}
}
