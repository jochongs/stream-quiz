package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Quiz1 {

    public static void main(String[] args) throws IOException {
        Quiz1 quiz1 = new Quiz1();

        quiz1.quiz3();
    }

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
                .map((words) -> words[1])
                .flatMap(hobby -> Arrays.stream(hobby.replaceAll(" ", "").split(":")))
                .collect(Collectors.toMap((elem) -> elem, (elem) -> 1, (oldValue, newValue) -> oldValue + 1));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();


        return csvLines.stream()
                        .filter(elem -> elem[0].startsWith("정"))
                        .map(elem -> elem[1].replaceAll(" ", ""))
                        .flatMap(elem -> Arrays.stream(elem.split(":")))
                        .collect(Collectors.toMap(elem -> elem, elem -> 1, (oldValue, newValue) -> oldValue + 1));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
                .map(elem -> likeLetterCount(elem[2], 0))
                .reduce(0, Integer::sum);
    }

    private int likeLetterCount(String str, int fromIndex) {
        int index = str.indexOf("좋아", fromIndex);
        if (index == -1) {
            return 0;
        }
        return 1 + likeLetterCount(str, index + "좋아".length());
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
