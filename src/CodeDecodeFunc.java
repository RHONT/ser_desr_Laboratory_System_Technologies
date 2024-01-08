import java.util.List;
import java.util.Map;

public class CodeDecodeFunc {

    public static String fillStringSerialise() {
        StringBuilder strBuild = new StringBuilder();
        for (Map.Entry<Byte, List<Short>> element : bucket.entrySet()) {
            if (!element.getValue().isEmpty()) {
                strBuild.append(" ").append(element.getKey());
                for (Short valShort : element.getValue()) {
                    char ch = (char) (short) valShort;
                    strBuild.append(ch);
                }
            }
        }
        return strBuild.toString();

    }

}
