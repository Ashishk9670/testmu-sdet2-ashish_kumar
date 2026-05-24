package driver;

import java.util.Locale;

public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE;

    public static BrowserType from(String browser) {
        return BrowserType.valueOf(browser.trim().toUpperCase(Locale.ROOT));
    }
}
