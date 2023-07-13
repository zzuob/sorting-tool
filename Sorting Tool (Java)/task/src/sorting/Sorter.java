package sorting;

import java.util.*;

public class Sorter {

    private List<Object> data;
    private Map<Object, Integer> count;

    public int getTotalOccurrences() {
        return data.size();
    }

    public static double percentageOccurrences(int repeats, int total) {
        return ((double)repeats/total)*100;
    }

    public String getObjectStats(Object object) {
        StringBuilder result = new StringBuilder();
        result.append(object).append(": ");
        int objectCount = count.get(object);
        result.append(objectCount).append(" time(s), ");
        result.append(Math.round(percentageOccurrences(objectCount, data.size()))).append("%");
        return result.toString();
    }

    class FrequencyComparator implements Comparator<Object> {

        @Override
        public int compare(Object object1, Object object2) {
            Integer frequency1 = count.get(object1) == null ? 0 : count.get(object1);
            Integer frequency2 = count.get(object2) == null ? 0 : count.get(object2);
            return Integer.compare(frequency1, frequency2);
        }
    }

    class AlphanumericObjectComparator implements Comparator<Object> {
        @Override
        public int compare(Object object1, Object object2) {
            if (object1 instanceof String && object2 instanceof String) {
                return ((String) object1).compareTo((String) object2);
            } else if (object1 instanceof Long && object2 instanceof Long) {
                return ((Long) object1).compareTo((Long) object2);
            } else {
                throw new RuntimeException("Objects in AlphanumericObjectComparator must be of same class");
            }
        }
    }


    private static Map<Object, Integer> getFrequency(List<Object> dataSet) {
        Map<Object, Integer> result = new TreeMap<>();
        for (Object object: dataSet) {
            if (result.get(object) == null) {
                result.put(object, 1);
            } else {
                int frequency = result.get(object) + 1;
                result.put(object, frequency);
            }
        }
        return result;
    }

    public List<Object> sortByCount() {
        List<Object> result = new ArrayList<>(count.keySet());
        result.sort(new FrequencyComparator());
        return result;
    }

    public List<Object> sortNaturally() {
        List<Object> result = new ArrayList<>(data);
        result.sort(new AlphanumericObjectComparator());
        return result;
    }
    public Sorter(List<Object> data) {
        this.data = data;
        this.count = getFrequency(data);
    }
}
