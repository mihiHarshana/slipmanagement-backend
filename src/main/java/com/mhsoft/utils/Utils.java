
package com.mhsoft.utils;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public class Utils {

	String message;
	HttpStatus statusCode;
	
	final String MESSAGE = "message";
	final String STATUS_CODE= "statusCode";

	public static final  String BANK_CODE="bankCode";
	public static final  String BANK_BRANCH="branchName";

	public static final String BANK_INS="instruction";

	public static final String BANK_ACC_NO="accountNo";

	public static final String BANK_NAME="bankName";

	public static final String AGENT="agent";

	public static final String TRTYPEWIDTHDRAW = "Withdraw";
	public static final String TRTRYOEDEPOSIT = "Deposit";

	public static final String TR_AMOUNT = "amount";
	public static final String TR_DATE = "date";
	public static final String TR_STATUS = "status";
	public static final String TR_TYPE = "type";
	public static final String TR_ID = "transactionId";
	public static final String TR_USERID = "userId";

	public static final String TR_SLIPDATE = "slipDate";

	public static final String TR_REMARKS = "remarks";

	public static final String TR_SLIP = "slip";
	private static Utils utils = null;

	private Utils () {}

	public static synchronized Utils getInstance()
	{
		if (utils == null)
			utils = new Utils();

		return utils;
	}
	public String JsonMessage (String message, HttpStatus statusCode) {
		
		this.message = message;
		this.statusCode = statusCode;
		
		JSONObject jo = new JSONObject();
		jo.put(MESSAGE, message);
		jo.put(STATUS_CODE, statusCode.value());
		
		return jo.toString();
	}

	public String concatinateAgent(String str) {

		String str1 = str.substring(0,1);
		str1 = str1.toUpperCase();
		String str2 = str.substring(1);

		return "agent".concat(str1).concat(str2);


	}
}
