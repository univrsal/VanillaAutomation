package de.universallp.va.core.util;

/**
 * Created by universallp on 31.03.2016 14:57 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public interface ICustomField {

    void setStringField(int id, String val);

    String getStringField(int id);
}
