package com.ksyun.vm.utils.enumeration;

/**
 * Created by LiYang14 on 2014/9/21.
 */
import  java.util.List;
import   com.ksyun.vm.pojo.memdb.FlavorPOJO;
import com.alibaba.fastjson.JSONArray;
public class test {
    public  static  void  main(String[] args)
    {
        String content="[{\"id\":1,\"local_storage\":0,\"name\":\"proxy_0\",\"ram\":512},{\"id\":2,\"local_storage\":0,\"name\":\"data_0\",\"ram\":782},{\"id\":3,\"local_storage\":0,\"name\":\"data_1\",\"ram\":1465},{\"id\":4,\"local_storage\":0,\"name\":\"data_2\",\"ram\":2148},{\"id\":5,\"local_storage\":0,\"name\":\"data_3\",\"ram\":4196},{\"id\":6,\"local_storage\":0,\"name\":\"data_4\",\"ram\":10340}]";
        List<FlavorPOJO> list = JSONArray.parseArray(content, FlavorPOJO.class);
        System.out.println(list.get(0).getRam());
    }
}
