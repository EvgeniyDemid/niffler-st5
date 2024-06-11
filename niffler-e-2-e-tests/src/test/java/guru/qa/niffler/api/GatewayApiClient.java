package guru.qa.niffler.api;

import guru.qa.niffler.model.*;

import java.util.List;

public class GatewayApiClient extends ApiClient {

	private final GatewayApi gatewayApi;

	public GatewayApiClient() {
		super(CFG.spendUrl());
		this.gatewayApi = retrofit.create(GatewayApi.class);
	}

	public List<SpendJson> getSpends(String bearerToken) throws Exception {
		return gatewayApi.getSpends(bearerToken).execute().body();
	}

	public List<CategoryJson> getCategories(String bearerToken) throws Exception {
		return gatewayApi.getCategories(bearerToken).execute().body();
	}

	public CategoryJson addCategory(String bearerToken, CategoryJson categoryJson) throws Exception {
		return gatewayApi.addCategory(bearerToken, categoryJson).execute().body();
	}

	public List<UserJson> getAllFriends(String bearerToken) throws Exception {
		return gatewayApi.getAllFriends(bearerToken).execute().body();
	}

	public void deleteFriend(String bearerToken, String targetUsername) throws Exception {
		gatewayApi.deleteFriend(bearerToken, targetUsername).execute();
	}

	public List<UserJson> getIncomeInvitations(String bearerToken) throws Exception {
		return gatewayApi.getIncomeInvitations(bearerToken).execute().body();
	}

	public UserJson sendInvitation(String bearerToken, FriendJson friend) throws Exception {
		return gatewayApi.sendInvitation(bearerToken, friend).execute().body();
	}

	public UserJson acceptInvitation(String bearerToken, FriendJson invitation) throws Exception {
		return gatewayApi.acceptInvitation(bearerToken, invitation).execute().body();
	}

	public UserJson declineInvitation(String bearerToken, FriendJson invitation) throws Exception {
		return gatewayApi.declineInvitation(bearerToken, invitation).execute().body();
	}

	public SessionJson getSession(String bearerToken) throws Exception {
		return gatewayApi.getSession(bearerToken).execute().body();
	}

	public SpendJson addSpend(String bearerToken, SpendJson spendJson) throws Exception {
		return gatewayApi.addSpend(bearerToken, spendJson).execute().body();
	}

	public SpendJson editSpend(String bearerToken, SpendJson spendJson) throws Exception {
		return gatewayApi.editSpend(bearerToken, spendJson).execute().body();
	}

	public void deleteSpends(String bearerToken, List<String> ids) throws Exception {
		gatewayApi.deleteSpends(bearerToken, ids).execute();
	}

	public List<StatisticJson> getTotalStatistic(String bearerToken) throws Exception {
		return gatewayApi.getTotalStatistic(bearerToken).execute().body();
	}

	public UserJson getCurrentUser(String bearerToken) throws Exception {
		return gatewayApi.getCurrentUser(bearerToken).execute().body();
	}

	public List<UserJson> getAllUser(String bearerToken) throws Exception {
		return gatewayApi.getAllUser(bearerToken).execute().body();
	}

	public UserJson updateUserInfo(String bearerToken, UserJson userJson) throws Exception {
		return gatewayApi.updateUserInfo(bearerToken, userJson).execute().body();
	}

	public List<CurrencyJson> getAllCurrencies(String bearerToken) throws Exception {
		return gatewayApi.getAllCurrencies(bearerToken).execute().body();
	}
}
