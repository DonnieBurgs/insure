package com.web.util;

import java.util.List;
import java.util.Map;

import com.web.db.DbUtils;

public class MessageUtil {
	
	public static int saveMessageAction(String user_sender, String user_receiver, String content, String url, String linkid, String sessionid) {
		if(sessionid==null || sessionid.length()==0) {
			Map<String, Object> session = getOrMakeSession(user_sender, user_receiver);
			if(session!=null) {
				sessionid = Putil.getString(session.get("messagesessionid"));
			}
		}
		StringBuilder select = new StringBuilder("insert into em_message (messagesessionid,groupid,messagetypeid,sender,receiver,content,url,linkid,groupseq,senderread,receiverread,senderdelete,receiverdelete,createdate) values ("
				+ "" + sessionid + ""
				+ ",0"
				+ ",0"
				+ "," + user_sender + ""
				+ "," + user_receiver + ""
				+ ",'" + content.replace("'", "''") + "'"
				+ ",'" + url.replace("'", "''") + "'"
				+ ",'" + linkid.replace("'", "''") + "'"
				+ ",0"
				+ ",1"
				+ ",0"
				+ ",0"
				+ ",0"
				+ ",SYSDATE()"
				+ ")"
			);
		int result = DbUtils.save(select.toString());
		if(result>0) {
			countMessageSession(sessionid, user_sender);
		}
		
		return result;
	}
	
	public static Map<String, Object> getOrMakeSession(String userid, String to_userid){
		Map<String, Object> session = DbUtils.queryOne("select p.* from em_messagesession p where "
				+ " (p.userid1="+userid + " and p.userid2=" + to_userid + " or p.userid1="+to_userid + " and p.userid2=" + userid + ")");
		if(session==null) {
			int result = DbUtils.save("insert into em_messagesession (userid1,userid2,messagecount,isnew1,isnew2,lastmessageid,companyid) values ("
					+ "" + userid + ""
					+ "," + to_userid + ""
					+ ",0"
					+ ",0"
					+ ",0"
					+ ",0"
					+ ",0"
					+ ")"
				);
			if(result>0) {
				session = DbUtils.queryOne("select p.* from em_messagesession p where "
						+ " (p.userid1="+userid + " and p.userid2=" + to_userid + " or p.userid1="+to_userid + " and p.userid2=" + userid + ")");
			}
		}
		
		return session;
	}	
	
	public static void countMessageSession(String sessionid, String userid) {
		int uid = Putil.getInt(userid);
		List<Map<String, Object>> messageList = DbUtils.query("select p.* from em_message p where "
				+ " p.messagesessionid=" + sessionid
				+ " order by messageid desc");
		Map<String, Object> session = DbUtils.queryOne("select p.*,u1.username,u1.username username1,u2.username username2,u1.photo,u1.photo photo1,u2.photo photo2 from em_messagesession p,em_user u1,em_user u2"
				+ " where p.userid1=u1.userid and p.userid2=u2.userid"
				+ " and p.messagesessionid="+ sessionid + "");
		if(messageList!=null && messageList.size()>0 && session!=null) {
			Map<String, Object> newMessage = messageList.get(0);
			int userid1 = Putil.getInt(session.get("userid1"));
			int isnew1 = 0 ;
			int isnew2 = 0 ;
			for(Map<String, Object> message:messageList) {
				if(Putil.getInt(message.get("receiver"))==uid && Putil.getInt(message.get("receiverread"))==0) {
					isnew1++;
				} else if(Putil.getInt(message.get("receiver"))!=uid && Putil.getInt(message.get("receiverread"))==0) {
					isnew2++;
				}
			}
			DbUtils.save("update em_messagesession set lastmessageid=" + Putil.getString(newMessage.get("messageid"))
						 + ",isnew1=" + (userid1==uid?isnew1:isnew2)
						 + ",isnew2=" + (userid1==uid?isnew2:isnew1)
						 + ",messagecount=" + messageList.size()
						 + " where messagesessionid="+ sessionid + ""
					);
		}
	}
}
