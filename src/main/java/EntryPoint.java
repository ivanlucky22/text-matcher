import com.bigid.config.ApplicationConfiguration;
import com.bigid.model.TextEntry;
import com.bigid.service.impl.TextSearchingServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.Arrays;


public class EntryPoint {
    public static void main(String[] args) {
        final File file = new File("src/test/resources/big.txt");
        final String[] keywords = ("James,John,Robert,Michael,William,David,Richard,Charles,Joseph,Thomas,Christopher,Daniel," +
                "Paul,Mark,Donald,George,Kenneth,Steven,Edward,Brian,Ronald,Anthony,Kevin,Jason,Matthew,Gary,Timothy,Jose," +
                "Larry,Jeffrey,Frank,Scott,Eric,Stephen,Andrew,Raymond,Gregory,Joshua,Jerry,Dennis,Walter,Patrick,Peter,Harold," +
                "Douglas,Henry,Carl,Arthur,Ryan,Roger").split(",");

        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        final TextSearchingServiceImpl main = ctx.getBean(TextSearchingServiceImpl.class);

        final MultiValueMap<String, TextEntry> result = main.findKeyWords(file, Arrays.asList(keywords));

        System.out.println("--- Please note that lineOffset and charOffsets starting from 1 ---");
        for (String keyword : result.keySet()) {
            System.out.printf("%s --> %s%n", keyword, result.get(keyword));
        }
    }
}
