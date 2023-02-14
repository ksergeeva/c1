import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> mapWord;

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        mapWord = new HashMap<>();
        Map<String, Integer> resultWord = new HashMap<>();

        List<File> listOfPDFFiles = List.of(Objects.requireNonNull(pdfsDir.listFiles()));
        for (File filePdf : listOfPDFFiles) {
            var doc = new PdfDocument(new PdfReader(filePdf));

            for (var pageName = 1; pageName <= doc.getNumberOfPages(); pageName++) {
                var text = PdfTextExtractor.getTextFromPage(doc.getPage(pageName));
                var words = text.split("\\P{IsAlphabetic}+");

                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    resultWord.put(word.toLowerCase(), resultWord.getOrDefault(
                            word.toLowerCase(), 0) + 1);
                }
                int count;
                for (var w : resultWord.keySet()) {
                    String count1 = w.toLowerCase();
                    if (resultWord.get(count1) != null) {
                        count = resultWord.get(count1);
                        mapWord.computeIfAbsent(count1, k -> new ArrayList<>()).add(
                                new PageEntry(filePdf.getName(), pageName, count));
                    }
                }
                resultWord.clear();
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> list = mapWord.get(word.toLowerCase());
        return list;
    }
}