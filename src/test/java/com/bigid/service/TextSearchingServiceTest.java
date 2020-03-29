package com.bigid.service;

import com.bigid.config.ApplicationConfiguration;
import com.bigid.model.TextEntry;
import com.bigid.service.impl.TextSearchingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class})
class TextSearchingServiceTest {

    public static final String JOHN = "John";
    @Autowired
    private TextSearchingServiceImpl textSearchingService;

    @Test
    void testJohnIsFoundOnce() {
        final MultiValueMap<String, TextEntry> resultMap = textSearchingService.findKeyWords(getFile("john1.txt"), Arrays.asList(JOHN));
        assertNotNull(resultMap.get(JOHN));
        assertEquals(resultMap.get(JOHN).size(), 1);
    }

    @Test
    void testJohnIsFoundTwice() {
        final MultiValueMap<String, TextEntry> resultMap = textSearchingService.findKeyWords(getFile("john2.txt"), Arrays.asList(JOHN));
        assertNotNull(resultMap.get(JOHN));
        assertEquals(resultMap.get(JOHN).size(), 2);
    }

    @Test
    void testJohnIsFoundTwiceInOneLine() {
        final MultiValueMap<String, TextEntry> resultMap = textSearchingService.findKeyWords(getFile("john21.txt"), Arrays.asList(JOHN));
        assertNotNull(resultMap.get(JOHN));
        assertEquals(2, resultMap.get(JOHN).size());
    }

    @Test
    void testJohnIsNotFound() {
        final MultiValueMap<String, TextEntry> resultMap = textSearchingService.findKeyWords(getFile("john0.txt"), Arrays.asList(JOHN));
        assertNull(resultMap.get(JOHN));
    }

    @Test
    void testBigTextSearchWorksAsExpected() {
        final String[] split = ("James,John,Robert,Michael,William,David,Richard,Charles,Joseph,Thomas,Christopher,Daniel," +
                "Paul,Mark,Donald,George,Kenneth,Steven,Edward,Brian,Ronald,Anthony,Kevin,Jason,Matthew,Gary,Timothy,Jose," +
                "Larry,Jeffrey,Frank,Scott,Eric,Stephen,Andrew,Raymond,Gregory,Joshua,Jerry,Dennis,Walter,Patrick,Peter,Harold," +
                "Douglas,Henry,Carl,Arthur,Ryan,Roger").split(",");
        final MultiValueMap<String, TextEntry> resultMap = textSearchingService.findKeyWords(getFile("big.txt"), Arrays.asList(split));
        assertEquals(173, resultMap.get(JOHN).size());
        assertEquals(10, resultMap.get("Patrick").size());
        assertEquals(1, resultMap.get("Larry").size());
        assertEquals(3, resultMap.get("Christopher").size());
    }

    private File getFile(final String pathname) {
        return new File("src/test/resources/" + pathname);
    }
}
