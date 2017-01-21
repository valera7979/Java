package model;

import view.View;
import vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Валера on 14.01.2017.
 * У View есть метод update, в него передается список вакансий для отображения.
 Очевидно, что этот метод будет вызываться моделью, т.к. только она получает данные.
 Пришло время создать модель.

 1. Создай класс Model в пакете model.

 2. Добавь два поля - 1) вид, 2) массив провайдеров

 3. Создай конструктор с двумя параметрами - 1) вид, 2) массив провайдеров
 На невалидные данные кинь IllegalArgumentException

 4. Создай публичный метод void selectCity(String city).

 5. Реализуй логику метода selectCity:
 5.1. получить вакансии с каждого провайдера
 5.2. обновить вью списком вакансий из п.5.1.
 */
public class Model
{
    View view;
    Provider[] providers;

    public Model(View view, Provider... providers)
    {
        this.view = view;
        this.providers = providers;
        if (providers.length==0||view==null) throw new IllegalArgumentException();
    }

    public void selectCity(String city) throws IOException
    {
        List<Vacancy> vacancies = new ArrayList<>();
        for (Provider provider : providers)
        {
            try
            {
                for (Vacancy vac : provider.getJavaVacancies(city))
                {
                    vacancies.add(vac);
                }
            }
            catch (NullPointerException e)
            {

            }
        }
        view.update(vacancies);
    }

}
