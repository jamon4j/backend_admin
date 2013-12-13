<%@ page import="java.sql.*, java.util.*" %>
<%!
    String getChart_list(String product) {
        String[] p = getColsByPName(product);

        String ret = "";
        for (int i = 1; i < p.length; i++) {
            ret += "<input type='checkbox' name='chart_select' value='" + p[i] + "'>" + getTitle(p[i]) + "</option>&nbsp;&nbsp;";
        }
        return ret;

    }

    int getColumn_Array(String product, String col) {
        String[] p = getColsByPName(product);

        int column = -1;
        for (int i = 0; i < p.length; i++) {
            if (p[i].equals(col)) {
                column = i;
                break;
            }
        }
        return column;
    }
%>