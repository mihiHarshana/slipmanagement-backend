
package com.mhsoft.utils;

import com.mhsoft.model.DAOUser;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

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

	public static final String VALID_TO="validTo";

	public static final String LAST_UPDATED="lastUpdated";

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

	public static final String TR_STATUS_FULLYRECIEVED  = "Fully Recieved";

	public static final String TR_STATUS_PARCIALLYRECIEVED  = "Partially Recieved";

	public static final String TR_STATUS_PROCESSING = "Processing";

	public static final String TR_STATUS_Completed  = "Completed";
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
				String [] array= new String[2];
				array[0] = TR_STATUS_SUBMITTED;
				array[1] = TR_STATUS_Cancelled;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_NotReceived)) {
				String [] array= new String[3];
				array[0] = TR_STATUS_NotReceived;
				array[0] = TR_STATUS_RESUBMITTED;
				array[1] = TR_STATUS_Cancelled;
				return array;
			}

			if (currentStatus.equals(TR_STATUS_Cancelled)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_Cancelled;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_Completed)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_Completed;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_FULLYRECIEVED)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_FULLYRECIEVED;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_UserConfirmed)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_UserConfirmed;
				return array;
			}

		} else if (trType.equals(Utils.TRTYPEWIDTHDRAW)){
			if (currentStatus.equals(TR_STATUS_SUBMITTED)) {
				String [] array= new String[2];
				array[0] = TR_STATUS_SUBMITTED;
				array[1] = TR_STATUS_Cancelled;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_AwatingConfirmation)) {
				String [] array= new String[3];
				array[0] = TR_STATUS_AwatingConfirmation;
				array[1] = TR_STATUS_UserConfirmed;
				array[2] = TR_STATUS_NotReceived;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_AmountDifferent)) {
				String [] array= new String[2];
				array[0] = TR_STATUS_AmountDifferent;
				array[1] = TR_STATUS_UserConfirmed;
				array[2] = TR_STATUS_NotReceived;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_FULLYRECIEVED)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_FULLYRECIEVED;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_NotReceived)) {
				String [] array= new String[3];
				array[0] = TR_STATUS_NotReceived;
				array[1] = TR_STATUS_RESUBMITTED;
				array[2] = TR_STATUS_Cancelled;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_InsufficientFunds)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_InsufficientFunds;
				return array;
			}

			if (currentStatus.equals(TR_STATUS_UserConfirmed)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_UserConfirmed;
				return array;
			}
		}
		return null;
	}

	public String [] getTransStatusForCCA(String currentStatus, String trType) {
		if (trType.equals(Utils.TRTRYOEDEPOSIT)) {
			if (currentStatus.equals(TR_STATUS_SUBMITTED)) {
				String [] array= new String[5];
				array[0] = TR_STATUS_SUBMITTED;
				array[1] = TR_STATUS_FULLYRECIEVED;
				array[2] = TR_STATUS_PARCIALLYRECIEVED;
				array[3] = TR_STATUS_NotReceived;
				array[4] = TR_STATUS_AwatingConfirmation;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_RESUBMITTED)) {
				String [] array= new String[5];
				array[0] = TR_STATUS_RESUBMITTED;
				array[1] = TR_STATUS_FULLYRECIEVED;
				array[2] = TR_STATUS_PARCIALLYRECIEVED;
				array[3] = TR_STATUS_NotReceived;
				array[4] = TR_STATUS_AwatingConfirmation;
				return array;
			}

			if (currentStatus.equals(TR_STATUS_AwatingConfirmation)) {
				String [] array= new String[4];
				array[0] = TR_STATUS_AwatingConfirmation;
				array[1] = TR_STATUS_FULLYRECIEVED;
				array[2] = TR_STATUS_PARCIALLYRECIEVED;
				array[3] = TR_STATUS_NotReceived;
				return array;
			}

			if (currentStatus.equals(TR_STATUS_FULLYRECIEVED)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_FULLYRECIEVED;
				return array;
			}

			if (currentStatus.equals(TR_STATUS_Cancelled)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_Cancelled;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_UserConfirmed)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_UserConfirmed;
				return array;
			}

		} else if (trType.equals(Utils.TRTYPEWIDTHDRAW)){
			if (currentStatus.equals(TR_STATUS_SUBMITTED)) {
				String [] array= new String[5];
				array[0] = TR_STATUS_SUBMITTED;
				array[1] = TR_STATUS_PROCESSING;
				array[2] = TR_STATUS_InsufficientFunds;
				array[3] = TR_STATUS_AwatingConfirmation;
				array[4] = TR_STATUS_AmountDifferent;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_NotReceived)) {
				String [] array= new String[5];
				array[0] = TR_STATUS_NotReceived;
				array[1] = TR_STATUS_PROCESSING;
				array[2] = TR_STATUS_InsufficientFunds;
				array[3] = TR_STATUS_AwatingConfirmation;
				array[4] = TR_STATUS_AmountDifferent;
				return array;
			}

			if (currentStatus.equals(TR_STATUS_FULLYRECIEVED)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_FULLYRECIEVED;
				return array;
			}

			if (currentStatus.equals(TR_STATUS_PROCESSING)) {
				String [] array= new String[5];
				array[0] = TR_STATUS_PROCESSING;
				array[1] = TR_STATUS_InsufficientFunds;
				array[2] = TR_STATUS_AwatingConfirmation;
				array[3] = TR_STATUS_AmountDifferent;
				array[4] = TR_STATUS_FULLYRECIEVED;
				return array;
			}

			if (currentStatus.equals(TR_STATUS_Cancelled)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_Cancelled;
				return array;
			}
			if (currentStatus.equals(TR_STATUS_UserConfirmed)) {
				String [] array= new String[1];
				array[0] = TR_STATUS_UserConfirmed;
				return array;
			}
		}
		// TODO : Increase the count of the cycle by one
		// TODO : 1. Widthrawal at processing
		// TODO : 2. Deposit at   Status
		return null;
	}

	public enum  USERETYPE {
		CUSTOMER,
		AGENT,
		CCAGENT
	}

	public enum  USERSTATUS {
		APPROVED,
		REGISTERED,
		REJECTED,
		SUSPEND,
	}
/**
Gets the time in miliseconds and convert it to How many days passed.
and return a string.
 */
public String calculateElaspedDays(Long lastRecordTime) {
	long days = TimeUnit.SECONDS.toDays(lastRecordTime);
	Long current = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
	Long diff =  current -lastRecordTime ;


	if (diff / 3600 == 0) {
		return diff/60 + " mins(s)";
	}

	if (diff /86400 == 0) {
		return diff/ 3600 + " hour(s)";
	}

	return String.valueOf(diff /86400  + " day(s)");
}
	public  Long getCurrentDateTime() {
		return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

	}

}


