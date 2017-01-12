/* Считайте с консоли имя файла, который имеет HTML-формат
Пример:
Info about Leela <span xml:lang="en" lang="en"><b><span>Turanga Leela
</span></b></span><span>Super</span><span>girl</span>
Первым параметром в метод main приходит тег. Например, "span"
Вывести на консоль все теги, которые соответствуют заданному тегу
Каждый тег на новой строке, порядок должен соответствовать порядку следования в файле
Количество пробелов, \n, \r не влияют на результат
Файл не содержит тег CDATA, для всех открывающих тегов имеется отдельный закрывающий тег, одиночных тегов нету
Тег может содержать вложенные теги
Пример вывода:
<span xml:lang="en" lang="en"><b><span>Turanga Leela</span></b></span>
<span>Turanga Leela</span>
<span>Super</span>
<span>girl</span>

Шаблон тега:
<tag>text1</tag>
<tag text2>text1</tag>
<tag
text2>text1</tag>

text1, text2 могут быть пустыми
*/

import java.io.*;

import java.util.Map;
import java.util.TreeMap;

public class Solution {
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FileReader fileReader = new FileReader(reader.readLine());
        String S = "";
        while (fileReader.ready()) {
            S += (char)fileReader.read();
        }

        int startOpeningIndex=0;
        int startClosingIndex=0;
        int lastIndex = S.lastIndexOf("</" + args[0]);
        Map<Integer, String> tagsIndexes = new TreeMap<>();

        while (true) {
            int lastOpeningIndex = S.indexOf("<" + args[0], startOpeningIndex);
            tagsIndexes.put(lastOpeningIndex, "<tag");
            startOpeningIndex = lastOpeningIndex+1;
            int lastClosingIndex = S.indexOf("</" + args[0] + ">",startClosingIndex);

            tagsIndexes.put(lastClosingIndex, "</tag>");
            if (lastClosingIndex == lastIndex) break;
            startClosingIndex = lastClosingIndex+1;

        }

      /** for (Map.Entry<Integer,String> map:tagsIndexes.entrySet()) {
            System.out.println(map.getKey() + ": " + map.getValue());}*/
        // here starting finding closing tags for each opening tags in map

        for (Map.Entry<Integer,String> map:tagsIndexes.entrySet()) {

            if (map.getValue().equals("<tag")) {
                int indexOfCurrentTag = map.getKey();

                int conditionOfLastTag = 1;
                // here for each opening tags open new same map (subMap) and finding closing tag in this map starting with
                // position of currentTag+1;
                int indexOfTag=0;
                    for (Map.Entry<Integer,String> subMap:tagsIndexes.entrySet())
                    {
                        indexOfTag = subMap.getKey();
                        // System.out.println(indexOfTag);
                        if (indexOfTag > indexOfCurrentTag)
                        {
                            if (subMap.getValue().equals("</tag>")) conditionOfLastTag--; else conditionOfLastTag++;
                            if (conditionOfLastTag == 0)
                            {

                                break;
                            }

                    }
                    }

                String subString = S.substring(indexOfCurrentTag,indexOfTag + args[0].length()+3);
                //subString.replaceAll("<","*");
                System.out.println(subString.replaceAll("\r\n|  ",""));
            }
        }
        reader.close();
        fileReader.close();
    }
}
