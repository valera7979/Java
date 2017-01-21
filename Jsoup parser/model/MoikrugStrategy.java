package model;

import vo.Vacancy;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Валера on 21.01.2017.
 * 1. По аналогии с HHStrategy добавь стратегию для Мой круг.
 Назови класс MoikrugStrategy, реализуй метод getVacancies.

 Вот тебе пример ссылки:
 https://moikrug.ru/vacancies?q=java+Dnepropetrovsk

 Пример ссылки на вакансию:
 https://moikrug.ru/vacancies/560164256
 */
public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?page=%d&q=java+%s";

    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
    private static final String referrer = "http://javarush.ru/";


    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, page, searchString);
        Document doc = Jsoup.connect(url).
                userAgent(userAgent).referrer(referrer).get();
        return doc;

    }


    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException
    {
        List<Vacancy> Vacancies = new ArrayList<>();
        int pageNum = 1;
        Document doc = null;

        while(true)
        {
            doc = getDocument(searchString, pageNum);
            Elements vacancies = doc.getElementsByClass("job");
            if (vacancies.size()==0) break;

            for (Element element: vacancies)
            {
                if (element != null)
                {
                    Vacancy vac = new Vacancy();

                    String title, salary, city, companyName, siteName, url;

                    title = element.getElementsByAttributeValue("class", "title").text();
                    salary = element.getElementsByAttributeValue("class", "salary").text();
                    city = element.getElementsByAttributeValue("class", "location").text();
                    companyName = element.getElementsByAttributeValue("class", "company_name").text();
                    url = element.select("a[class=job_icon]").attr("href");

                    vac.setTitle(title);
                    if (salary.length()==0) vac.setSalary(""); else vac.setSalary(salary);
                    if (city.length()==0) vac.setCity("");
                    else vac.setCity(city);
                    vac.setCompanyName(companyName);
                    vac.setSiteName(URL_FORMAT);
                    vac.setUrl("https://moikrug.ru" + url);

                    Vacancies.add(vac);
                }
            }
            pageNum++;
        }


        return Vacancies;
    }
}
