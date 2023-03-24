package repo;

public class UserDetailsRepo {
	String strAthenaId;
	String strCourseId;

	public String getAthenaId() {
		return strAthenaId;
	}

	public void setAthenaId(String strAthenaId) {
		strAthenaId=strAthenaId.replace("ID:", "").replace("Athena", "").trim();
		this.strAthenaId = strAthenaId;
	}

	public String getCourseId() {
		return strCourseId;
	}

	public void setCourseId(String courseId) {
		this.strCourseId = courseId;
	}

}
