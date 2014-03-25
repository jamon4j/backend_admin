package com.ksyun.vm.dao.interfaces;

import java.util.List;

public interface ILoginDao<User> {

	public abstract List<User> getUser(String username, String password);

}