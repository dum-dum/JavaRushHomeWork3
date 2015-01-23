package com.javarush.test.level17.lesson10.home09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* Транзакционность
Сделать метод joinData транзакционным, т.е. если произошел сбой, то данные не должны быть изменены.
1. Считать с консоли 2 имени файла
2. Считать построчно данные из файлов. Из первого файла - в allLines, из второго - в forRemoveLines
В методе joinData:
3. Если список allLines содержит все строки из forRemoveLines, то удалить из списка allLines все строки,
 которые есть в forRemoveLines
4. Если список allLines НЕ содержит каких-либо строк, которые есть в forRemoveLines, то
4.1. очистить allLines от данных
4.2. выбросить исключение CorruptedDataException
Сигнатуру метода main не менять.  Метод joinData должен вызываться в main.
*/

public class Solution {
    public static List<String> allLines = new ArrayList<String>();
    public static List<String> forRemoveLines = new ArrayList<String>();

    public static void main(String[] args) {
        String file1 = null;
        String file2 = null;
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
             file1 = reader.readLine();
             file2 = reader.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file1))){

            while ((line = reader.readLine()) != null) allLines.add(line);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file2))){

            while ((line = reader.readLine()) != null) forRemoveLines.add(line);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            new Solution().joinData();
        }
        catch (CorruptedDataException e)
        {
            e.printStackTrace();
        }

    }

    public  void joinData () throws CorruptedDataException {

        List<String> allLines_copy = new ArrayList<String>();
        List<String> forRemoveLines_copy = new ArrayList<String>();
        allLines_copy.addAll(allLines);
        forRemoveLines_copy.addAll(forRemoveLines);

        //Если список allLines содержит все строки из forRemoveLines, то удалить из списка allLines все строки,
        //которые есть в forRemoveLines

        if (allLines_copy.containsAll(forRemoveLines_copy)) {

            allLines_copy.removeAll(forRemoveLines_copy);
            allLines = allLines_copy;
        }   //4. Если список allLines НЕ содержит каких-либо строк, которые есть в forRemoveLines, то
            //4.1. очистить allLines от данных
            //4.2. выбросить исключение CorruptedDataException
        else {
            allLines.clear();
            throw new CorruptedDataException();
        }




    }
}
