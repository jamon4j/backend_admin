package com.ksyun.vm.pojo.lbs;

import java.util.List;

import com.ksyun.vm.pojo.BasePo;

/**
 * 健康检查POJO
 * 
 * @author XueQi
 * 
 */
public class HealthPOJO extends BasePo {
	private List<HealthVipPOJO> vips;
	private String admin_state_up;
	private String tenant_id;
	private String rise;
	private String delay;
	private String max_retries;
	private String create_time;
	private String timeout;
	private String fall;
	private String type;
	private String id;
	private String http_method;
	private String url_path;

	public List<HealthVipPOJO> getVips() {
		return vips;
	}

	public void setVips(List<HealthVipPOJO> vips) {
		this.vips = vips;
	}

	public String getAdmin_state_up() {
		return admin_state_up;
	}

	public void setAdmin_state_up(String admin_state_up) {
		this.admin_state_up = admin_state_up;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public String getRise() {
		return rise;
	}

	public void setRise(String rise) {
		this.rise = rise;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getMax_retries() {
		return max_retries;
	}

	public void setMax_retries(String max_retries) {
		this.max_retries = max_retries;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getFall() {
		return fall;
	}

	public void setFall(String fall) {
		this.fall = fall;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHttp_method() {
		return http_method;
	}

	public void setHttp_method(String http_method) {
		this.http_method = http_method;
	}

	public String getUrl_path() {
		return url_path;
	}

	public void setUrl_path(String url_path) {
		this.url_path = url_path;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		HealthPOJO pojo = (HealthPOJO) obj;
		if (this.getId().equals(pojo.getId())) {
			return true;
		}
		return false;
	}

}
