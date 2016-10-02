package ru.goblin.htmlparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by Александр on 02.10.2016.
 */
public class Calculate {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private String url;
    private String css_class;
    private List<String> news = new LinkedList<>();
    private Map<String, Integer> map = new HashMap<>();
    private  List<Map.Entry<String, Integer>> sorted;

    public Calculate(String url, String css_class) {
        this.url = url;
        this.css_class = css_class;
    }

    /**
     * Метод получает текст со страницы
     */
    public void readText() {
        LOG.info("Start read site {}", url);
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            LOG.info("Start parse site by \"{}\" css-class", css_class);
            Elements elements = doc.getElementsByClass(css_class);
            for (Element element : elements) {
                news.add(element.text());
                LOG.debug(element.text());
            }
            LOG.info("Finish read site {}", url);
        } catch (IOException e) {
            LOG.error("Ошибка при чтении сайта: {}", e.getMessage());
        }
    }

    /**
     * Метод парсит прочитанный текст
     *
     * @param words слова для поиска
     */
    public void parse(String... words) {
        LOG.info("Prepare tokens");
        List<String> list = Arrays.asList(words);
        StringTokenizer tokenizer;

        for (String s : list) {
            map.put(s.toLowerCase(), 0);
        }
        String word;
        LOG.info("Start processing news");
        LOG.info("Words number: {}", list.size());
        for (String item : news) {
            tokenizer = new StringTokenizer(item, " ,.:;\"");
            while (tokenizer.hasMoreElements()) {
                word = tokenizer.nextToken().trim().toLowerCase();
                if (map.containsKey(word)) {
                    map.put(word, (map.get(word) + 1));
                }
            }
        }
        LOG.info("Finish processing news");
    }

    /**
     * Метод проводит сортировку значений карты по значению
     */
    public void sortResult(){
        LOG.info("Start sorting");
        sorted = new ArrayList<>(map.entrySet());
        Collections.sort(sorted, (a, b) -> b.getValue() - a.getValue());
        LOG.info("Finish sorting");
    }

    /**
     * Метод выводит результаты после сортировки
     */
    public void printResults() {
        System.out.println("Results: ");
        for (Map.Entry<String, Integer> entry : sorted) {
            System.out.println(entry.getKey() + " : "+ entry.getValue());
        }
    }
}
