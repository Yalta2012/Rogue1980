package Domain.Logger;

import Domain.GameConstants;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Logger {

    public List<String> logs = new LinkedList<>();
    public Collection<String> getLogs(){
        return logs;
    }

    public void addLog(String log) {
        logs.addLast(log);
        if (logs.size() > GameConstants.LOG_CAPACITY) {
            logs.removeFirst();
        }
    }

    public Logger(){}
    public Logger(Collection<String> logs) {
        this.logs = new LinkedList<>(logs);
    }
}
