<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*,com.ksyun.vm.kpi.common.*" contentType="text/html"%>
<%!
    
Vector getKPI(String product) {
    if("track".equals(product))
        return getKPI(product, "KPI");
    return getKPI(product, "KPI");
}
Vector getKPI(String product, String dbLink) {
    String sql = getSql(product,null);
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Vector ret = null;
    try {
        con = MysqlConnectionPools.getConnection(dbLink);
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        ret = getData(rs, product);
        close(con, ps, rs);
    } catch(Exception e) { 
        e.printStackTrace();
        return null;
    }
    return ret;
}

Vector getData(ResultSet rs, String product) throws SQLException{
    Vector ret = new Vector(93);
    if(rs == null) return ret;
    int cols = getColNum(product);
    String[] one = null;
    int row = 1;
    while(rs.next()) {
        one = new String[cols];
        for(int i=0; i<cols; i++) {
        if("xiaonei".equals(product) && i==6  //xiaonei.p2p is float
            ) {
                float p2p = rs.getFloat(i+1);
                java.text.DecimalFormat df = new java.text.DecimalFormat("##.###");
                one[i] = df.format(p2p);
            } else if("rdecay".equals(product) && (i%2)==1) { //大富翁衰减率特殊颜色处理，产生阶梯效果
                String r = rs.getString(i+1);
                if(r != null) {
                    if(((row-((i+1)/2)) % 2) == 0) one[i] = "<font color=blue>" + r + "</font>";
                    else one[i] = "<font color=green>" + r + "</font>";
                }
            } else one[i] = rs.getString(i+1);
        }
        ret.add(one);
        row++;
    }
    return ret;
}

void close(Connection con,PreparedStatement pstmt,ResultSet rs) {
    try {
        if(rs != null) rs.close();
        if(pstmt != null) pstmt.close();
        if(con != null) con.close();
    } catch (SQLException e) {}
}

static String[] TEST_COLS = {"day","pv", "uv"};
static String[] PUBLIC_COLS = {"day","uv", "newuv_percentage","pv","trial_num","trial_success_num","cash_pay_num","cash_vm_num","cash_amount","coupon_pay_num","coupon_vm_num","coupon_amount"};
static String[] PUB_KVM_COLS = {"day","pv","uv","tenant","tenant_day","revenue","revenue_day","coupon_day","recharge","recharge_day","refund","refund_day","vm","vm_day","vm_revoke"};
static String[] PUB_OP_COLS = {"day","charge_uv","charge_sum","vm_close","vm_reboot","hdd_new","snap_new","snap_del","fw_new","fw_del","fw_rule_new","fw_rule_del"};
static String[] PUB_USER_COLS = {"day","uv","uv_oauth","new_register","user_sum"};


String[] getColsByPName(String product){
    String[] p = null;
    if("test".equals(product)) p = TEST_COLS;
    else if("public".equals(product)) p = PUBLIC_COLS;
    else if("pub_kvm".equals(product)) p = PUB_KVM_COLS;
    else if("pub_op".equals(product)) p = PUB_OP_COLS;
    else if("user".equals(product)) p = PUB_USER_COLS;
    return p;
}

int getColNum(String product) {
    int cols = 0;
    String[] p = getColsByPName(product);
    cols = p.length;
    return cols;
}

static String[] COLORS_ARRAY= {"0000FF","990066","003333","009999","FFCC33","FF66CC","A66EDD"};

String arrayToString(String [] array ,String split){
    if(array==null) return "";
    StringBuffer sb = new StringBuffer();
    for(int i=0;i<array.length;i++){
    sb.append(array[i]+split);
    }
    if(sb.length()>1&&!"".equals(split))sb.deleteCharAt(sb.length()-1);
    return sb.toString();
}

String getSql(String product,Hashtable params) {
    String sql = null;
    String  cols = null;
    if(params!=null){
        cols = arrayToString((String[])params.get("cols"),",");
        cols  += " , to_char(day,'yy/mm/dd') as day";
    }
    if("test".equals(product)) sql="SELECT " + (params==null?set2str(TEST_COLS):cols) + " FROM kpi_" + product;
    else if("public".equals(product)) sql="SELECT " + (params==null?set2str(PUBLIC_COLS):cols) + " FROM kpi_" + product;
    else if("pub_kvm".equals(product)) sql="SELECT " + (params==null?set2str(PUB_KVM_COLS):cols) + " FROM kpi_" + product;
    else if("pub_op".equals(product)) sql="SELECT " + (params==null?set2str(PUB_OP_COLS):cols) + " FROM kpi_" + product;
    else if("user".equals(product)) sql="SELECT " + (params==null?set2str(PUB_USER_COLS):cols) + " FROM kpi_" + product;

    String rollback = KPI.getDateString(93);  
    if(params==null)sql += " WHERE day>'" + rollback + "' ORDER BY day DESC";
    else sql += " where day between to_date('"+params.get("stime").toString()+"','yyyy-mm-dd') and to_date('"+params.get("etime").toString()+"','yyyy-mm-dd') ";
    System.out.println(sql);
    return sql;
}
String set2str(String[] set) {
    String ret = "";
    int i = 0;
    for(;i<set.length-1;i++) ret += set[i] + ",";
    ret += set[i];
    return ret;
}

String doDay(String[] one, int cols) {
    StringBuffer sb = new StringBuffer(1024);
    String day = one[0].substring(0,10);
    int day_of_week = 0;
    try {
        Calendar c = Calendar.getInstance();
        int idx1 = day.indexOf("-");
        int idx2 = day.indexOf("-", idx1+1);
        int year = Integer.parseInt(day.substring(0, idx1));
        int month = Integer.parseInt(day.substring(idx1+1, idx2));
        int date = Integer.parseInt(day.substring(idx2+1));
        c.set(year, month-1, date-1);
        day_of_week = c.get(c.DAY_OF_WEEK);
    } catch(Exception e) {
        e.printStackTrace();
    }
    switch(day_of_week) {
        case 2 :
        case 4 : sb.append("<tr align=center bgcolor='#F2F2F2'><td>"); break;
        case 6 : sb.append("<tr align=center bgcolor='#F2E2D2'><td>"); break;
        case 7 : sb.append("<tr align=center bgcolor='#E2E2D2'><td>"); break;
        default: sb.append("<tr align=center bgcolor='#FFFFFF'><td>"); break;
    }
    sb.append(day).append("_").append(day_of_week);
    sb.append("</td>");
    for(int j=1; j<cols; j++) sb.append("<td>").append(strNN(one[j])).append("</td>");
    sb.append("</tr>");
    return sb.toString();
}


//---------------------------- for web -------------------------
static Hashtable TITLE = new Hashtable(64);
static {
    TITLE.put("day", "日期");    
    TITLE.put("uv", "uv");
    TITLE.put("pv", "pv");
    
    TITLE.put("tenant", "付费租户总数");
    TITLE.put("tenant_day", "今日新增");
    TITLE.put("revenue", "今年总收入");
    TITLE.put("revenue_day", "今日收入");   
    TITLE.put("coupon_day", "今日代金券消耗");
    TITLE.put("vm", "虚拟机总数");
    TITLE.put("vm_day", "今日新增虚拟机");
    TITLE.put("vm_revoke", "今日回收虚拟机");   
    
    TITLE.put("charge_uv", "充值人UV");
    TITLE.put("charge_sum", "充值钱数");
    TITLE.put("vm_close", "虚拟机关闭次数");
    TITLE.put("vm_reboot", "虚拟机重启次数");   
    TITLE.put("hdd_new", "新建硬盘");
    TITLE.put("snap_new", "新建快照");
    TITLE.put("snap_del", "删除快照");
    TITLE.put("fw_new", "新建防火墙");   
    TITLE.put("fw_del", "删除防火墙");
    TITLE.put("fw_rule_new", "新建防火墙规则");
    TITLE.put("fw_rule_del", "删除防火墙规则");
    
    
    TITLE.put("newuv_percentage", "% New Visits");
    TITLE.put("trial_num", "试用申请");    
    TITLE.put("trial_success_num", "开通试用");
    TITLE.put("cash_pay_num", "现金购买用户数");
    TITLE.put("cash_vm_num", "台数");
    TITLE.put("cash_amount", "金额");   
    TITLE.put("coupon_pay_num", "代金券购买用户数");
    TITLE.put("coupon_vm_num", "台数");    
    TITLE.put("coupon_amount", "金额");
    
    
    TITLE.put("uv_oauth", "uv_oauth");
    TITLE.put("new_register", "今日新增用户");    
    TITLE.put("user_sum", "总用户数");    
    
    
    TITLE.put("recharge", "今年总充值");
    TITLE.put("recharge_day", "今日充值");
    TITLE.put("refund", "今年总退款");    
    TITLE.put("refund_day", "今日退款");   
    
    
}

String strNN(String src) {
    return src==null?"-":src;
}

String titleTD(String product) {
    String[] p = getColsByPName(product);

    String ret = "";
    for(int i=0; i<p.length; i++) {
        if(i == 0) ret += "<th width='120'><div class='th-gap'>";
        else ret += "<th><div class='th-gap'>";
        ret += "<span title='" + p[i] + "'>" + getTitle(p[i]) + "</span></div></th>";
    }
    return ret;
}
String getTitle(String src) {
    String ret = (String) TITLE.get(src);
    return (ret==null) ? src : ret;
}
%>
