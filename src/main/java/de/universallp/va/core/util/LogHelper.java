package de.universallp.va.core.util;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

/**
 * Created by universallp on 24.03.2016 23:28.
 */
public class LogHelper {


    public static void log(String message, Object... data) {
        FMLLog.log(References.MOD_NAME, Level.INFO,
                String.format(message, data));
    }

    public static void logError(String message, Object... data) {
        FMLLog.log(References.MOD_NAME, Level.ERROR,
                String.format(message, data));
    }

    public static void logException(String string, Exception exception, boolean stopGame) {
        String s = "";
        FMLLog.log(References.MOD_NAME, Level.FATAL, string, exception);
        exception.printStackTrace();
        if (stopGame)
            FMLCommonHandler.instance().getSidedDelegate().haltGame(string, exception);
    }

}
