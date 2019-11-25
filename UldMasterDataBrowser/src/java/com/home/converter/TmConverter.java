package com.home.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Konvertierer fuer "TM" Format.<br>
 * TM Format: hhmmssiii<br>
 * hh --> Stunden (0-23)<br>
 * mm --> Minuten<br>
 * ss --> Sekunden<br>
 * iii --> Mircosekunden (optional)<br>
 */
public class TmConverter implements Converter {

    private static final Log LOG = LogFactory.getLog(TmConverter.class);
    public static final String CONVERTER_ID = "TmConverter";

    /**
     * Ein "TM" wird als String formatiert.<br>
     * Beispiel: "11:23:45.321" wird zu "112345321"
     *
     * @param context   the JSF context
     * @param component the Faces component
     * @param value     the String to format
     *
     * @return the formatted String as an Object
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        String temp;

        LOG.debug("-->getAsObject [" + value + "]");

        temp = value.replace(":", "").replace(".", "").replace(" ", "");

        LOG.debug("-->RETURN [" + temp + "]");

        return temp;
    }

    /**
     * Ein String wird als "TM" formatiert.<br>
     * Beispiel: "112345321" wird zu "11:23:45.321"
     *
     * @param context   the JSF context
     * @param component the Faces component
     * @param value     the Object to format
     *
     * @return the formatted result as a String
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if (value == null) {
            LOG.debug("-->RETURN (null)");
            return null;
        }
        else if (value instanceof String) {
            String ref = (String) value;

            switch (ref.length()) {
                case 0:
                    return ref;
                case 1:
                case 2:
                    return ref;
                case 3:
                case 4:
                    return ref.substring(0, 2) + ":" + ref.substring(2);
                case 5:
                case 6:
                    return ref.substring(0, 2) + ":" + ref.substring(2, 4) + ":"
                            + ref.substring(4);
                default:
                    return ref.substring(0, 2) + ":" + ref.substring(2, 4) + ":"
                            + ref.substring(4, 6) + "." + ref.substring(6);
            }
        }
        else {
            throw new ConverterException("Wrong Type: [" + value.getClass() + "] has to be 'String'!");
        }
    }
}
