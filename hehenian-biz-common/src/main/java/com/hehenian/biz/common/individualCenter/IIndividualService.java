package com.hehenian.biz.common.individualCenter;

import java.util.Map;

public interface IIndividualService {

	public Map<String, String> queryPersonById(long id) throws Exception;
}
