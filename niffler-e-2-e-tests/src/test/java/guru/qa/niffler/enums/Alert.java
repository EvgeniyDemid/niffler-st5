package guru.qa.niffler.enums;

public enum Alert {
	INVITATION_IS_SENT("Invitation is sent"),
	INVITATION_IS_ACCEPTED("Invitation is accepted"),
	FRIEND_IS_DELETED("Friend is deleted"),
	INVITATION_IS_DECLINED("Invitation is declined"),
	CAN_NOT_ADD_NEW_CATEGORY("Can not add new category"),
	NEW_CAN_NOT_ADD_NEW_CATEGORY_ADDED("New category added"),
	SPEND_SUCCESSFULLY_ADD("Spending successfully added"),
	SPEND_SUCCESSFULLY_DELETE("Spending successfully deleting"),
	PROFILE_SUCCESSFULLY_UPDATE("Profile successfully updated"),
	SPEND_SUCCESSFULLY_UPDATED("Spending successfully updated");

	private final String value;

	Alert(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
