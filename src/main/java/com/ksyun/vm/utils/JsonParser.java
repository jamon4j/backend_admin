package com.ksyun.vm.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.ksyun.vm.dto.ebs.EBSDto;
import com.ksyun.vm.dto.host.HypervisorDto;
import com.ksyun.vm.dto.host.HypervisorsListDto;
import com.ksyun.vm.dto.images.ImagesDto;
import com.ksyun.vm.dto.images.SnapshotsDto;
import com.ksyun.vm.dto.user.UserDto;
import com.ksyun.vm.dto.vm.FlavorDto;
import com.ksyun.vm.dto.vm.ServerDto;
import com.ksyun.vm.dto.zone.AggregatesDto;

/**
 * Created with IntelliJ IDEA. User: yuri Date: 13-7-9 Time: 上午11:14 To change
 * Date:13-8-2 modifer: liuchuandong func:提出公有方法
 * this template use File | Settings | File Templates.
 */
public class JsonParser extends Json{
	// 返回zone列表
	public static List<AggregatesDto> returnAggregatesList(String tenantId) throws HttpException, IOException {
		return returnList(AggregatesDto.class,InitConst.AGGREGATES, tenantId);
	}

	// 返回主机列表
	public static List<HypervisorsListDto> returnHypervisorsList(String tenantId) throws HttpException, IOException {
		return returnList(HypervisorsListDto.class,InitConst.HYPERVISORSLIST, tenantId);
	}

	// 返回主机详情
	public static HypervisorDto returnHypervisorsDetail(String tenantId, String hostName) throws HttpException, IOException {
		return returnObj(HypervisorDto.class,InitConst.HYPERVISOR, tenantId,hostName);
	}

	// 返回指定主机的虚拟机列表
	public static List<ServerDto> returnServerList(String tenantId, String hostName, String marker) throws HttpException, IOException {
		return returnList(ServerDto.class,InitConst.SERVERS,tenantId,hostName, marker);
	}

	// 返回虚拟机详情
	public static ServerDto returnServerDto(String tenantId,String vmId) throws HttpException, IOException {
		return returnObj(ServerDto.class,InitConst.SERVER,tenantId,vmId);
	}

	// 返回用户列表
	public static List<UserDto> returnUserList(String pageNum) throws HttpException, IOException {
		String pageLimit = getRemoteProperty(InitConst.PAGELIMIT);
		return returnList(UserDto.class,InitConst.USERLIST, pageLimit, pageNum);
	}
	//返回用户虚机列表
	public static List<ServerDto> returnServerListByUser(String tenantId, String userId) throws HttpException, IOException {
		return returnList(ServerDto.class,InitConst.USERSERVERLIST, tenantId);
	}
	// 返回某用户ebs列表
	public static List<EBSDto> returnEBSList(String tenantId, String userId) throws HttpException, IOException {
		return returnList(EBSDto.class,InitConst.EBSLIST,tenantId);
	}

	// 返回某用户系统盘
	public static List<ImagesDto> returnImagesList(String tenantId, String userId) throws HttpException, IOException {
		return returnList(ImagesDto.class,InitConst.IMAGES);
	}

	// 返回某用户数据盘
	public static List<SnapshotsDto> returnSnapshotsList(String tenantId, String userId) throws HttpException, IOException {
		return returnList(SnapshotsDto.class,InitConst.SNAPSHOTS,tenantId);
	}
	//返回用户可选的镜像列表
	public static List<ImagesDto> returnImagePublicIdList(String tenantId, String userId) throws HttpException, IOException{
		return returnList(ImagesDto.class,InitConst.IMAGESPUBLIC);
	}
	//返回虚拟机类型列表
	public static List<FlavorDto> returnFlavorIdList(String tenantId, String userId) throws HttpException, IOException{
		return returnList(FlavorDto.class,InitConst.FLAVORS,tenantId);
	}
	public static List<String> findHostList(List<AggregatesDto> list, String zoneId) {
		List<String> hostList = null;
		for(AggregatesDto dto : list){
			if(dto.getId().equals(zoneId)){
				hostList = dto.getHosts();
			}
		}
		return hostList;
	}
	
}