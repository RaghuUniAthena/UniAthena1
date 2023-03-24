package utility;

public class InstanceRepo {
	static GenericUtility u;
	static ExtentReport rep;

	public static GenericUtility getGenericUtilityInstance() {
		return u;
	}
	public static void setGenericUtilityInstance(GenericUtility gu) {
		u=gu;
	}
	public static ExtentReport getExtentReportInstance() {
		return rep;
	}
	public static void setExtentReportInstance(ExtentReport erep) {
		rep=erep;
	}
}
