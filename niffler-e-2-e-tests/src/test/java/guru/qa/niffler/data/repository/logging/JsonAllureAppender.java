package guru.qa.niffler.data.repository.logging;

import com.google.gson.Gson;
import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import lombok.SneakyThrows;

public class JsonAllureAppender {

	private final String templateName = "json.ftl";
	private final AttachmentProcessor<AttachmentData> attachmentProcessor = new DefaultAttachmentProcessor();
	Gson gson = new Gson();

	@SneakyThrows
	public void logJson(String jsonName, Object object) {
		String json = gson.toJson(object);
		JsonAttachment attachment = new JsonAttachment(jsonName, json);
		attachmentProcessor.addAttachment(attachment, new FreemarkerAttachmentRenderer(templateName));
	}
}
