package seedu.address.model.alias;

import java.util.HashMap;
import java.util.Map;

public class AliasBook {

    private final Map<String, String> aliasMap = new HashMap<>();

    public void addAlias(Alias alias) {
        aliasMap.put(alias.getAliasWord(), alias.getCommandWord());
    }

    public void removeAlias(String key) {
        aliasMap.remove(key);
    }

    public boolean isAliasPresent(String aliasWord) {
        return aliasMap.containsKey(aliasWord);
    }

    public boolean isCommandPresent(String commandWord) {
        return aliasMap.containsValue(commandWord);
    }

    public String getCommandWordForAlias(String aliasWord) {
        return aliasMap.get(aliasWord);
    }

    public String getAliasForCommandWord(String commandWord) {
        return aliasMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(commandWord))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(commandWord);
    }

    public void clear() { // Method for future use
        aliasMap.clear();
    }

    public Map<String, String> getAliasMap() {
        return Map.copyOf(aliasMap);
    }

    @Override
    public String toString() {
        return aliasMap.toString();
    }
}
