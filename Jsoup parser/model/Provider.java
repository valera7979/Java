package model;

import vo.Vacancy;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * * 1. Добавь в интерфейс метод getVacancies(String searchString), который будет возвращать список вакансий.

 2. Поправь ошибки в классе HHStrategy.

 3. Вернись в метод getJavaVacancies класса Provider, реализуй его логику из расчета, что всех данных хватает.

 4. Давай попробуем запустить и посмотреть, как работает наша программа.
 В методе main вместо вывода на экран напиши controller.scan();
 Воспользуйся подскайкой IDEA и создай метод.
 Внутри метода пройдись по всем провайдерам и собери с них все вакансии, добавь их в список. Выведи количество вакансий в консоль.

 5. Исправь NPE, если появляется это исключение (поставь заглушку).
 */
public class Provider {

    private Strategy strategy;

    public List<Vacancy> getJavaVacancies(String searchString) throws IOException
    {

          return strategy.getVacancies(searchString) ==
                null ? Collections.<Vacancy>emptyList() : strategy.getVacancies(searchString);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Provider(Strategy strategy) {

        this.strategy = strategy;
    }
}
