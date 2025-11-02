package miranda.gabriel.tenus.adapters.outbounds.langchain;

import miranda.gabriel.tenus.adapters.outbounds.langchain.dto.TaskScheduleExtraction;

/**
 * Port para serviço de processamento de linguagem natural com LangChain.
 * Permite extrair informações estruturadas de comandos em linguagem natural.
 */
public interface LangChainServicePort {
    
    /**
     * Processa um comando em linguagem natural para extrair informações de agendamento de tarefa.
     * 
     * @param userPrompt comando do usuário (ex: "Mude essa tarefa para segunda-feira às 14h")
     * @param currentTaskInfo informações atuais da tarefa para contexto
     * @return informações extraídas sobre data e horário
     */
    TaskScheduleExtraction extractTaskSchedule(String userPrompt, String currentTaskInfo);
}
