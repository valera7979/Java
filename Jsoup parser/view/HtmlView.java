package view;

import Controller;
import vo.Vacancy;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Валера on 14.01.2017.
 * Пора заняться отображением вакансий.
 1. В методе update класса HtmlView реализуй следующую логику:
 1.1. сформируй новое тело файла vacancies.html, которое будет содержать вакансии
 1.2. запиши в файл vacancies.html его обновленное тело
 1.3. Все исключения должны обрабатываться в этом методе - выведи стек-трейс, если возникнет исключение.

 2. Для реализации п.1 создай два пустых private метода:
 String getUpdatedFileContent(List<Vacancy>), void updateFile(String)
 Реализовывать их логику будем в следующих заданиях.

 3. Чтобы добраться до файла vacancies.html, сформируй относительный путь к нему
 В классе HtmlView создай приватное строковое final поле filePath, присвой ему относительный путь к vacancies.html.
 Путь должен быть относительно корня проекта JavaRushHomeWork.
 Формируй путь динамически используя this.getClass().getPackage() и разделитель "/"
 Подсказка: путь должен начинаться с "./"
 */
public class HtmlView implements View
{
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replaceAll("\\.","/") +
            "/vacancies.html" ;


    Controller controller = null;

    @Override
    public void update(List<Vacancy> vacancies) {
        try
        {
            updateFile(getUpdatedFileContent(vacancies));
        } catch (Exception ex) {ex.printStackTrace();}


    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() throws IOException
    {
        controller.onCitySelect("Dnepropetrovsk");
    }

    /**
     * 2. Получи элемент, у которого есть класс template.
     Сделай копию этого объекта, удали из нее атрибут "style" и класс "template".
     Используй этот элемент в качестве шаблона для добавления новой строки в таблицу вакансий.

     3. Удали все добавленные ранее вакансии. У них единственный класс "vacancy".
     В файле backup.html это одна вакансия - Junior Java Developer.
     Нужно удалить все теги tr, у которых class="vacancy".
     Но тег tr, у которого class="vacancy template", не удаляй.
     Используй метод remove.

     4. В цикле для каждой вакансии:
     4.1. склонируй шаблон тега, полученного в п.2. Метод clone.
     4.2. получи элемент, у которого есть класс "city". Запиши в него название города из вакансии.
     4.3. получи элемент, у которого есть класс "companyName". Запиши в него название компании из вакансии.
     4.4. получи элемент, у которого есть класс "salary". Запиши в него зарплату из вакансии.
     4.5. получи элемент-ссылку с тегом a. Запиши в него название вакансии(title).
     Установи реальную ссылку на вакансию вместо href="url"
     4.6. добавь outerHtml элемента, в который ты записывал данные вакансии,
     непосредственно перед шаблоном <tr class="vacancy template" style="display: none">

     5. Верни html код всего документа в качестве результата работы метода.

     6. В случае возникновения исключения, выведи его стек-трейс и верни строку "Some exception occurred".

     7. Запусти приложение, убедись, что все вакансии пишутся в файл vacancies.html
     Для этого в HtmlView реализуй метод update.
     */
   private String getUpdatedFileContent(List<Vacancy> vacancies) {
       String updatedFile = null;
       Document doc = null;
       try  { doc = getDocument();
       Element template = doc.select("[class*=\"template\"]").first();
          // System.out.println(template);

       Element pattern = template.clone();
        pattern.removeAttr("style");
       pattern.removeClass("template");

           //remove all tags <tr> with only one class="vacancy"
           doc.select("tr[class=\"vacancy\"]").remove();

            for (Vacancy vacancy : vacancies) {
                Element vac = pattern.clone();
                vac.select(".city").first().text(vacancy.getCity());
               vac.select(".companyName").first().text(vacancy.getCompanyName());
                vac.select(".salary").first().text(vacancy.getSalary());
                vac.select("a").first().text(vacancy.getTitle());
                vac.select("a").first().attr("href", vacancy.getUrl());
                vac.outerHtml();
                template.before(vac.outerHtml());
            }
           updatedFile = doc.html();
       }
       catch (IOException e)
       {
           e.printStackTrace();
           return "Some exception occurred";
       }
              return updatedFile;
   }

    /***
     * Он принимает тело файла в виде строки. Нужно его записать в файл, который находится по пути filePath.
     Ты это хорошо умеешь делать, поэтому подробностей тут не будет.

     * @param string
     */

    private void updateFile(String string) {
            File file = new File(filePath);

            try {
                if (!file.exists())
                    file.createNewFile();
                PrintWriter out = new PrintWriter(file.getAbsoluteFile());
                try {
                    out.print(string);
                }
                finally  { out.close(); }
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }
    }

/**
 * 1. В HtmlView создай protected метод Document getDocument() throws IOException, в котором
 распарси файл vacancies.html используя Jsoup. Кодировка файла "UTF-8", используй поле filePath.


 */

    protected Document getDocument() throws IOException {
        File file = new File(filePath);
    Document doc = Jsoup.parse(file, "UTF-8");
        return doc;
    }

}
