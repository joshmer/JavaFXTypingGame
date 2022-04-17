import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class KeyCodeMacher {
    private KeyCode kc;
    private Text t;

    KeyCodeMacher(KeyCode kc, Text t) {
        this.kc = kc;
        this.t = t;
    }

    public void setKc(KeyCode kc) {
        this.kc = kc;
    }

    public KeyCode getKc() {
        return kc;
    }

    public void setT(Text t) {
        this.t = t;
    }

    public Text getT() {
        return t;
    }

    public boolean match() {
        return match(this.kc, this.t);
    }

    public static boolean match(KeyCode kc, Text t) {
        if (kc.equals(KeyCode.DIGIT0)) {
            if (t.getText().equals(")"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT1)) {
            if (t.getText().equals("!"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT2)) {
            if (t.getText().equals("@"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT3)) {
            if (t.getText().equals("#"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT4)) {
            if (t.getText().equals("$"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT5)) {
            if (t.getText().equals("%"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT6)) {
            if (t.getText().equals("^"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT7)) {
            if (t.getText().equals("&"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT8)) {
            if (t.getText().equals("*"))
                return true;
        } else if (kc.equals(KeyCode.DIGIT9)) {
            if (t.getText().equals("("))
                return true;
        } else if (kc.equals(KeyCode.COMMA)) {
            if (t.getText().equals("<"))
                return true;
        } else if (kc.equals(KeyCode.PERIOD)) {
            if (t.getText().equals(">"))
                return true;
        } else if (kc.equals(KeyCode.SLASH)) {
            if (t.getText().equals("?"))
                return true;
        } else if (kc.equals(KeyCode.SEMICOLON)) {
            if (t.getText().equals(":"))
                return true;
        } else if (kc.equals(KeyCode.QUOTE)) {
            if (t.getText().equals("\""))
                return true;
        } else if (kc.equals(KeyCode.OPEN_BRACKET)) {
            if (t.getText().equals("{"))
                return true;
        } else if (kc.equals(KeyCode.CLOSE_BRACKET)) {
            if (t.getText().equals("}"))
                return true;
        } else if (kc.equals(KeyCode.BACK_SLASH)) {
            if (t.getText().equals("|"))
                return true;
        } else if (kc.equals(KeyCode.EQUALS)) {
            if (t.getText().equals("+"))
                return true;
        } else if (kc.equals(KeyCode.MINUS)) {
            if (t.getText().equals("_"))
                return true;
        } else if (kc.equals(KeyCode.BACK_QUOTE)) {
            if (t.getText().equals("~"))
                return true;
        } else {
            if (t.getText().equals(kc.toString()))
                return true;
        }
        return false;
    }
}
