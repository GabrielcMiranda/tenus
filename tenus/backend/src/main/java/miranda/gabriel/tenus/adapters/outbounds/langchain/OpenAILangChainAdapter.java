package miranda.gabriel.tenus.adapters.outbounds.langchain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.adapters.outbounds.langchain.dto.TaskScheduleExtraction;

@Service
@Slf4j
public class OpenAILangChainAdapter implements LangChainServicePort {

    private final TaskScheduleAssistant assistant;
    
    public OpenAILangChainAdapter(@Value("${langchain.openai.api-key}") String apiKey) {
        ChatLanguageModel model = OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gpt-4o-mini")
            .temperature(0.0) 
            .build();
        
        this.assistant = AiServices.create(TaskScheduleAssistant.class, model);
    }

    @Override
    public TaskScheduleExtraction extractTaskSchedule(String userPrompt, String currentTaskInfo) {
        log.info("Processing user prompt: {}", userPrompt);
        log.info("Current task info: {}", currentTaskInfo);
        
        try {
         
            String fullPrompt = buildPrompt(userPrompt, currentTaskInfo);
            
           
            String response = assistant.extractScheduleInfo(fullPrompt);
            
            log.info("AI Response: {}", response);
            
            return parseResponse(response);
            
        } catch (Exception e) {
            log.error("Error processing prompt with AI: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process AI request: " + e.getMessage(), e);
        }
    }

    private String buildPrompt(String userPrompt, String currentTaskInfo) {
        return String.format("""
            Você é um assistente que extrai informações de agendamento de tarefas a partir de comandos em português.
            
            TAREFA ATUAL:
            %s
            
            COMANDO DO USUÁRIO:
            "%s"
            
            INSTRUÇÕES:
            - Extraia APENAS as informações de agendamento mencionadas no comando
            - Se algo não foi mencionado, retorne NULL para esse campo
            - Interprete referências relativas (ex: "segunda-feira" → próxima segunda)
            - Use o formato de data YYYY-MM-DD
            - Use o formato de hora HH:mm (24 horas)
            
            RESPONDA NO SEGUINTE FORMATO JSON:
            {
                "date": "YYYY-MM-DD ou null",
                "startTime": "HH:mm ou null",
                "endTime": "HH:mm ou null",
                "explanation": "Explicação do que você entendeu"
            }
            
            Responda APENAS com o JSON, sem markdown nem explicações adicionais.
            """, currentTaskInfo, userPrompt);
    }

    private TaskScheduleExtraction parseResponse(String jsonResponse) {
        try {
            String cleanJson = jsonResponse
                .replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();
            
            LocalDate date = extractDate(cleanJson);
            LocalTime startTime = extractTime(cleanJson, "startTime");
            LocalTime endTime = extractTime(cleanJson, "endTime");
            String explanation = extractExplanation(cleanJson);
            
            return new TaskScheduleExtraction(date, startTime, endTime, explanation);
            
        } catch (Exception e) {
            log.error("Error parsing AI response: {}", jsonResponse, e);
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }

    private LocalDate extractDate(String json) {
        String dateStr = extractJsonValue(json, "date");
        if (dateStr == null || dateStr.equals("null")) {
            return null;
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private LocalTime extractTime(String json, String fieldName) {
        String timeStr = extractJsonValue(json, fieldName);
        if (timeStr == null || timeStr.equals("null")) {
            return null;
        }
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String extractExplanation(String json) {
        return extractJsonValue(json, "explanation");
    }

    private String extractJsonValue(String json, String fieldName) {
        String pattern = "\"" + fieldName + "\"\\s*:\\s*\"([^\"]+)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        
        pattern = "\"" + fieldName + "\"\\s*:\\s*(null)";
        p = java.util.regex.Pattern.compile(pattern);
        m = p.matcher(json);
        if (m.find()) {
            return "null";
        }
        
        return null;
    }

    interface TaskScheduleAssistant {
        String extractScheduleInfo(String prompt);
    }
}
