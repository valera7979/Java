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
 1. В классе HHStrategy создай protected метод Document getDocument(String searchString, int page) throws IOException.

 */
public class HHStrategy implements Strategy {

 private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

 private static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 " +
         "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
 private static final String referrer = "";


 protected Document getDocument(String searchString, int page) throws IOException {

  Document doc = Jsoup.connect(String.format(URL_FORMAT,searchString,page)).
          userAgent(userAgent).referrer(referrer).get();
  return doc;

 }

 @Override
 public List<Vacancy> getVacancies(String searchString) throws IOException
 {
  List<Vacancy> Vacancies = new ArrayList<>();
  int pageNum = 0;

  while(pageNum<2)
  {
      Document doc = null;
       try  { doc = getDocument(searchString, pageNum); }
       catch (HttpStatusException ex)  {   }

             Elements vacancies = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
         if (vacancies.size()==0) break;

           for (Element element: vacancies)
           {
                if (element != null)
                {
                      Vacancy vac = new Vacancy();

                      String title, salary, city, companyName, siteName, url;

                      title = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text();
                      salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text();
                      city = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text();
                      companyName = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text();
                      url = element.select("a[data-qa=vacancy-serp__vacancy-title]").attr("href");

                      vac.setTitle(title);
                      if (salary.length()==0) vac.setSalary(""); else vac.setSalary(salary);
                      vac.setCity(city);
                      vac.setCompanyName(companyName);
                      vac.setSiteName(URL_FORMAT);
                      vac.setUrl(url);

                      Vacancies.add(vac);
                }
           }
            pageNum++;
  }


  return Vacancies;
 }
}
