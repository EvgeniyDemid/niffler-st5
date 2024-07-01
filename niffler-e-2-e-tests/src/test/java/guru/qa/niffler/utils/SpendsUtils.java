package guru.qa.niffler.utils;

import guru.qa.niffler.model.SpendJson;

import java.util.ArrayList;

public class SpendsUtils {
	public static String spendFormat(SpendJson... spendsJson) {
		ArrayList<String> listSpends = new ArrayList<>();
		for (SpendJson spendJson : spendsJson) {
			listSpends.add("- " + DateUtils.formatDate(
					spendJson.spendDate(),
					"dd MMM yy") +
					" | " + spendJson.amount() +
					" | " + spendJson.currency() +
					" | " + spendJson.category() +
					" | " + spendJson.description());
		}
		return listSpends.toString();
	}
}
