package io.datadynamics.pilot.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.datadynamics.pilot.kafka.util.IOUtils;
import io.datadynamics.pilot.kafka.util.resource.DefaultResourceLoader;
import io.datadynamics.pilot.kafka.util.resource.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class SpELTest {

    private ExpressionParser expressionParser = new SpelExpressionParser();

    @Test
    public void sendFileringMessages() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map entity = mapper.readValue(getMessageJson(), Map.class);

        StandardEvaluationContext context = new StandardEvaluationContext(entity);

        // 메시지의 `id` 값 추출, spel로 필터링
        filteringMessages(context, entity);
    }

    private void filteringMessages(StandardEvaluationContext context, Map entity) {
        String defaultExpression = "filesize > 0 ";
        String spelExpression = "and datalakeName == 'test' and id == 'test'";
        Expression expression = expressionParser.parseExpression(defaultExpression + spelExpression);

        context.addPropertyAccessor(new MapAccessor());

        Boolean result = expression.getValue(context, Boolean.class);

        if (result != null && result) {
            // `id` 값으로 db에서 시스템 조회
            log.info((String) entity.get("id"));
        }
    }

    private String getMessageJson() throws IOException {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:test-message.json");
        InputStream is = resource.getInputStream();
        String json = new String(IOUtils.toByteArray(is));
        IOUtils.closeQuietly(is);
        return json;
    }
}