package utility;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {
	Connection con;
	ReadPropFile db = new ReadPropFile();

	/***
	 * Constructor that initiates and establish the database connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public DBConnector() throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://" + db.getDBData().getProperty("HostName") + ":3306/"
						+ db.getDBData().getProperty("DefaultSchema") + "?useSSL=false",
				db.getDBData().getProperty("Username"), db.getDBData().getProperty("Password"));
	}

	/***
	 * On providing the query, this function executes and retrieve the data in the form of ResultSet data type
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String query) throws SQLException {
		System.out.println(query);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}

	/**
	 * Function to convert the ResultSet data type content to ArrayList
	 * Note: Expected that the resultSet has only a single value
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getSingleColumnData(ResultSet rs) throws SQLException {
		return getSingleColumnData(rs, 1);
	}

	/**
	 * Function to convert the ResultSet data type content to ArrayList
	 * @param rs
	 * @param columnNumber
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getSingleColumnData(ResultSet rs, int columnNumber) throws SQLException {
		ArrayList<String> colData = new ArrayList<>();
		while (rs.next()) {
			colData.add(rs.getString(columnNumber));
		}
		return colData;
	}

	public int getSingleColumnDataCount(ResultSet rs) throws SQLException {
		int count = getSingleColumnData(rs, 1).size();
		return count;
	}

	public ResultSet selectAllFromCourses() throws SQLException {
		return executeQuery(db.getQuery().getProperty("SelectAllFromCourses"));
	}

	public ResultSet selectAllFromOffers() throws SQLException {
		return executeQuery(db.getQuery().getProperty("SelectAllFromOffers"));
	}

	public ResultSet SelectFromCourseEnrollment_Uid(String uid) throws SQLException {
		return executeQuery(db.getQuery().getProperty("SelectFromCourseEnrollment_Uid").replace("<Replace>", uid));
	}

	public ResultSet SelectFromOffers_UserID(String userId) throws SQLException {
		return executeQuery(db.getQuery().getProperty("SelectFromOffers_UserID").replace("<Replace>", userId));
	}

	public ResultSet SelectFromPaymentDetails_UserID(String userId) throws SQLException {
		return executeQuery(db.getQuery().getProperty("SelectFromPaymentDetails_UserID").replace("<Replace>", userId));
	}


	public String Select_IsClaimed_FromCourseEnrolment_UID_CID(String uid, String cid) throws SQLException {
		ResultSet rs = executeQuery(db.getQuery().getProperty("Select_IsClaimed_FromCourseEnrolment_UID_CID")
				.replace("<Replace_uid>", uid).replace("<Replace_cid>", cid));
		String value = "";
		while (rs.next()) {
			value = rs.getString("is_Claimed");
		}
		return value; // returns only a single value
	}
	public String Select_IsPaid_FromCourseEnrolment_UID_CID(String uid, String cid) throws SQLException {
		ResultSet rs = executeQuery(db.getQuery().getProperty("Select_IsPaid_FromCourseEnrolment_UID_CID")
				.replace("<Replace_uid>", uid).replace("<Replace_cid>", cid));
		String value = "";
		while (rs.next()) {
			value = rs.getString("is_paid");
		}
		return value; // returns only a single value
	}
	
	public String SelectCoursePercentage_UID_CID(String uid, String cid) throws SQLException {
		ResultSet rs = executeQuery(db.getQuery().getProperty("SelectCoursePercentage_UID_CID")
				.replace("<Replace_uid>", uid).replace("<Replace_cid>", cid));
		String value = "";
		while (rs.next()) {
			value = rs.getString("course_percentage");
		}
		return value; // returns only a single value
	}
	public String Select_QuizCorrectAnswer(String cid, String question) throws SQLException {
		ResultSet rs = executeQuery(db.getQuery().getProperty("Select_QuizCorrectAnswer").replace("<Replace_cid>", cid)
				.replace("<Replace_question>", question));
//	String answer = rs.getString("optionname");
//	while (rs.next()) {
//		answer = rs.getString("optionname");
//	}
		String answer = "";
		try {
			while (rs.next()) {
				answer = rs.getString("optionname");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return answer;
	}

}
