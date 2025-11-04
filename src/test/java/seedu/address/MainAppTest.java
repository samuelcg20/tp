package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.UserPrefsStorage;

public class MainAppTest {

    @TempDir
    public Path tempDir;

    @Test
    public void initConfig_nullPath_usesDefaultConfig() {
        MainApp app = new MainApp();
        Config config = app.initConfig(null);
        assertNotNull(config);
    }

    @Test
    public void initConfig_existingValidFile_loadsConfig() throws Exception {
        Path configFile = tempDir.resolve("config.json");

        // Manually save a config file using ConfigUtil
        Config originalConfig = new Config();
        originalConfig.setLogLevel(Level.WARNING);
        originalConfig.setUserPrefsFilePath(tempDir.resolve("prefs.json"));
        ConfigUtil.saveConfig(originalConfig, configFile);

        MainApp app = new MainApp();
        Config loadedConfig = app.initConfig(configFile);

        assertEquals(Level.WARNING, loadedConfig.getLogLevel());
        assertEquals(tempDir.resolve("prefs.json"), loadedConfig.getUserPrefsFilePath());
    }

    @Test
    public void initConfig_corruptFile_usesDefaultConfig() throws Exception {
        Path configFile = tempDir.resolve("config.json");
        Files.writeString(configFile, "invalid-json-content");

        MainApp app = new MainApp();
        Config config = app.initConfig(configFile);

        assertNotNull(config);
        assertEquals(Level.INFO, config.getLogLevel());
        assertEquals(Paths.get("preferences.json"), config.getUserPrefsFilePath());
    }

    @Test
    public void initPrefs_missingFile_createsNewPrefs() {
        Path prefsFile = tempDir.resolve("prefs.json");
        UserPrefsStorage storage = new JsonUserPrefsStorage(prefsFile);

        MainApp app = new MainApp();
        UserPrefs prefs = app.initPrefs(storage);

        assertNotNull(prefs);
        assertTrue(Files.exists(prefsFile));
    }

    @Test
    public void initPrefs_existingFile_loadsPrefs() throws Exception {
        Path prefsFile = tempDir.resolve("prefs.json");
        UserPrefs expectedPrefs = new UserPrefs();
        new JsonUserPrefsStorage(prefsFile).saveUserPrefs(expectedPrefs);

        MainApp app = new MainApp();
        UserPrefs loadedPrefs = app.initPrefs(new JsonUserPrefsStorage(prefsFile));

        assertEquals(expectedPrefs, loadedPrefs);
    }

    @Test
    public void initPrefs_corruptedFile_returnsDefaultPrefs() throws Exception {
        Path prefsFile = tempDir.resolve("prefs.json");
        Files.writeString(prefsFile, "corrupted content");

        MainApp app = new MainApp();
        UserPrefs prefs = app.initPrefs(new JsonUserPrefsStorage(prefsFile));

        assertNotNull(prefs);
    }

}
