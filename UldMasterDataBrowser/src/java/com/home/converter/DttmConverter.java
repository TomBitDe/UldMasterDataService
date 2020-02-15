package com.home.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Converter for the "DTTM" format.<br>
 * DTTM Format: YYYYMMDDhhmmssiii<br>
 * YYYY -- Jahr<br>
 * MM -- Monat<br>
 * DD -- Tag<br>
 * hh -- Stunden (0-23)<br>
 * mm -- Minuten<br>
 * ss -- Sekunden<br>
 * iii -- Mircosekunden (optional)<br>
 */
@FacesConverter("com.home.converter.DttmConverter")
public class DttmConverter implements Converter {

    private static final Logger LOG = LogManager.getLogger(DttmConverter.class.getName());
    public static final String CONVERTER_ID = "DttmConverter";

    /**
     * Format a "DTTM" as a String.<br>
     * Example: "2008.06.20 11:23:45.321" will be formatted to "20080620112345321"
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
     * Format a String as a "DTTM".<br>
     * Example: "20080620112345321" will be formatted to "2008.06.20 11:23:45.321"
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
                case 3:
                case 4:
                    return ref;
                case 5:
                case 6:
                    return ref.substring(0, 4) + "." + ref.substring(4);
                case 7:
                case 8:
                    return ref.substring(0, 4) + "." + ref.substring(4, 6) + "."
                            + ref.substring(6);
                case 9:
                case 10:
                    return ref.substring(0, 4) + "." + ref.substring(4, 6) + "."
                            + ref.substring(6, 8) + " " + ref.substring(8);
                case 11:
                case 12:
                    return ref.substring(0, 4) + "." + ref.substring(4, 6) + "."
                            + ref.substring(6, 8) + " " + ref.substring(8, 10)
                            + ":" + ref.substring(10);
                case 13:
                case 14:
                    return ref.substring(0, 4) + "." + ref.substring(4, 6) + "."
                            + ref.substring(6, 8) + " " + ref.substring(8, 10)
                            + ":" + ref.substring(10, 12) + ":" + ref.substring(12);
                default:
                    return ref.substring(0, 4) + "." + ref.substring(4, 6) + "."
                            + ref.substring(6, 8) + " " + ref.substring(8, 10)
                            + ":" + ref.substring(10, 12) + ":" + ref.substring(12, 14)
                            + "." + ref.substring(14);
            }
        }
        else {
            throw new ConverterException("Wrong Type: [" + value.getClass() + "] has to be 'String'!");
        }
    }
}
