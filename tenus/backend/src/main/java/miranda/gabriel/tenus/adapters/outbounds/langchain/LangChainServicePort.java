package miranda.gabriel.tenus.adapters.outbounds.langchain;

import miranda.gabriel.tenus.adapters.outbounds.langchain.dto.TaskScheduleExtraction;

public interface LangChainServicePort {
    
    TaskScheduleExtraction extractTaskSchedule(String userPrompt, String currentTaskInfo);
}
