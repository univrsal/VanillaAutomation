package de.universallp.va.core.util;

import de.universallp.va.core.util.libs.LibNames;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

/**
 * Created by universallp on 24.03.2016 23:28.
 */
public class LogHelper {


    public static void logInfo(String message, Object... data) {
        FMLLog.log(LibNames.MOD_NAME, Level.INFO, "[VanillaAutomation] " + String.format(message, data));
    }

    public static void logError(String message, Object... data) {
        FMLLog.log(LibNames.MOD_NAME, Level.ERROR, String.format(message, data));
    }

    public static void logException(String string, Exception exception, boolean stopGame) {
        FMLLog.log(LibNames.MOD_NAME, Level.FATAL, "[VanillaAutomation] " + string, exception);
        exception.printStackTrace();
        if (stopGame)
            FMLCommonHandler.instance().getSidedDelegate().haltGame(string, exception);
    }

}
