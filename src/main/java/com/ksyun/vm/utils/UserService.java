package com.ksyun.vm.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ksyun.payment.dao.IUserAccountDetailDao;
import com.ksyun.payment.dao.IUserDao;
import com.ksyun.payment.dao.IUserInfoDao;
import com.ksyun.payment.dto.UserAccountDetailDto;
import com.ksyun.payment.dto.UserDto;
import com.ksyun.payment.dto.UserInfoDto;
import com.ksyun.payment.service.IUserService;
import com.ksyun.pcloud.common.account.UserHelper;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.UserPo;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.service.JSONService;

/**
 * User: liuchuandong
 * Date: 13-10-11
 * Time: 下午2:37
 * Func:
 */
@Service
public class UserService {

    @Autowired
    private JSONService jsonService;
    
    //iConsole UserService
    @Autowired
  	private IUserDao<UserDto> userDao;
  	
    @Autowired
  	private IUserAccountDetailDao<UserAccountDetailDto> userAccountDetailDao;
  	
    @Autowired
  	private IUserInfoDao<UserInfoDto> userInfoDao;


    public List<UserPojo> getUsers() throws ErrorCodeException, NoTokenException {
        List<UserPojo> list = jsonService.getPoList(InitConst.KVM_USER_LIST,InitConst.ADMIN,InitConst.PASSWORD,UserPojo.class);
        return list;
    }

    public void addUser(String username,String email) throws ErrorCodeException, NoTokenException {
        Map<String,String> map = new HashMap();
        map.put("name",username);
        map.put("email",email);
        map.put("is_admin","0");
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poWithNoAuth(InitConst.KVM_USER_REGISTER, null,requestBody);
    }

	public UserPojo searchUser(String name, String email,String user_id) throws ErrorCodeException, NoTokenException {
		UserPo userPo = null;
		String userId = null;
		if(!StringUtils.isEmpty(name) && name.equals(InitConst.ADMIN)){
			userPo = jsonService.getToken(name, InitConst.PASSWORD);
			userId = userPo.getUser_id();
		}else if(!StringUtils.isEmpty(name)){
			userPo = jsonService.getToken(name, name);
			userId = userPo.getUser_id();
		}
		
		if(!StringUtils.isEmpty(email)){
			String ADMINUSERNAME = "kpweb@kingsoft.com";
		    String ADMINPASSWORD = "WWcW59FqUFubBzmTe6BmOF1sHmaye";
		//	String ADMINUSERNAME = "test@kingsoft.com";
		//	String ADMINPASSWORD = "123456";
		    String token = UserHelper.login(ADMINUSERNAME, ADMINPASSWORD).get("token");
		    Map<String, String> map =  UserHelper.adminGetUserInfoByEmail(token, email);
		    userPo = jsonService.getToken(map.get("userId"), map.get("userId"));
		    userId = userPo.getUser_id();
		}
        if(!StringUtils.isEmpty(user_id)){
            userId = user_id;
        }
		UserPojo userPojo = jsonService.poGet(InitConst.KVM_USER, InitConst.ADMIN, InitConst.PASSWORD, UserPojo.class, userId);
		return userPojo;
	}
	
	
	public UserDto getUserAccount(long userId) {
		return userDao.findById(userId);
	}

	
	public List<UserAccountDetailDto> findAllDetail(long userId, short status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("status", status);
		List<UserAccountDetailDto> list = userAccountDetailDao.findAllByUser(params, "");
		
		//获得结果集条总数
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageList<UserAccountDetailDto> pageList = (PageList)list;
		return pageList;	
	}

	
	public void updateAlarmBalance(long userId, BigDecimal alarmBalance, short isAlarm) {
		UserDto user = new UserDto();
		user.setUserId(userId);
		user.setAlarmBalance(alarmBalance);
		user.setIsAlarm(isAlarm);
		userDao.updateAlarmBalance(user);
		
	}

	
	public void saveAccountDetail(UserAccountDetailDto detail) {
		userAccountDetailDao.save(detail);
	}

	
	public UserAccountDetailDto findDetail(String caSerial) {
		return userAccountDetailDao.findById(caSerial);
	}

	
	public BigDecimal saveIncomMoney(long userId, String payId, String caSerial, BigDecimal money) {
		//获取账户信息
		UserDto userAccount = userDao.findById(userId);
		BigDecimal balance = userAccount.getBalance();
		BigDecimal balance_now = balance.add(money);
		int arrearageDay = userAccount.getArrearageDay();
		//写入账户余额
		UserDto tmp = new UserDto();
		tmp.setBalance(balance_now);
		tmp.setUserId(userId);
		if(balance_now.compareTo(BigDecimal.ZERO) >= 0) {
			tmp.setArrearageDay(0);
		} else {
			tmp.setArrearageDay(arrearageDay + 1);
		}
		userDao.updateBalance(tmp);
		
		
		//首先写入用户账户明细，为了对账方便。
		UserAccountDetailDto detail = new UserAccountDetailDto();
		detail.setCaSerial(caSerial);
		detail.setUserId(userId);
		detail.setBalance(balance_now);
		detail.setPayId(payId);
		detail.setIncomeMoney(money);
		detail.setPayMoney(BigDecimal.ZERO);
		detail.setDealTime(new Date());
		detail.setStatus((short)1);
		userAccountDetailDao.updateDetailStatus(detail);

		return balance_now;
	}

	
	public List<UserAccountDetailDto> findAllIncomeDetail(long userId, short dealType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("dealType", dealType);
		List<UserAccountDetailDto> list = userAccountDetailDao.findAllByUser(params, "");
		
		//获得结果集条总数
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageList<UserAccountDetailDto> pageList = (PageList)list;
		return pageList;	
	}

	
	public List<UserAccountDetailDto> findAllDetail(Map<String, Object> params) {
		List<UserAccountDetailDto> list = userAccountDetailDao.findAllDetails(params);
		//获得结果集条总数
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageList<UserAccountDetailDto> pageList = (PageList)list;
		return pageList;
	}

	
	public void saveBackRechargeMoney(long userId, String payId, String caSerial, BigDecimal money) {
		//获取账户信息
		UserDto userAccount = userDao.findById(userId);
		BigDecimal balance = userAccount.getBalance();
		BigDecimal balance_now = balance.add(money);
		//写入账户余额
		UserDto tmp = new UserDto();
		tmp.setBalance(balance_now);
		tmp.setUserId(userId);
		
		userDao.updateBalance(tmp);
		
		
		//首先写入用户账户明细，为了对账方便。
		UserAccountDetailDto detail = new UserAccountDetailDto();
		detail.setCaSerial(caSerial);
		detail.setUserId(userId);
		detail.setBalance(balance_now);
		detail.setPayId(payId);
		detail.setIncomeMoney(money);
		detail.setPayMoney(BigDecimal.ZERO);
		detail.setDealTime(new Date());
		detail.setStatus((short)1);
		detail.setDealType((short)1);
		detail.setDealTime(new Date());
		userAccountDetailDao.save(detail);

		
	}

	
	public void saveBackDrawMoney(long userId, String payId, String caSerial, BigDecimal money) {
		//获取账户信息
		UserDto userAccount = userDao.findById(userId);
		BigDecimal balance = userAccount.getBalance();
		BigDecimal balance_now = balance.subtract(money);
		
		//写入账户余额
		UserDto tmp = new UserDto();
		tmp.setBalance(balance_now);
		tmp.setUserId(userId);
		
		userDao.updateBalance(tmp);
		
		
		//首先写入用户账户明细，为了对账方便。
		UserAccountDetailDto detail = new UserAccountDetailDto();
		detail.setCaSerial(caSerial);
		detail.setUserId(userId);
		detail.setBalance(balance_now);
		detail.setPayId(payId);
		detail.setIncomeMoney(BigDecimal.ZERO);
		detail.setPayMoney(money);
		detail.setDealTime(new Date());
		detail.setStatus((short)1);
		detail.setDealType((short)4);
		detail.setDealTime(new Date());
		userAccountDetailDao.save(detail);

	}

	
	
	public void saveBackDeductMoney(long userId, String payId, String caSerial,BigDecimal money) {
		//获取账户信息
		UserDto userAccount = userDao.findById(userId);
		BigDecimal balance = userAccount.getBalance();
		BigDecimal balance_now = balance.subtract(money);
		//写入账户余额
		UserDto tmp = new UserDto();
		tmp.setBalance(balance_now);
		tmp.setUserId(userId);
		
		userDao.updateBalance(tmp);
		
		
		//首先写入用户账户明细，为了对账方便。
		UserAccountDetailDto detail = new UserAccountDetailDto();
		detail.setCaSerial(caSerial);
		detail.setUserId(userId);
		detail.setBalance(balance_now);
		detail.setMemo(payId);
		detail.setIncomeMoney(BigDecimal.ZERO);
		detail.setPayMoney(money);
		detail.setDealTime(new Date());
		detail.setStatus((short)1);
		detail.setDealType((short)5);
		detail.setDealTime(new Date());
		userAccountDetailDao.save(detail);	
		
	}
	
	
	public UserInfoDto findUserInfo(long userId) {
		return userInfoDao.findById(userId);
	}

	
	
	public List<UserInfoDto> findUserInfoByEmailAndMobile(long userId, String email, String mobile) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("email", email);
		params.put("mobile", mobile);
		List<UserInfoDto> list = userInfoDao.findAllByEmailAndMobile(params);
		//获得结果集条总数
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageList<UserInfoDto> pageList = (PageList)list;
		return pageList;

	}

	
	public void updateUserType(long userId, short userType, String userFrom) {
		
		System.out.println("userId = "+userId + ", userTyep=" + userType);
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setUserId(userId);
		userInfoDto.setUserType(userType);
		userInfoDto.setUserFrom(userFrom);
		userInfoDao.updateUserType(userInfoDto);
	}
	
	public static void main(String[] args) {
	//	String ADMINUSERNAME = "kpweb@kingsoft.com";
	//    String ADMINPASSWORD = "WWcW59FqUFubBzmTe6BmOF1sHmaye";
		String ADMINUSERNAME = "test@kingsoft.com";
		String ADMINPASSWORD = "123456";
	    String token = UserHelper.login(ADMINUSERNAME, ADMINPASSWORD).get("token");
	    Map<String, String> map =  UserHelper.adminGetUserInfoByEmail(token, "liuyu1@kingsoft.com");
	    System.out.println(map);
	}
}
