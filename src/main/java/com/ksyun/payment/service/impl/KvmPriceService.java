package com.ksyun.payment.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ksyun.payment.common.KvmBean;
import com.ksyun.payment.common.RestapiRet;
import com.ksyun.payment.service.IKvmPriceService;


@Service
public class KvmPriceService implements IKvmPriceService {
	
	//kvm价格，暂时写到程序里面，最终确定后从数据库中获取(没有更好的办法统一处理)
	private static Map<String, Number> kvmPriceMap;
	
	static {
		kvmPriceMap = new HashMap<String, Number>();
		kvmPriceMap.put("cpu_2", 70);
		kvmPriceMap.put("cpu_4", 140);
		kvmPriceMap.put("cpu_8", 280);
		kvmPriceMap.put("cpu_16", 560);

	}

	@Override
	public RestapiRet calcPrice(KvmBean kbean) {
		
		RestapiRet ret = new RestapiRet();
		int monthCount = 1;
		int buyCount = 1;
		int cpu = kbean.getCpu();
		double mem = kbean.getMem();
		int net =  kbean.getNet();
		int ebs = kbean.getEbs();
		short productType = kbean.getProductType();
		//String os = kbean.getOs();
		
		//数据验证
		boolean verifyFlag = verifyData(productType, monthCount, buyCount, cpu, mem, ebs, net, ret);
		if(!verifyFlag) {
			return ret;
		}
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		if(productType == 1) {
			//计算cpu的花费
			if(cpu == 1) {
				if(mem < 2) {
					totalPrice = totalPrice.add(new BigDecimal(15));
				} else {
					totalPrice = totalPrice.add(new BigDecimal(35));
				}
			} else {
				totalPrice = totalPrice.add(new BigDecimal(kvmPriceMap.get(String.format("cpu_%d", cpu)).intValue()));
			}
			//计算内存花费
			if(cpu == 1 && mem < 2) {
				totalPrice = totalPrice.add(new BigDecimal(mem * 40));
			} else {
				totalPrice = totalPrice.add(new BigDecimal(mem * 50));
			}
			
			
			int netPrice = calcNetPrice(net);
			
			totalPrice = totalPrice.add(new BigDecimal(netPrice));
		}
		
		//计算ebs的价格
		BigDecimal ebsPrice = new BigDecimal(0.333 * ebs);
		totalPrice = totalPrice.add(ebsPrice);
		//计算网络带宽价格
		Map<String, Object> map = new HashMap<String, Object>();
		totalPrice = totalPrice.multiply(new BigDecimal(monthCount * buyCount));
		totalPrice = totalPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
		
		ebsPrice = ebsPrice.multiply(new BigDecimal(monthCount * buyCount));
		ebsPrice = ebsPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
		if(totalPrice.compareTo(BigDecimal.ZERO) <= 0 || totalPrice.compareTo(new BigDecimal(Integer.MAX_VALUE) ) >= 0) {
			ret.setRet(-1001);
			ret.setMsg("the price is error");
			return ret;
		}
		
		int freeMonth = monthCount/6;
		
		BigDecimal kvmMonthPrice = totalPrice;
		totalPrice = kvmMonthPrice.multiply(new BigDecimal(kbean.getMonth())).add(kvmMonthPrice.multiply(new BigDecimal(kbean.getDay()).divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_DOWN)));
		BigDecimal ebsMonthPrice = ebsPrice;
		ebsPrice = ebsMonthPrice.multiply(new BigDecimal(kbean.getMonth())).add(ebsMonthPrice.multiply(new BigDecimal(kbean.getDay()).divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_DOWN)));
		
		map.put("totalPrice", totalPrice.intValue());
		map.put("freeMonth", freeMonth);
		map.put("ebsPrice", ebsPrice.intValue());
		ret.addToList(map);
		
		ret.setRet(0);
		ret.setMsg("success");
		return ret;
	}
	
	@Override
	public RestapiRet calcEbsPrice(KvmBean kbean) {
		RestapiRet ret = new RestapiRet();
		int ebs = kbean.getEbs();
		int monthCount = 1;
		int buyCount = 1;
		if(ebs > 2000) {
			ret.setRet(-1006);
			ret.setMsg("the disk is too large");
			return ret;
		}
		if(monthCount <= 0 || monthCount >= 1000) {
			ret.setRet(-1001);
			ret.setMsg("month data error");
			return ret;
		}
		//计算ebs的价格
		BigDecimal totalPrice = new BigDecimal(0.333 * ebs);
		
		Map<String, Object> map = new HashMap<String, Object>();
		totalPrice = totalPrice.multiply(new BigDecimal(monthCount * buyCount));
		totalPrice = totalPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
		if(totalPrice.compareTo(BigDecimal.ZERO) <= 0 || totalPrice.compareTo(new BigDecimal(Integer.MAX_VALUE) ) >= 0) {
			ret.setRet(-1001);
			ret.setMsg("the price is error");
			return ret;
		}
		
		int freeMonth = monthCount/6;
		BigDecimal ebsMonthPrice = totalPrice;
		totalPrice = ebsMonthPrice.multiply(new BigDecimal(kbean.getMonth())).add(ebsMonthPrice.multiply(new BigDecimal(kbean.getDay()).divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_DOWN)));
		
		map.put("totalPrice", totalPrice.intValue());
		map.put("freeMonth", freeMonth);
		ret.addToList(map);
		
		ret.setRet(0);
		ret.setMsg("success");
		return ret;
	}

	
	/**
	 * 计算宽带价格
	 * @param net
	 * @return
	 */
	private int calcNetPrice(int net) {
		int price = 0;
		if(net > 5) {
			price = ((net - 5) * 80) + 102;
		}
		
		if(net > 4 && net <= 5) {
			price = ((net - 4) * 24) + 78;
		}
		
		if(net > 2 && net <= 4) {
			price = ((net - 2) * 20) + 38;
		}
		
		if(net <= 2) {
			price = net * 19;
		}
		
		return price;
		
	}

	/**
	 * 对输入的虚拟机数据进行验证
	 * @param monthCount
	 * @param buyCount
	 * @param data
	 * @param ret
	 * @return
	 */
	private boolean verifyData(short productType, int monthCount, int buyCount, int cpu, double mem, int ebs, int net, RestapiRet ret) {
		
		if(productType != 1 && productType != 3) {
			ret.setRet(-1008);
			ret.setMsg("product type is error");
			return false;
		}
		
		if(productType == 1) {
			if(cpu  < 1) {
				ret.setRet(-1003);
				ret.setMsg("cpu data error");
				return false;
			}
			
			if(cpu != 1) {
				if(kvmPriceMap.get(String.format("cpu_%d", cpu)) == null) {
					ret.setRet(-1004);
					ret.setMsg("cpu core data error");
					return false;
				}
			}
			
			
			if(mem <= 0) {
				ret.setRet(-1005);
				ret.setMsg("memory data error");
				return false;
			}
			
			if(net > 100) {
				ret.setRet(-1006);
				ret.setMsg("the net is too large");
				return false;
			}
		}
		
		if(ebs > 2000) {
			ret.setRet(-1006);
			ret.setMsg("the disk is too large");
			return false;
		}
		
		if(monthCount <= 0 || monthCount >= 1000) {
			ret.setRet(-1001);
			ret.setMsg("month data error");
			return false;
		}
		
		if(buyCount <= 0) {
			ret.setRet(-1001);
			ret.setMsg("buy quantity data error");
			return false;
		}
		
		return true;
		
	}


	

}
