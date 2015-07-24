package com.shove.util;

import org.apache.commons.lang3.StringUtils;

public class SqlInfusion {
	public static String FilteSqlInfusion(String input) {
		if ((input == null) || (input.trim() == "")) {
			return "";
		}
		if (!StringUtils.isNumeric(input)) {
			return input.replace("'", "’").replace("update", "ｕｐｄａｔｅ").replace(
					"drop", "ｄｒｏｐ").replace("delete", "ｄｅｌｅｔｅ").replace("exec",
					"ｅｘｅｃ").replace("create", "ｃｒｅａｔｅ").replace("execute",
					"ｅｘｅｃｕｔｅ").replace("where", "ｗｈｅｒｅ").replace("truncate",
					"ｔｒｕｎｃａｔｅ").replace("insert", "ｉｎｓｅｒｔ");
		} else {
			return input;
		}
	}
}
