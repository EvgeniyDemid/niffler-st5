package guru.qa.niffler.utils;

import guru.qa.niffler.model.UserJson;

import java.util.ArrayList;

public class UserUtils {
	public static String userFormat(UserJson... usersJson) {
		ArrayList<String> listUser = new ArrayList<>();
		for (UserJson user : usersJson) {
			listUser.add("- " + user.username() +
					" | " + user.firstname() + " " + user.surname());
		}
		return listUser.toString();
	}
}
