package com.ksyun.vm.pojo.memdb;

import com.ksyun.vm.pojo.BasePo;

/**
 * Created by LiYang14 on 2014/9/16.
 */
public class InstancePOJO extends BasePo {

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getMaxmemory() {
        return maxmemory;
    }

    public void setMaxmemory(String maxmemory) {
        this.maxmemory = maxmemory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsedmemory() {
        return usedmemory;
    }

    public void setUsedmemory(String usedmemory) {
        this.usedmemory = usedmemory;
    }

    public DataFlavorPOJOPro getFlavor() {
        return flavor;
    }

    public void setFlavor(DataFlavorPOJOPro flavor) {
        this.flavor = flavor;
    }

    public DataStorePOJO getDatastore() {
        return datastore;
    }

    public void setDatastore(DataStorePOJO datastore) {
        this.datastore = datastore;
    }


    public FlavorPOJO getFlavordisplay() {
        return flavordisplay;
    }

    public void setFlavordisplay(FlavorPOJO flavordisplay) {
        this.flavordisplay = flavordisplay;
    }

    private String role;
    private String status;
    private String name;
    private String ip;
    private String id;
    private int port;
    private String maxmemory;
    private String usedmemory;

    private DataFlavorPOJOPro flavor;


    private FlavorPOJO flavordisplay;


    private DataStorePOJO datastore;

    public class DataStorePOJO extends BasePo {
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        String type = "";
        String version = "";
    }

    public class DataFlavorPOJOPro extends BasePo
    {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        String id="";
    }
}


