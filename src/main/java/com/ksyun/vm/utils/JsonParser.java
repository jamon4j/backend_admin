package com.ksyun.vm.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.dto.ebs.EBSDto;
import com.ksyun.vm.dto.ebs.VmEBSDto;
import com.ksyun.vm.dto.ebs.VolumeTypeDto;
import com.ksyun.vm.dto.host.HypervisorDto;
import com.ksyun.vm.dto.host.HypervisorsListDto;
import com.ksyun.vm.dto.images.SnapshotsDto;
import com.ksyun.vm.dto.images.SystemImageDto;
import com.ksyun.vm.dto.securitygroup.SecurityGroupDto;
import com.ksyun.vm.dto.user.UserDto;
import com.ksyun.vm.dto.vm.FlavorDto;
import com.ksyun.vm.dto.vm.ServerDto;
import com.ksyun.vm.dto.zone.AggregatesDto;

/**
 * Created with IntelliJ IDEA. User: yuri Date: 13-7-9 Time: 上午11:14 To change
 * this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
public class JsonParser {
	// 返回zone列表
	public static List<AggregatesDto> returnAggregatesList(String tenantId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.AGGREGATES, tenantId);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		if (resultJson != null) {
			List<AggregatesDto> resultList = new ArrayList<AggregatesDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					AggregatesDto dto = JSON.parseObject(innerIter.next().toString(), AggregatesDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回主机列表
	public static List<HypervisorsListDto> returnHypervisorsList(String tenantId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.HYPERVISORSLIST, tenantId);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		if (resultJson != null) {
			List<HypervisorsListDto> resultList = new ArrayList<HypervisorsListDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					HypervisorsListDto dto = JSON.parseObject(innerIter.next().toString(), HypervisorsListDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回主机详情
	public static HypervisorDto returnHypervisorsDetail(String tenantId, String hostName) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.HYPERVISOR, tenantId, hostName);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		HypervisorDto dto = null;
		if (resultJson != null) {
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				String testStr = entry.getValue().toString();
				dto = JSON.parseObject(testStr, HypervisorDto.class);
			}
			return dto;
		}
		return null;
	}

	// 返回指定主机的虚拟机列表
	public static List<ServerDto> returnServerList(String tenantId, String hostName, String marker) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.SERVERS, tenantId, hostName, marker);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		if (resultJson != null) {
			List<ServerDto> resultList = new ArrayList<ServerDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					ServerDto dto = JSON.parseObject(innerIter.next().toString(), ServerDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回虚拟机详情
	public static ServerDto returnServerDto(String tenantId, String vmId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.SERVER, tenantId, vmId);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		ServerDto dto = null;
		if (resultJson != null) {
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				String testStr = entry.getValue().toString();
				dto = JSON.parseObject(testStr, ServerDto.class);
			}
			return dto;
		}
		return null;
	}

	// 返回用户列表
	public static List<UserDto> returnUserList(String pageNum) throws HttpException, IOException {
		String pageLimit = Constants.getPropertyValue(InitConst.PAGELIMIT);
		String resultJson = HttpUtils.getAdminResponseData(Constants.getPropertyValue(InitConst.USERLIST, pageLimit, pageNum));
		if (resultJson != null) {
			List<UserDto> resultList = new ArrayList<UserDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					UserDto dto = JSON.parseObject(innerIter.next().toString(), UserDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回用户虚机列表
	public static List<ServerDto> returnServerListByUser(String tenantId, String userId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.USERSERVERLIST, tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String resultJson = HttpUtils.getResponseData(requestStr, header);
		if (resultJson != null) {
			List<ServerDto> resultList = new ArrayList<ServerDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					ServerDto dto = JSON.parseObject(innerIter.next().toString(), ServerDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回某用户ebs列表
	public static List<EBSDto> returnEBSList(String tenantId, String userId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.EBSLIST, tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String resultJson = HttpUtils.getResponseData(requestStr, header);
		if (resultJson != null) {
			List<EBSDto> resultList = new ArrayList<EBSDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					EBSDto dto = JSON.parseObject(innerIter.next().toString(), EBSDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回某用户系统盘
	public static List<SystemImageDto> returnSysImagesList(String tenantId, String userId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.SYSIMAGES, tenantId, userId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String resultJson = HttpUtils.getResponseData(requestStr, header);
		if (resultJson != null) {
			List<SystemImageDto> resultList = new ArrayList<SystemImageDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					SystemImageDto dto = JSON.parseObject(innerIter.next().toString(), SystemImageDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回某用户数据盘
	public static List<SnapshotsDto> returnSnapshotsList(String tenantId, String userId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.SNAPSHOTS, tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String resultJson = HttpUtils.getResponseData(requestStr, header);
		if (resultJson != null) {
			List<SnapshotsDto> resultList = new ArrayList<SnapshotsDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					SnapshotsDto dto = JSON.parseObject(innerIter.next().toString(), SnapshotsDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回用户可选的镜像列表
	public static List<SystemImageDto> returnImagePublicIdList(String tenantId, String userId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.IMAGESPUBLIC);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String resultJson = HttpUtils.getResponseData(requestStr, header);
		if (resultJson != null) {
			List<SystemImageDto> resultList = new ArrayList<SystemImageDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					SystemImageDto dto = JSON.parseObject(innerIter.next().toString(), SystemImageDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回虚拟机类型列表
	public static List<FlavorDto> returnFlavorIdList(String tenantId, String userId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.FLAVORS, tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String resultJson = HttpUtils.getResponseData(requestStr, header);
		if (resultJson != null) {
			List<FlavorDto> resultList = new ArrayList<FlavorDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					FlavorDto dto = JSON.parseObject(innerIter.next().toString(), FlavorDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	public static List<String> findHostList(List<AggregatesDto> list, String zoneId) {
		List<String> hostList = null;
		for (AggregatesDto dto : list) {
			if (dto.getId().equals(zoneId)) {
				hostList = dto.getHosts();
			}
		}
		return hostList;
	}

	// 返回安全组列表
	public static List<SecurityGroupDto> returnSecurityGroupList(String tenantId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.SECURITYGROUPLIST, tenantId);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		if (resultJson != null) {
			List<SecurityGroupDto> resultList = new ArrayList<SecurityGroupDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					SecurityGroupDto dto = JSON.parseObject(innerIter.next().toString(), SecurityGroupDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}

	// 返回volume_type
	public static Map<String, List<VolumeTypeDto>> returnVolumeTypeList(String tenantId, String userId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.VOLUMETYPE, tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String resultJson = HttpUtils.getResponseData(requestStr, header);
		if (resultJson != null) {
			List<VolumeTypeDto> resultList = new ArrayList<VolumeTypeDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					VolumeTypeDto dto = JSON.parseObject(innerIter.next().toString(), VolumeTypeDto.class);
					resultList.add(dto);
				}
			}
			Map<String, List<VolumeTypeDto>> maps = new HashMap<String, List<VolumeTypeDto>>();
			maps.put("volume_types", resultList);
			return maps;
		}
		return null;
	}
	//返回vm ebs列表
	public static List<VmEBSDto> returnVMEBSLIST(String tenantId, String userId,String vmId) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.VMEBSLIST,tenantId,vmId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String resultJson = HttpUtils.getResponseData(requestStr, header);
		if (resultJson != null) {
			List<VmEBSDto> resultList = new ArrayList<VmEBSDto>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					VmEBSDto dto = JSON.parseObject(innerIter.next().toString(), VmEBSDto.class);
					resultList.add(dto);
				}
			}
			return resultList;
		}
		return null;
	}
}