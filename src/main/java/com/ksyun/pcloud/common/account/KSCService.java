package com.ksyun.pcloud.common.account; /**
 * @author: yangji
 * @data:   Aug 2, 2013
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class KSCService{	

	public static final KSCService KS3;
	public static final KSCService KVM;	
	
	private String service;
	
	private KSCService(String service){
		this.service = service;
	}

	public String toString() {
		return this.service;
	}
	
	public boolean equals(String service){
		return this.service.equals(service);
	}
	
	public static KSCService getKSCService(String service){
		KSCService kscService = null;
		Class<?> cls = KSCService.class;
		Field[] fields = cls.getDeclaredFields();		
		try {
			for(Field field:fields){
				boolean isStatic = Modifier.isStatic(field.getModifiers());
		        if(isStatic) {
		        	String value = field.get(null).toString();
		        	if(value.equals(service))
		        		kscService = new KSCService(service);
		        }				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return kscService;
	}
	
	static {
		KS3 = new KSCService("ks3");
		KVM = new KSCService("kvm");		
	}
}
