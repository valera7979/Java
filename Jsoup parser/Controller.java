
import model.Model;
import model.Provider;
import vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *Теперь - как дебажить.
 Поставь брекпоинт, запусти в дебаг моде.
 нажми Alt+F8 (Run -> Evaluate Expression)
 В выражении (верхняя строка) напиши System.out.println("AAA") и нажми Alt+V (снизу кнопка Evaluate).
 Перейди в окно консоли, там вывелось "AAA".

 Используя это окно ты можешь смотреть текущие данные, заменять их на другие, нужные тебе.
 Можешь выполнять все, что хочешь. Например, удалить все элементы мапы, изменить значение любой переменной,
 присвоить новое значение либо обнулить ее.
 Поставь брекпоинт в Controller.onCitySelect, запусти в дебаг моде.
 Остановились на этом брекпоинте? Отлично, жми Alt+F8, сверху в строке
 cityName = "junior";
 далее жми Alt+V и F9, чтобы продолжить работу приложения.
 Список вакансий, который пришел в HtmlView.update, выполнялся для запроса "java junior".
 Используй окно Expression Evaluation, думаю, оно тебе понадобится в следующем пункте.
 */
public class Controller {

    Model model;

    public Controller(Model model)
    {
        if (model == null) throw new IllegalArgumentException();
        else
        this.model = model;
    }

   public void onCitySelect(String cityName) throws IOException
   {
       model.selectCity(cityName);
   }




}
