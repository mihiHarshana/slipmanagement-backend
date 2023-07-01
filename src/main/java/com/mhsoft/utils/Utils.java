
package com.mhsoft.utils;

import com.mhsoft.model.DAOUser;
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

//	public static final String TR_REMARKS = "remarks";

	public static final String TR_CUS_REMARKS = "customerRemarks";

	public static final String TR_CCAGENT_REMARKS = "ccAgentRemarks";

	public static final String TR_AGENT_REMARKS = "agentRemarks";

	public static final String TR_SLIP = "slipLink";

	public static final String TR_SLIP_NAME = "slipName";

	public static final String TR_SLIP_DATe = "slipDate";

	// Transaction table statues

	public static final String TR_STATUS_SUBMITTED = "Submitted";

	public static final String TR_STATUS_RESUBMITTED = "Re Submitted";
	public static final String TR_STATUS_NotReceived = "Not Received";
	public static final String TR_STATUS_AwatingConfirmation = "Awaiting Confirmation";
	public static final String TR_STATUS_Received  = "Received";
	public static final String TR_STATUS_Cancelled  = "Cancelled";
	public static final String TR_STATUS_AmountDifferent  = "Amount Different";
	public static final String TR_STATUS_InsufficientFunds  = "Insufficient Funds";
	public static final String TR_STATUS_UserConfirmed  = "User Confirmed";
	public static final String TR_STATUS_LIST = "statusList";
	private static Utils utils = null;

	DAOUser currentUser = null;

	private Utils () {}

	public static synchronized Utils getInstance() {
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

	public String getTokenFromAuthKey(String Authorization) {
		return Authorization.substring(7);

	}

	public void setUserDetails(DAOUser daoUser) {
		currentUser = new DAOUser();
		currentUser.setUserId(daoUser.getUserid());
		currentUser.setUserType(daoUser.getUsertype());
		currentUser.setUserfname(daoUser.getUserfname());
		currentUser.setUserlname(daoUser.getUserlname());
		currentUser.setUserstatus(daoUser.getUserStatus());

	}
	public DAOUser getUserDetails() {
		return currentUser;
	}

	public String [] getTransStatus(String currentStatus, String trType) {
	if (trType.equals(Utils.TRTRYOEDEPOSIT)) {
		if (currentStatus.equals(TR_STATUS_SUBMITTED)) {
			String [] array= new String[1];
			array[0] = TR_STATUS_Cancelled;
			return array;
		}
		if (currentStatus.equals(TR_STATUS_NotReceived)) {
			String [] array= new String[2];
			array[0] = TR_STATUS_RESUBMITTED;
			array[1] = TR_STATUS_Cancelled;
			return array;
		}
	} else if (trType.equals(Utils.TRTYPEWIDTHDRAW)){
		if (currentStatus.equals(TR_STATUS_SUBMITTED)) {
			String [] array= new String[1];
			array[0] = TR_STATUS_Cancelled;
			return array;
		}
		if (currentStatus.equals(TR_STATUS_AwatingConfirmation)) {
			String [] array= new String[2];
			array[0] = TR_STATUS_UserConfirmed;
			array[1] = TR_STATUS_NotReceived;
			return array;
		}
		if (currentStatus.equals(TR_STATUS_AmountDifferent)) {
			String [] array= new String[2];
			array[0] = TR_STATUS_UserConfirmed;
			array[1] = TR_STATUS_NotReceived;
			return array;
		}
	}
		return null;
	}
}
