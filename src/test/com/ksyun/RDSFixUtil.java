package com.ksyun;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.*;
import com.ksyun.vm.service.RDSService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ZHANGNAN4 on 2014-5-22.
 * Email: zn.share@gmail.com
 */
public class RDSFixUtil extends BaseTest {


    final int userNameCell = 1;
    final int rdsNameCell = 3;
    final int rdsIdCell = 5;
    final int adminNameCell = 11;
    final int adminPasswodCell = 12;
    final int protCell = 13;


    @Test
    public void start() {
        readExcel("G:\\Work\\ksyun\\project\\RDS_FIX\\building47 2");
    }

    public void readExcel(String fileName) {
        InputStream inp = null;

        try {
            inp = new FileInputStream(fileName + ".xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(inp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        Row row = null;
        Cell cell = null;
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum() + 1; i++) {
            logger.info("i:" + i);
            row = sheet.getRow(i);
            String userName = null;
            String rdsName = null;
            String rdsId = null;
            String adminName = null;
            String adminPasswod = null;
            String prot = null;

            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
//                System.out.print(getCellValue(cell) + "|");
                switch (j) {
                    case userNameCell:
                        userName = StringUtils.trim(getCellValue(cell));
                        System.out.println("userNameCell:\t" + userName);
                        break;
                    case rdsNameCell:
                        rdsName = StringUtils.trim(getCellValue(cell));
                        System.out.println("rdsNameCell:\t" + rdsName);
                        break;
                    case rdsIdCell:
                        rdsId = StringUtils.trim(getCellValue(cell));
                        System.out.println("rdsIdCell:\t" + rdsId);
                        break;
                    case adminNameCell:
                        adminName = StringUtils.trim(getCellValue(cell));
                        System.out.println("adminNameCell:\t" + adminName);
                        break;
                    case adminPasswodCell:
                        adminPasswod = StringUtils.trim(getCellValue(cell));
                        System.out.println("adminPasswodCell:\t" + adminPasswod);
                        break;
                    case protCell:
                        prot = StringUtils.trim(getCellValue(cell));
                        System.out.println("protCell:\t" + prot);
                        break;
                    default:
                        break;
                }
            }

            try {
                checkNotNull(userName);
                checkNotNull(rdsName);
                checkNotNull(rdsId);
                checkNotNull(adminName);
                checkNotNull(adminPasswod);
                checkNotNull(prot);

                try {
                    List<RDSInstance> instances = rdsService.getInstanceList(userName);
                    logger.info("instances.size():" + instances.size());
                    for (RDSInstance instance : instances) {
                        logger.info("instance:" + ToStringBuilder.reflectionToString(i));
                        if (instance.getId().equals(rdsId)
                                && ("building".equalsIgnoreCase(instance.getStatus()) || "unactive".equalsIgnoreCase(instance.getStatus()))) {
                            rdsService.removeInstance(userName, rdsId);//删除原有RDS
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                RDSValidationPo rdsValidationPo = createRDS(rdsName, adminName, adminPasswod, prot, userName);//创建新RDS

                int sleep = 0;//等待新RDS创建完成
                long start = System.currentTimeMillis();
                String result = rdsValidationPo.getRdsId();
                while (true) {
                    RDSInstance rdsInstance = rdsService.getInstance(userName, rdsValidationPo.getRdsId());
                    if ("active".equalsIgnoreCase(rdsInstance.getStatus())) {
                        break;
                    }
                    Thread.sleep(1000);
                    System.out.println("Thread.sleep(1000*5):\t" + rdsValidationPo.getRdsId() + "\t" + userName);
                    sleep++;
                    if (sleep > 60 * 5) {
                        logger.error("创建RDS超时:" + sleep);
                        result += ":创建RDS超时";
                        break;
                    }
                }
                long end = System.currentTimeMillis();
                System.out.println("用时:\t" + (end - start) / 1000);

                cell = row.getCell(row.getLastCellNum() - 1);
                cell.setCellValue(result);
                FileOutputStream fos = new FileOutputStream(fileName + " result.xls");
                wb.write(fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取单元格内的数据值
     */
    private String getCellValue(Cell cell) {
        String str = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                str = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                str = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                str = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                str = String.valueOf((int) cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                str = cell.getStringCellValue();
                break;
            default:
                str = null;
                break;
        }
        return str;
    }

    public RDSValidationPo createRDS(String rdsName, String adminName, String adminPassword, String port, String userName) throws Exception {

        CreateInstance createInstance = new CreateInstance();
        InstancePo instancePo = new InstancePo();
        instancePo.setType("SG");
        instancePo.setName(rdsName);
        instancePo.setService_type("mysql");
        Extend extend = new Extend();
        extend.setAdmin_user(adminName);
        extend.setAdmin_password(adminPassword);
        extend.setPort(port);
        extend.setAutobackup_at("2300");//
        extend.setDuration("1440");//
        extend.setExpire_after("7");//
        instancePo.setExtend(extend);
        CreateFlavor createFlavor = new CreateFlavor();
        createFlavor.setDisk("5");
        createFlavor.setRam("512");
        createFlavor.setVcpus("1");
        instancePo.setFlavor(createFlavor);
        createInstance.setRds(instancePo);
        createInstance.setUser_id(userName);

        RDSValidationPo rdsValidationPo = rdsService.addRDS(createInstance);
        logger.info("rdsValidationPo:" + ToStringBuilder.reflectionToString(rdsValidationPo));
        return rdsValidationPo;
    }


    @Autowired
    private RDSService rdsService;

    protected static Logger logger = Logger.getLogger(RDSFixUtil.class);
}
