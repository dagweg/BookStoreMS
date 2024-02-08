package component;

import java.awt.*;

public class Fonts {    
    public static Font 
    headingFont, 
    headingFontB, 
    headingFont1, 
    headingFont1B;

    static Dimension dimWindow = Toolkit.getDefaultToolkit().getScreenSize();

    static{
        String fontName = "Tahoma";
        headingFont = new Font(fontName, 0, 20);
        headingFontB = new Font(fontName, Font.BOLD, 20);
        headingFont1 = new Font(fontName, 0, 15);
        headingFont1B = new Font(fontName, Font.BOLD, 15);
    }
}
