<%!
    //最高管理权限: 审核、管理、查阅KPI
    String L0 = ",999,";
    //可以审核、基本管理
    String L10 = "";
    //只可以基本管理
    String L100 = "";
    //单独的KPI查阅权限
    String Lkpi = "";

    boolean isBasic(int uid) {
        if (uid < 2) return false;
        if (L100.indexOf("," + uid + ",") >= 0) return true;
        return false;
    }

    boolean isAudit(int uid) {
        if (uid < 2) return false;
        if (L10.indexOf("," + uid + ",") >= 0) return true;
        return false;
    }

    boolean isSuper(int uid) {
        if (uid < 2) return false;
        if (L0.indexOf("," + uid + ",") >= 0) return true;
        return false;
    }

    boolean isKPI(int uid) {
        if (uid < 2) return false;
        if (Lkpi.indexOf("," + uid + ",") >= 0) return true;
        return false;
    }

%>
