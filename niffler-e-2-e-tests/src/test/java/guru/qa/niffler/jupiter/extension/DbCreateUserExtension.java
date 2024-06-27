package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepositoryStringJdbc;
import guru.qa.niffler.data.repository.logging.JsonAllureAppender;
import guru.qa.niffler.model.UserJson;

import java.util.Arrays;

public class DbCreateUserExtension extends CreateUserExtension {

	private final UserRepositoryStringJdbc userRepositoryStringJdbc = new UserRepositoryStringJdbc();
	private final JsonAllureAppender jsonAllureAppender = new JsonAllureAppender();

	@Override
	UserJson createUser(UserJson user) {
		UserEntity userEntity = userRepositoryStringJdbc.createUserInUserdata(new UserEntity().fromJson(user));
		UserAuthEntity userAuthEntity = userRepositoryStringJdbc.createUserInAuth(new UserAuthEntity().fromJson(user));
		jsonAllureAppender.logJson("userAuthEntity", userAuthEntity);
		jsonAllureAppender.logJson("userEntity", userEntity);

		return new UserJson(
				userEntity.getId(),
				userEntity.getUsername(),
				userEntity.getFirstname(),
				userEntity.getSurname(),
				userEntity.getCurrency(),
				Arrays.toString(userEntity.getPhoto()),
				Arrays.toString(userEntity.getPhotoSmall()),
				user.friendState(),
				user.testData()
		);
	}
}
